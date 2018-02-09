package Services;

import java.util.ArrayList;

import Model.Game;
import Model.Player;
import Server.ServerModel;
import common.ICommand;
import common.IServer;
import common.Results;

/**
 * Created by Brian on 2/1/18.
 */

public class ServerCommandService implements IServer {

    private static ServerCommandService _instance = new ServerCommandService();
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
    public static ServerCommandService getInstance(){
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
        Results returnValue = null;
        Player tempPlayer = new Player(username, password);

        // iterate through the registered users to find the requested User
        ArrayList<Player> players = _serverModel.getRegisteredPlayers();

        for (int count = 0; count < players.size(); count++){
            Player currPlayer = players.get(count);
            if (currPlayer.getUsername().equals(tempPlayer.getUsername())){
                returnValue = new Results(false, "", "That username is already in use");
                break;
            }
        }

        if (returnValue == null){
            // the username has not been taken
            tempPlayer.setPlayerID(username + "_registered");
            _serverModel.register(tempPlayer);
            _serverModel.setLoggedIn(tempPlayer);
        }

        return returnValue;
    }

    /**
     * Attempts to log the user in
     *
     * @param username
     * @param password
     * @return
     */
    public Results login(String username, String password){
        Results returnValue = new Results(false, "", "Invalid username");
        Player tempPlayer = new Player(username, password);

        // iterate through the registered users to find the requested User
        ArrayList<Player> players = _serverModel.getRegisteredPlayers();

        for (int count = 0; count < players.size(); count++){
            Player currPlayer = players.get(count);
            if (currPlayer.equals(tempPlayer)){
                tempPlayer.setPlayerID(username + "_loggedIn");
                _serverModel.setLoggedIn(tempPlayer);
                returnValue = new Results(true, tempPlayer.getPlayerID(), "");
                break;
            }
            else if (currPlayer.getUsername().equals(tempPlayer.getUsername())){
                returnValue = new Results(false, "", "Incorrect password");
                break;
            }
        }

        return returnValue;
    }

    /**
     * Creates a new game on the Server
     *
     * @param gameName
     * @param numOfPlayers
     * @return
     */
    public Results createGame(String gameName, Double numOfPlayers) {
        return createGame(gameName, (int)Math.round(numOfPlayers));
    }

    /**
     * Creates a new game on the Server
     *
     * @param gameName
     * @param numOfPlayers
     * @return
     */
    public Results createGame(String gameName, int numOfPlayers) {
        Results returnValue = new Results(false, "", "Unknown error occurred");

        Game newGame = new Game(gameName, numOfPlayers);
        if (_serverModel.gameExists(newGame)){
            returnValue = new Results(false, "", "The game \"" + gameName + "\" already exists. Please try a different name");
        }
        else {
            _serverModel.addGame(newGame);
            returnValue = new Results(true, "Game \"" + gameName + "\" successfully created.", "");
        }

        return returnValue;
    }

    /**
     * Joins a user to a game on the Server
     *
     * @param gameName
     * @param playerID
     * @return
     */
    public Results joinGame(String gameName, String playerID){
        Results returnValue = new Results(false, "", "Unknown error occurred");

        if (_serverModel.isPlayerLoggedIn(playerID)){
            // the player exists, so add them to the game
            String gameMessage = _serverModel.addPlayerToGame(gameName, playerID);

            if (gameMessage.equals("")){
                // the player was added successfully
                returnValue = new Results(true, "The player \"" + playerID + "\" was added to the game \"" + gameName + "\"", "");
            }
            else {
                returnValue = new Results(false, "", "Error message from Game: \"" + gameMessage + "\"");
            }
        }
        else {
            // there is no player with that ID
            returnValue = new Results(false, "", "The player ID \"" + playerID + "\" is invalid");
        }

        return returnValue;
    }

    public ArrayList<String> getAvailableGames(){
        ArrayList<String> returnValue = new ArrayList<String>();

        ArrayList<Game> availableGames = _serverModel.getGames();

        for (int count = 0; count < availableGames.size(); count++){
            if (!availableGames.get(count).hasStarted()){
                returnValue.add(availableGames.get(count).getName());
            }
        }


        return returnValue;
    }

    public boolean playerIsValid(String playerID){
        return _serverModel.isPlayerLoggedIn(playerID);
    }

    public ICommand[] getPlayerCommands(String playerID){

        ICommand[] returnValue = _serverModel.getPlayerCommands(playerID);


        return returnValue;
    }

    @Override
    public String toString(){
        return _serverModel.toString();
    }

    public static String getModelString(){
        return getInstance().toString();
    }
}
