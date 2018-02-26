package Services;

import java.util.List;

import ClientModel.ClientModel;
import common.DestCard;
import common.Route;

/**
 * Created by ephraimkunz on 2/22/18.
 */

public class GameHistoryService {
    private static GameHistoryService _instance = new GameHistoryService();
    private final ClientModel model = ClientModel.getInstance();

    private GameHistoryService(){}

    public static GameHistoryService getInstance() {
        return _instance;
    }

    public void playerChoseDestCards(String playerId, List<DestCard> cards) {
        model.addHistory(String.format("Player %s chose %d destination cards", playerId, cards.size()));
    }

    public void playerTurnStarted(String playerId) {
        model.addHistory(String.format("Player %s started their turn", playerId));
    }

    public void playerChoseTrainCard(String playerId) {
        model.addHistory(String.format("Player %s chose a train card", playerId));
    }

    public void playerClaimedRoute(String playerId, String routeId) {
        Route route = model.getRouteById(routeId);
        // TODO: Fix this once route implemented.
        model.addHistory(String.format("Player %s claimed a route from %s to %s", playerId, routeId));
    }
}
