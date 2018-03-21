package States;

import android.app.Activity;
import android.widget.Toast;

import com.example.cs340.tickettoride.Views.IGamePlayView;
import com.example.cs340.tickettoride.Views.PickTrainCardView;

import ClientModel.ClientModel;
import Presenters.IPresenter;
import Presenters.PickTrainCardPresenter;
import Services.GamePlayService;
import common.TrainCard;

/**
 * Created by matto on 3/14/2018.
 */

public class PickedFirstTrainState extends IState {
    private IGamePlayView mView;

    @Override
    public void enableDisableButtons(IGamePlayView view) {
        mView = view;
        view.enableTrainCardButton();
        view.disableDrawRouteButton();
        view.disableClaimRouteButton();
    }

    @Override
    public void choseTrainCard(IPresenter presenter, TrainCard card)
    {
        if(card.getColor().equals(TrainCard.Colors.wildcard))
        {
            //need to send a toast here
            PickTrainCardPresenter pickTrainCardPresenter = (PickTrainCardPresenter) presenter;
            PickTrainCardView pickTrainCardView = (PickTrainCardView) pickTrainCardPresenter.getView();
            Activity activity = pickTrainCardView.getActivity();
            String msg = "You cannot choose a wild card as your second card.";
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            return;
        }
        GamePlayService.getInstance().selectTrainCard(presenter, card);
        GamePlayService.getInstance().turnEnded(presenter);
        ClientModel.getInstance().setState(new NotMyTurnState());
    }
}
