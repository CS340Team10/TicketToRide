package Communication;

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
        ServerCommand command = ServerCommandFactory.createRegisterCommand(username, password);
        return null;
    }

    @Override
    public Results login(String username, String password) {
        ServerCommand command = ServerCommandFactory.createLoginCommand(username, password);
        return null;
    }

    @Override
    public Results createGame(String gameName, int numPlayers) {
        ServerCommand command = ServerCommandFactory.createCreateGameCommand(gameName, numPlayers);
        return null;
    }

    @Override
    public Results joinGame(String gameName, String playerID) {
        ServerCommand command = ServerCommandFactory.createRegisterCommand(gameName, playerID);
        return null;
    }
}
