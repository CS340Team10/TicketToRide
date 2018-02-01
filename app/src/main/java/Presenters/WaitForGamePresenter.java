package Presenters;

import com.example.cs340.tickettoride.Views.IWaitForGameView;

import java.util.Observable;
import java.util.Observer;

import common.Results;

/**
 * Created by ephraimkunz on 2/1/18.
 */

public class WaitForGamePresenter implements IWaitForGamePresenter, IPresenter, Observer {
    IWaitForGameView view;
    public WaitForGamePresenter(IWaitForGameView view) {
        this.view = view;

        // TODO: Register as model observer
    }

    @Override
    public void onPostExecute(Results result) {

    }

    @Override
    public void update(Observable observable, Object o) {
        // TODO: Observe game started change in model and call view.displayMessage("Game began");
    }
}
