package States;

import com.example.cs340.tickettoride.Views.IGamePlayView;

import java.util.List;

import Presenters.IPresenter;
import common.DestCard;
import common.TrainCard;

/**
 * Created by matto on 3/14/2018.
 */

public abstract class IState {
    public void enableDisableButtons(IGamePlayView view){}
    public void choseDestCards(List<DestCard> cards){}
    public void choseTrainCard(TrainCard card){}
    public void pickedTrainCard(IPresenter presenter, TrainCard card){}
    public void onClickDrawDestCard(){}
    public void onClickClaimRoute(){}
    public void claimedRoute(String routeID, String playerID){}

}
