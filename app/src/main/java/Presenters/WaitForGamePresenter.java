package Presenters;

import com.example.cs340.tickettoride.Views.IWaitForGameView;

import java.util.Observable;
import java.util.Observer;

import Communication.Poller;
import Services.GUIService;
import common.Results;

/**
 * Created by ephraimkunz on 2/1/18.
 */

public class WaitForGamePresenter implements IWaitForGamePresenter, IPresenter, Observer {
    IWaitForGameView view;
    public WaitForGamePresenter(IWaitForGameView view) {
        this.view = view;

        Poller.get_instance().startCommandPoll(); // Stop this when the game ends later on.

        GUIService.getInstance().getClientModel().addObserver(this);
    }

    @Override
    public void onPostExecute(Results result) {

    }

    @Override
    public void update(Observable observable, Object o) {
        if (GUIService.getInstance().getClientModel().getGame().hasStarted()) {
            view.displayMessage("Game began");
        }
    }
}
