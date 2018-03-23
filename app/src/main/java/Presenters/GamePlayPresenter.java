package Presenters;

import android.content.Intent;

import com.example.cs340.tickettoride.Views.IGamePlayView;

import java.util.Observable;
import java.util.Observer;

import ClientModel.ClientModel;
import States.IState;
import common.Results;

/**
 * Created by ephraimkunz on 3/5/18.
 */

public class GamePlayPresenter implements Observer, IPresenter, IGamePlayPresenter {
    IGamePlayView view;
    IState state;

    public GamePlayPresenter(IGamePlayView view) {
        this.view = view;
        update(null, null);
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void onPostExecute(Results result) {

    }

    @Override
    public void update(Observable observable, Object o) {
        view.setDestDeckSize(ClientModel.getInstance().getGame().getDestCardDeckNum());
        state = ClientModel.getInstance().getState();
        state.enableDisableButtons(view);
        if (ClientModel.getInstance().getPointSummaries() != null
                && !ClientModel.getInstance().getPointSummaries().isEmpty())
        {
            view.goToEndGameView();
        }
        if (ClientModel.getInstance().hasLastRoundBegan())
        {
            view.showLastRoundWarning();
        }
    }
}
