package Services;

import java.util.List;

import common.DestCard;

/**
 * Created by ephraimkunz on 2/22/18.
 */

public class GameHistoryService {
    private static GameHistoryService _instance = new GameHistoryService();

    private GameHistoryService(){}

    public static GameHistoryService getInstance() {
        return _instance;
    }

    public void choseDestCards(String playerId, List<DestCard> cards) {

    }

    public void playerTurnStarted(String playerId) {

    }

    public void playerChoseTrainCard(String playerId) {

    }

    public void playerClaimedRoute(String playerId, String routeId) {

    }
}
