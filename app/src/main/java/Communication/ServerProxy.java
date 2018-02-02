package Communication;

/**
 * Created by matto on 2/1/2018.
 */

public class ServerProxy implements IServer{

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
