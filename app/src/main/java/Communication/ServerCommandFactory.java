package Communication;

import java.util.List;

import common.Command;
import common.DestCard;
import common.ICommand;
import common.TrainCard;

/**
 * Created by matto on 2/1/2018.
 */

public class ServerCommandFactory {
    public static ICommand createLoginCommand(String username, String password)
    {
        String[] objects = {"java.lang.String", "java.lang.String"};
        Object[] values = {username, password};
        return new Command("Services.ServerCommandService", "login", objects, values);
    }

    public static ICommand createRegisterCommand(String username, String password)
    {
        String[] objects = {"java.lang.String" , "java.lang.String"};
        Object[] values = {username, password};
        return new Command("Services.ServerCommandService", "register", objects, values);
    }

    public static ICommand createCreateGameCommand(String gameName, int numPlayers)
    {
        String[] objects = {"java.lang.String", "java.lang.Integer"};
        Object[] values = {gameName, numPlayers};
        return new Command("Services.ServerCommandService", "createGame", objects, values);
    }

    public static ICommand createJoinGameCommand(String gameName, String playerID)
    {
        String[] objects = {"java.lang.String", "java.lang.String"};
        Object[] values = {gameName, playerID};
        return new Command("Services.ServerCommandService", "joinGame", objects, values);
    }

    public static ICommand createClaimRouteCommand(String playerId, String routeId, List<TrainCard> cardsUsed) {
        Command command = new Command(
                "Services.ServerCommandService",
                "claimRoute",
                new String[]{"java.lang.String", "java.lang.String", "java.util.List"},
                new Object[]{playerId, routeId, cardsUsed});
        return command;
    }

    public static ICommand createTurnEndedCommand(String playerId) {
        Command command = new Command(
                "Services.ServerCommandService",
                "turnEnded",
                new String[]{"java.lang.String"},
                new Object[]{playerId});
        return command;
    }

    public static ICommand createRequestDestCardsCommand(String playerId) {
        Command command = new Command(
                "Services.ServerCommandService",
                "requestDestCards",
                new String[]{"java.lang.String"},
                new Object[]{playerId});
        return command;
    }

    public static ICommand createKeepDestCardsCommand(String playerId, List<DestCard> keep) {
        Command command = new Command(
                "Services.ServerCommandService",
                "keepDestCards",
                new String[]{"java.lang.String", "java.util.List"},
                new Object[]{playerId, keep});
        return command;
    }

    public static ICommand createSelectTrainCardCommand(String playerId, TrainCard card, Boolean cardValid) {
        Command command = new Command(
                "Services.ServerCommandService",
                "selectTrainCard",
                new String[]{"java.lang.String", "common.TrainCard", "java.lang.Boolean"},
                new Object[]{playerId, card, cardValid.booleanValue()});
        return command;
    }

    public static ICommand createChatCommand(String playerId, String message) {
        Command command = new Command(
                "Services.ServerCommandService",
                "chat",
                new String[]{"java.lang.String", "java.lang.String"},
                new Object[]{playerId, message});
        return command;
    }
}
