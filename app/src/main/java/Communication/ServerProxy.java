package Communication;

import com.google.gson.Gson;

import common.Command;
import common.Endpoints;
import common.IServer;
import common.Results;

/**
 * Created by matto on 2/1/2018.
 */

public class ServerProxy implements IServer
{
    private static ServerProxy _instance = new ServerProxy();

    public static ServerProxy get_instance()
    {
        return _instance;
    }

    @Override
    public Results register(String username, String password) {
        Command command = ServerCommandFactory.createRegisterCommand(username, password);
        ClientCommunicator communicator = ClientCommunicator.get_instance();
        String commandJSON = new Gson().toJson(command, Command.class);
        Results results = (Results) communicator.post(Endpoints.EXEC_COMMAND_ENDPOINT, null, commandJSON, Results.class);
        return results;
    }

    @Override
    public Results login(String username, String password) {
        Command command = ServerCommandFactory.createLoginCommand(username, password);
        ClientCommunicator communicator = ClientCommunicator.get_instance();
        String commandJSON = new Gson().toJson(command, Command.class);
        Results results = (Results) communicator.post(Endpoints.EXEC_COMMAND_ENDPOINT, null, commandJSON, Results.class);
        return results;
    }

    @Override
    public Results createGame(String gameName, int numPlayers) {
        Command command = ServerCommandFactory.createCreateGameCommand(gameName, numPlayers);
        ClientCommunicator communicator = ClientCommunicator.get_instance();
        String commandJSON = new Gson().toJson(command, Command.class);
        Results results = (Results) communicator.post(Endpoints.EXEC_COMMAND_ENDPOINT, null, commandJSON, Results.class);
        return results;
    }

    @Override
    public Results joinGame(String gameName, String playerID) {
        Command command = ServerCommandFactory.createRegisterCommand(gameName, playerID);
        ClientCommunicator communicator = ClientCommunicator.get_instance();
        String commandJSON = new Gson().toJson(command, Command.class);
        Results results = (Results) communicator.post(Endpoints.EXEC_COMMAND_ENDPOINT, playerID, commandJSON, Results.class);
        return results;
    }

    public static void main(String[] args)
    {
        ServerProxy proxy = get_instance();
        Results r = proxy.login("u", "p");
    }
}
