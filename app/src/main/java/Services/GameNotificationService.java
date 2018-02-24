package Services;

import java.util.List;

import ClientModel.ClientModel;
import common.DestCard;
import common.PlayerAttributes;
import common.TrainCard;

/**
 * Created by ephraimkunz on 2/21/18.
 */

public class GameNotificationService {
    private final String tag = "GameNotificationService";

    private static GameNotificationService _instance = new GameNotificationService();
    private final ClientModel model = ClientModel.getInstance();
    private final GameHistoryService historyService = GameHistoryService.getInstance();

    private GameNotificationService(){}

    public static GameNotificationService getInstance() {
        return _instance;
    }

    private boolean isMe(String playerId) {
        return model.getUser().getId().equals(playerId);
    }

    public void playerUpdated(PlayerAttributes player) {
        model.updatePlayer(player);
    }

    public void turnBegan(String playerId) {
        model.playerTurnBegan(playerId);
        historyService.playerTurnStarted(playerId);
    }

    public void trainCardDeckUpdated(List<TrainCard> visible, Integer invisible) {
        model.updateTrainCardDeck(visible, invisible);
    }

    public void destCardDeckUpdated(Integer invisible) {
        model.updateDestCardDeck(invisible);
    }

    public void offerDestCards(String playerId, List<DestCard> cards) {
        if (isMe(playerId)) {
            model.setOfferedDestCards(cards);
        }
    }

    public void destCardsChosen(String playerId, List<DestCard> cards) {
        historyService.playerChoseDestCards(playerId, cards);

        if (isMe(playerId)) {
            model.setChosenDestCards(cards);
        }
    }

    public void trainCardChosen(String playerId, TrainCard card) {
        historyService.playerChoseTrainCard(playerId);

        if (isMe(playerId)) {
            model.addTrainCard(card);
        }
    }

    public void chat(String playerId, String message) {
        model.addChat(playerId, message);
    }

    public void routeClaimed(String playerId, String routeId, List<TrainCard> cardsUsed) {
        historyService.playerClaimedRoute(playerId, routeId);
        model.routeClaimed(playerId, routeId);

        if (isMe(playerId)) {
            model.removeTrainCards(cardsUsed);
        }
    }
}
