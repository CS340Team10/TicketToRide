package Communication;

import common.Command;

/**
 * Created by matto on 2/1/2018.
 */

public class ServerCommandFactory {
    public static Command createLoginCommand(String username, String password)
    {
        String[] objects = {"java.lang.String", "java.lang.String"};
        Object[] values = {username, password};
        return new Command("Services.ServerCommandService", "login", objects, values);
    }

    public static Command createRegisterCommand(String username, String password)
    {
        String[] objects = {"java.lang.String" , "java.lang.String"};
        Object[] values = {username, password};
        return new Command("Services.ServerCommandService", "register", objects, values);
    }

    public static Command createCreateGameCommand(String gameName, int numPlayers)
    {
        // Deserializing an integer becomes a double, so pass double.
        String[] objects = {"java.lang.String", "java.lang.Double"};
        Object[] values = {gameName, numPlayers};
        return new Command("Services.ServerCommandService", "createGame", objects, values);
    }

    public static Command createJoinGameCommand(String gameName, String playerID)
    {
        String[] objects = {"java.lang.String", "java.lang.String"};
        Object[] values = {gameName, playerID};
        return new Command("common.Command", "LoginCommand", objects, values);
    }

}
