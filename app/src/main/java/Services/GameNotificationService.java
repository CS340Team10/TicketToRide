package Services;

import android.util.Log;

import java.util.List;

import common.DestCard;
import common.PlayerAttributes;
import common.TrainCard;

/**
 * Created by ephraimkunz on 2/21/18.
 */

public class GameNotificationService {
    private static GameNotificationService _instance = new GameNotificationService();
    private final String tag = "GameNotificationService";

    private GameNotificationService(){}

    public static GameNotificationService getInstance() {
        return _instance;
    }

    public void playerUpdated(PlayerAttributes player) {
        Log.d(tag, "playerUpdated: " + player);

    }

    public void turnBegan(String playerId) {
        Log.d(tag, "turnBegan: " + playerId);

    }

    public void trainCardDeckUpdated(List<TrainCard> visible, Integer invisible) {
        Log.d(tag, "trainCardDeckUpdate: " + visible + ", " + invisible);

    }

    public void destCardDeckUpdated(Integer invisible) {
        Log.d(tag, "destCardDeckUpdated: " + invisible);

    }

    public void offerDestCards(String playerId, List<DestCard> cards) {
        Log.d(tag, "offerDestCards: " + playerId + ", " + cards);

    }

    public void destCardsChosen(String playerId, List<DestCard> cards) {
        Log.d(tag, "destCardsChosen: " + playerId + ", " + cards);

    }

    public void trainCardChosen(String playerId, TrainCard card) {
        Log.d(tag, "trainCardChosen: " + playerId + ", " + card);

    }

    public void chat(String playerId, String message) {
        Log.d(tag, "chat: " + playerId + ", " + message);

    }

    public void routeClaimed(String playerId, String routeId) {
        Log.d(tag, "routeClaimed: " + playerId + ", " + routeId);

    }
//
//    public static void main(String[] args){
//        ArrayList<TrainCard> cards = new ArrayList<>();
//        cards.add(new TrainCard());
//        DestCard card = new DestCard();
//        String playerId = "ephraim";
//        String routeId = "hello";
//        int i = 10;
//        Command command = new Command("Services.GameNotificationService", "routeClaimed", new String[]{"java.lang.String", "java.lang.String"}, new Object[]{playerId, routeId} );
//        command.execute();
//    }
}
