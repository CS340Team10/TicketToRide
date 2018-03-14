package States;

import java.util.List;

import Presenters.IPresenter;
import common.DestCard;
import common.Results;
import common.TrainCard;

/**
 * Created by matto on 3/14/2018.
 */

public class IState {
    IPresenter presenter;

    public IPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }

    public void choseDestCards(List<DestCard> cards){}
    public void choseTrainCard(TrainCard card){}
    public void onClickDrawTrainCard(){}
    public void onClickDrawDestCard(){}
    public void onClickClaimRoute(){}
    public void claimedRoute(String routeID, String playerID){}

}
