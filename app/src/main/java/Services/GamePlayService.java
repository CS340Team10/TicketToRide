package Services;

import java.util.ArrayList;
import java.util.List;

import Presenters.IPresenter;
import common.DestCard;
import common.Route;
import common.TrainCard;

/**
 * Created by ephraimkunz on 2/21/18.
 */

public class GamePlayService {
    private static GamePlayService _instance = new GamePlayService();
    private final String tag = "GamePlayService";

    private GamePlayService(){}

    public static GamePlayService getInstance() {
        return _instance;
    }

    public void claimRoute(IPresenter presenter, String routeId) {

    }

    public void turnEnded(IPresenter presenter) {

    }

    public void requestDestCards(IPresenter presenter) {

    }

    public void keepDestCards(IPresenter presenter, List<DestCard> keep) {

    }

    // Train card is null if the player desires to select from the facedown deck.
    public void selectTrainCard(IPresenter presenter, TrainCard card) {

    }

    public void sendChat(IPresenter presenter, String message) {

    }

    public List<Route> getClaimableRoutes() {
        return new ArrayList<Route>();
    }
}
