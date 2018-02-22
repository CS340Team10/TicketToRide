package Services;

import java.util.ArrayList;
import java.util.List;

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

    public void claimRoute(String routeId) {

    }

    public void turnEnded() {

    }

    public void requestDestCards() {

    }

    public void keepDestCards(List<DestCard> keep) {

    }

    // Train card is null if the player desires to select from the facedown deck.
    public void selectTrainCard(TrainCard card) {

    }

    public void sendChat(String message) {

    }

    public List<Route> getClaimableRoutes() {
        return new ArrayList<Route>();
    }
}
