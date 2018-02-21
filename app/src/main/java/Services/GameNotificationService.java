package Services;

import java.util.List;

import common.DestCard;
import common.PlayerAttributes;
import common.TrainCard;

/**
 * Created by ephraimkunz on 2/21/18.
 */

public class GameNotificationService {
    public static GameNotificationService _instance = new GameNotificationService();
    private final String tag = "GameNotificationService";

    private GameNotificationService(){}

    public static GameNotificationService getInstance() {
        return _instance;
    }

    void startGame() {

    }

    void playerUpdated(PlayerAttributes player) {

    }

    void turnBegan(String playerId) {

    }

    void trainCardDeckUpdated(List<TrainCard> visible, int invisible) {

    }

    void destCardDeckUpdated(int invisible) {

    }

    void offerDestCards(String playerId, List<DestCard> cards) {

    }

    void destCardsChosen(String playerId, List<DestCard> cards) {

    }

    void trainCardChosen(String playerId, TrainCard card) {

    }

    void chat(String playerId, String message) {

    }

    void routeClaimed(String playerId, String routeId) {

    }
}
