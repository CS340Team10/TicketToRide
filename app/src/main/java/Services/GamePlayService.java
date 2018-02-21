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
    public static GamePlayService _instance = new GamePlayService();
    private final String tag = "GamePlayService";

    private GamePlayService(){}

    public static GamePlayService getInstance() {
        return _instance;
    }

    void claimRoute(String routeId) {

    }

    void turnEnded() {

    }

    void requestDestCards() {

    }

    void keepDestCards(List<DestCard> keep) {

    }

    // Train card is null if the player desires to select from the facedown deck.
    void selectTrainCard(TrainCard card) {

    }

    void sendChat(String message) {

    }

    List<Route> getClaimableRoutes() {
        return new ArrayList<Route>();
    }
}
