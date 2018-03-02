package Communication;

import java.util.List;

import common.DestCard;
import common.Endpoints;
import common.ICommand;
import common.IServer;
import common.Results;
import common.Serializer;
import common.TrainCard;

/**
 * Created by matto on 2/1/2018.
 */

public class ServerProxy implements IServer
{
    private static ServerProxy _instance = new ServerProxy();

    private ServerProxy(){}

    public static ServerProxy getInstance()
    {
        return _instance;
    }

    private Results postCommand(ICommand command) {
        ClientCommunicator communicator = ClientCommunicator.getInstance();
        Results results = new Results(false, "", "Error serializing command");

        try {
            String commandJSON = Serializer.getInstance().serializeObject(command);
            results = (Results) communicator.post(Endpoints.EXEC_COMMAND_ENDPOINT, null, commandJSON, Results.class);
        } catch (Exception e) {
            results.setError(e.toString());
        } finally {
            return results;
        }
    }

    @Override
    public Results register(String username, String password) {
        ICommand command = ServerCommandFactory.createRegisterCommand(username, password);
        return postCommand(command);
    }

    @Override
    public Results login(String username, String password) {
        ICommand command = ServerCommandFactory.createLoginCommand(username, password);
        return postCommand(command);
    }

    @Override
    public Results createGame(String gameName, Integer numPlayers) {
        ICommand command = ServerCommandFactory.createCreateGameCommand(gameName, numPlayers);
        return postCommand(command);
    }

    @Override
    public Results joinGame(String gameName, String playerID) {
        ICommand command = ServerCommandFactory.createJoinGameCommand(gameName, playerID);
        return postCommand(command);
    }

    @Override
    public Results claimRoute(String playerId, String routeId, List<TrainCard> cardsUsed) {
        ICommand command = ServerCommandFactory.createClaimRouteCommand(playerId, routeId);
        return postCommand(command);
    }

    @Override
    public Results turnEnded(String playerId) {
        ICommand command = ServerCommandFactory.createTurnEndedCommand(playerId);
        return postCommand(command);
    }

    @Override
    public Results requestDestCards(String playerId) {
        ICommand command = ServerCommandFactory.createRequestDestCardsCommand(playerId);
        return postCommand(command);
    }

    @Override
    public Results keepDestCards(String playerId, List<DestCard> keep) {
        ICommand command = ServerCommandFactory.createKeepDestCardsCommand(playerId, keep);
        return postCommand(command);
    }

    @Override
    public Results selectTrainCard(String playerId, TrainCard card, Boolean pickFromFaceUp) {
        ICommand command = ServerCommandFactory.createSelectTrainCardCommand(playerId, card, pickFromFaceUp);
        return postCommand(command);
    }

    @Override
    public Results chat(String playerId, String message) {
        ICommand command = ServerCommandFactory.createChatCommand(playerId, message);
        return postCommand(command);
    }
}
