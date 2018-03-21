package Services;

import java.util.List;

import common.Command;
import common.DestCard;
import common.ICommand;
import common.PlayerAttributes;
import common.PlayerPointSummary;
import common.TrainCard;

/**
 * Created by Brian on 2/8/18.
 */

public class ClientCommandFactory {

    /**
     * Returns a command to inform the clients that the game is starting
     *
     * @return a command that informs the clients that the game is starting
     */
    public static ICommand createStartGameCommand(){
        Command returnCommand = new Command("Services.ClientGameService", "gameDidStart", new String[]{}, new Object[]{});

        return returnCommand;
    }

    public static ICommand createPlayerUpdatedCommand(PlayerAttributes player) {
        Command command = new Command("Services.GameNotificationService", "playerUpdated", new String[]{"common.PlayerAttributes"}, new Object[]{player} );
        return command;
    }

    public static ICommand createTurnBeganCommand(String playerId) {
        Command command = new Command("Services.GameNotificationService", "turnBegan", new String[]{"java.lang.String"}, new Object[]{playerId} );
        return command;
    }

    public static ICommand createTrainCardDeckUpdatedCommand(List<TrainCard> visible, int invisible) {
        Command command = new Command("Services.GameNotificationService", "trainCardDeckUpdated", new String[]{"java.util.List", "java.lang.Integer"}, new Object[]{visible, invisible} );
        return command;
    }

    public static ICommand createDestCardDeckUpdatedCommand(int invisible) {
        Command command = new Command("Services.GameNotificationService", "destCardDeckUpdated", new String[]{"java.lang.Integer"}, new Object[]{invisible} );
        return command;
    }

    public static ICommand createOfferDestCardsCommand(String playerId, List<DestCard> cards) {
        Command command = new Command("Services.GameNotificationService", "offerDestCards", new String[]{"java.lang.String", "java.util.List"}, new Object[]{playerId, cards} );
        return command;
    }

    public static ICommand createDestCardsChosenCommand(String playerId, List<DestCard> cards) {
        Command command = new Command("Services.GameNotificationService", "destCardsChosen", new String[]{"java.lang.String", "java.util.List"}, new Object[]{playerId, cards} );
        return command;
    }

    public static ICommand createTrainCardChosenCommand(String playerId, TrainCard card) {
        Command command = new Command("Services.GameNotificationService", "trainCardChosen", new String[]{"java.lang.String", "common.TrainCard"}, new Object[]{playerId, card} );
        return command;
    }

    public static ICommand createChatCommand(String playerId, String message) {
        Command command = new Command("Services.GameNotificationService", "chat", new String[]{"java.lang.String", "java.lang.String"}, new Object[]{playerId, message} );
        return command;
    }

    public static ICommand createRouteClaimedCommand(String playerId, String routeId, List<TrainCard> cardsUsed) {
        Command command = new Command("Services.GameNotificationService", "routeClaimed", new String[]{"java.lang.String", "java.lang.String", "java.util.List"}, new Object[]{playerId, routeId, cardsUsed} );
        return command;
    }

    public static ICommand createGameOverStatisticsCommand(List<PlayerPointSummary> pointSummaries) {
        Command command = new Command("Services.GameNotificationService", "gameOverStatistics", new String[]{"java.util.List"}, new Object[]{pointSummaries});
        return command;
    }

    public static ICommand createLastRoundBeganCommand() {
        Command command = new Command("Services.GameNotificationService", "lastRoundBegan", new String[]{}, new Object[]{});
        return command;
    }
}
