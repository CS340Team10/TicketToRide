package Services;

import java.util.ArrayList;

import Model.Player;
import Server.ServerModel;

import Server.ServerModel;
import common.IServer;
import common.Results;

/**
 * Created by Brian on 2/1/18.
 */

public class ServerCommandService implements IServer {

    private ServerCommandService _instance = new ServerCommandService();
    private ServerModel _serverModel;

    /**
     * Constructs the ServerCommandService Object
     */
    private ServerCommandService(){
        _serverModel = new ServerModel();
    }

    /**
     * Returns the instance of ServerCommandInstance
     *
     * @return the instance of ServerCommandInstance
     */
    public ServerCommandService getInstance(){
        return _instance;
    }

    /**
     * Attempts to register the user in the system
     *
     * @param username the username
     * @param password the password
     *
     * @return a Result from the server
     */
    public Results register(String username, String password){
        return null;
    }

    /**
     * Attempts to log the user in
     *
     * @param username
     * @param password
     * @return
     */
    public Results login(String username, String password){
        Results returnValue;

        // iterate through the registered users to find the requested User
        ArrayList<Player> players = _serverModel.getRegisteredPlayers();

        return null;
    }

    /**
     * Creates a new game on the Server
     *
     * @param gameName
     * @param numPlayers
     * @return
     */
    public Results createGame(String gameName, int numPlayers){
        return null;
    }

    /**
     * Joins a user to a game on the Server
     *
     * @param gameName
     * @param playerID
     * @return
     */
    public Results joinGame(String gameName, String playerID){
        return null;
    }
}
