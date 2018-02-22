package Communication;

import com.google.gson.Gson;

import java.util.List;

import common.Command;
import common.DestCard;
import common.Endpoints;
import common.ICommand;
import common.IServer;
import common.Results;
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

    @Override
    public Results register(String username, String password) {
        ICommand command = ServerCommandFactory.createRegisterCommand(username, password);
        ClientCommunicator communicator = ClientCommunicator.getInstance();
        String commandJSON = new Gson().toJson(command, Command.class);
        Results results = (Results) communicator.post(Endpoints.EXEC_COMMAND_ENDPOINT, null, commandJSON, Results.class);
        return results;
    }

    @Override
    public Results login(String username, String password) {
        ICommand command = ServerCommandFactory.createLoginCommand(username, password);
        ClientCommunicator communicator = ClientCommunicator.getInstance();
        String commandJSON = new Gson().toJson(command, Command.class);
        Results results = (Results) communicator.post(Endpoints.EXEC_COMMAND_ENDPOINT, null, commandJSON, Results.class);
        return results;
    }

    @Override
    public Results createGame(String gameName, int numPlayers) {
        ICommand command = ServerCommandFactory.createCreateGameCommand(gameName, numPlayers);
        ClientCommunicator communicator = ClientCommunicator.getInstance();
        String commandJSON = new Gson().toJson(command, Command.class);
        Results results = (Results) communicator.post(Endpoints.EXEC_COMMAND_ENDPOINT, null, commandJSON, Results.class);
        return results;
    }

    @Override
    public Results joinGame(String gameName, String playerID) {
        ICommand command = ServerCommandFactory.createJoinGameCommand(gameName, playerID);
        ClientCommunicator communicator = ClientCommunicator.getInstance();
        String commandJSON = new Gson().toJson(command, Command.class);
        Results results = (Results) communicator.post(Endpoints.EXEC_COMMAND_ENDPOINT, playerID, commandJSON, Results.class);
        return results;
    }

    @Override
    public Results claimRoute(String playerId, String routeId) {
        return null;
    }

    @Override
    public Results turnEnded(String playerId) {
        return null;
    }

    @Override
    public Results requestDestCards(String playerId) {
        return null;
    }

    @Override
    public Results keepDestCards(String playerId, List<DestCard> keep) {
        return null;
    }

    @Override
    public Results selectTrainCard(String playerId, TrainCard card, Boolean cardValid) {
        return null;
    }

    @Override
    public Results chat(String playerId, String message) {
        return null;
    }

//    public static void main(String[] args)
//    {
//        ServerProxy proxy = getInstance();
//        Results r = proxy.login("u", "p");
//    }
}
