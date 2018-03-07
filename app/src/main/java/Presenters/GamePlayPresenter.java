package Presenters;

import com.example.cs340.tickettoride.Views.IGamePlayView;

import java.util.Observable;
import java.util.Observer;

import ClientModel.ClientModel;
import common.Results;

/**
 * Created by ephraimkunz on 3/5/18.
 */

public class GamePlayPresenter implements Observer, IPresenter, IGamePlayPresenter {
    IGamePlayView view;
    boolean myTurnSeen;

    public GamePlayPresenter(IGamePlayView view) {
        this.view = view;
        myTurnSeen = false;
        update(null, null);

        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void onPostExecute(Results result) {

    }

    @Override
    public void update(Observable observable, Object o) {
        view.setDestDeckSize(ClientModel.getInstance().getGame().getDestCardDeckNum());

        // Check for beginning of turn
        if (ClientModel.getInstance().getUser().isMyTurn()) {
            if (!myTurnSeen) {
                view.enableClaimRouteButton();
                view.enableDrawRouteButton();
                view.enableTrainCardButton();
            }
            myTurnSeen = true;
        } else {
            myTurnSeen = false;
        }
    }
}
