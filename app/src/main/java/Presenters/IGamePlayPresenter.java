package Presenters;

import java.util.List;

import common.DestCard;
import common.TrainCard;

/**
 * Created by Joseph on 2/21/2018.
 */

public interface IGamePlayPresenter
{
    void update();
    void choseDestCards(List<DestCard> cards);
    void choseTrainCards(List<TrainCard> cards);
    void onClickDrawTrainCard();
    void onClickDrawRouteCard();
    void onClickClaimRoute();
    void claimedRoute(String routeID, String playerID);
}
