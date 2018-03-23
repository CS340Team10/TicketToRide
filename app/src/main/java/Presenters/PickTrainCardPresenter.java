package Presenters;


import com.example.cs340.tickettoride.Views.IPickTrainCardView;

import java.util.Observable;
import java.util.Observer;

import ClientModel.ClientModel;
import States.IState;
import common.Results;
import common.TrainCard;

/**
 * Created by ephraimkunz on 3/3/18.
 */

public class PickTrainCardPresenter implements IPickTrainCardPresenter, IPresenter, Observer {
    IPickTrainCardView view;
    IState state;

    public PickTrainCardPresenter(IPickTrainCardView view) {
        this.view = view;
        this.state = ClientModel.getInstance().getState();
        ClientModel.getInstance().addObserver(this);
        update(null, null); // Load data the first time
    }

    @Override
    public void pickedTrainCard(TrainCard card) {
        state.choseTrainCard(this, card);
    }

    @Override
    public void onPostExecute(Results result)
    {
        if (result != null && !result.succeeded())
        {
            view.showToast(result.getError());
        }
        else if (result.succeeded()) {
            view.dismissView();
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        view.setInvisibleCards(ClientModel.getInstance().getGame().getTrainCardDeckNum());
        view.setVisibleCards(ClientModel.getInstance().getGame().getFaceupTrainCards());
        state = ClientModel.getInstance().getState();
    }

    public IPickTrainCardView getView()
    {
        return view;
    }

}
