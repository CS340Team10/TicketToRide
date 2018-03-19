package States;

import com.example.cs340.tickettoride.Views.IGamePlayView;

import ClientModel.ClientModel;
import Presenters.IPresenter;
import Services.GamePlayService;
import common.TrainCard;

/**
 * Created by matto on 3/14/2018.
 */

public class MyTurnState extends IState {
    @Override
    public void enableDisableButtons(IGamePlayView view) {
        view.enableTrainCardButton();
        view.enableDrawRouteButton();
        view.enableClaimRouteButton();
    }

    @Override
    public void choseTrainCard(IPresenter presenter, TrainCard card){
        GamePlayService.getInstance().selectTrainCard(presenter, card);
        ClientModel.getInstance().setState(new PickedFirstTrainState());
    }

    @Override
    public void requestedDestCards(IPresenter presenter) {
        GamePlayService.getInstance().requestDestCards(presenter);
        ClientModel.getInstance().setState(new RequestedDestCardsState());
    }

    @Override
    public void claimedRoute(String routeID, String playerID){
        //TODO this
    }
}
