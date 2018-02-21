package Presenters;

import java.util.List;

/**
 * Created by Joseph on 2/21/2018.
 */

public interface IGamePlayPresenter
{
    void update();
    void choseDestCards(List<String> cards);    //String is a temporary place holder, replace w/ DestCard after merging
    void choseTrainCards(List<String> cards);   //String is a temporary place holder, replace w/ TrainCard after merging
    void onClickDrawTrainCard();
    void onClickDrawRouteCard();
    void onClickClaimRoute();
    void claimedRoute(String routeID, String playerID);
}
