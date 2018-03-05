package Presenters;


import com.example.cs340.tickettoride.Views.IPickTrainCardView;

import java.util.Observable;
import java.util.Observer;

import ClientModel.ClientModel;
import Services.GamePlayService;
import common.Results;
import common.TrainCard;

/**
 * Created by ephraimkunz on 3/3/18.
 */

public class PickTrainCardPresenter implements IPickTrainCardPresenter, IPresenter, Observer {
    IPickTrainCardView view;

    public PickTrainCardPresenter(IPickTrainCardView view) {
        this.view = view;
        ClientModel.getInstance().addObserver(this);
        update(null, null); // Load data the first time
    }

    @Override
    public void pickedFacedown() {
        GamePlayService.getInstance().selectTrainCard(this, null);
    }

    @Override
    public void pickedFaceup(TrainCard card) {
        GamePlayService.getInstance().selectTrainCard(this, card);
    }

    @Override
    public void onPostExecute(Results result) {
        if (result.succeeded()) {
            view.dismissView();
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        view.setInvisibleCards(ClientModel.getInstance().getGame().getTrainCardDeckNum());
        view.setVisibleCards(ClientModel.getInstance().getGame().getFaceupTrainCards());
    }
}
