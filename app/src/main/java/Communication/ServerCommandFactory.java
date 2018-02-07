package Communication;

/**
 * Created by matto on 2/1/2018.
 */

public class ServerCommandFactory {
    public static ServerCommand createLoginCommand(String username, String password)
    {
        Object[] objects = {String.class, String.class};
        Object[] values = {username, password};
        return new ServerCommand("ServerCommand", "LoginCommand", objects, values);
    }

    public static ServerCommand createRegisterCommand(String username, String password)
    {
        Object[] objects = {String.class, String.class};
        Object[] values = {username, password};
        return new ServerCommand("ServerCommand", "RegisterCommand", objects, values);
    }

    public static ServerCommand createCreateGameCommand(String gameName, int numPlayers)
    {
        Object[] objects = {String.class, int.class};
        Object[] values = {gameName, numPlayers};
        return new ServerCommand("ServerCommand", "CreateGameCommand", objects, values);
    }

    public static ServerCommand createJoinGameCommand(String gameName, String playerID)
    {
        Object[] objects = {String.class, String.class};
        Object[] values = {gameName, playerID};
        return new ServerCommand("ServerCommand", "LoginCommand", objects, values);
    }

}
