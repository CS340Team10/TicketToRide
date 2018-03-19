package States;

import com.example.cs340.tickettoride.Views.IGamePlayView;

import ClientModel.ClientModel;
import Presenters.IPresenter;
import Services.GamePlayService;
import common.TrainCard;

/**
 * Created by matto on 3/14/2018.
 */

public class PickedFirstTrainState extends IState {
    @Override
    public void enableDisableButtons(IGamePlayView view) {
        view.enableTrainCardButton();
        view.disableDrawRouteButton();
        view.disableClaimRouteButton();
    }

    @Override
    public void choseTrainCard(IPresenter presenter, TrainCard card)
    {
        GamePlayService.getInstance().selectTrainCard(presenter, card);
        GamePlayService.getInstance().turnEnded(presenter);
        ClientModel.getInstance().setState(new NotMyTurnState());
    }
}
