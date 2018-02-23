package Presenters;

import com.example.cs340.tickettoride.Views.IWaitForGameView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Communication.Poller;
import Services.GUIService;
import Services.GamePlayService;
import common.DestCard;
import common.Results;
import common.TrainCard;

/**
 * Created by ephraimkunz on 2/1/18.
 */

public class WaitForGamePresenter implements IWaitForGamePresenter, IPresenter, Observer {
    IWaitForGameView view;
    public WaitForGamePresenter(IWaitForGameView view) {
        this.view = view;

        Poller.getInstance().startCommandPoll(); // Stop this when the game ends later on.

        GUIService.getInstance().getClientModel().addObserver(this);

        // Test service
        GamePlayService.getInstance().claimRoute(this, "fakeRouteId");
        GamePlayService.getInstance().turnEnded(this);
        GamePlayService.getInstance().requestDestCards(this);
        ArrayList<DestCard> list = new ArrayList<>();
        list.add(new DestCard());
        list.add(new DestCard());
        GamePlayService.getInstance().keepDestCards(this, list);
        GamePlayService.getInstance().selectTrainCard(this, new TrainCard());
        GamePlayService.getInstance().sendChat(this, "heello chat");
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
