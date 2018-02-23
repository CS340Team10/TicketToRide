package Services;

import java.util.ArrayList;
import java.util.List;

import Model.Game;
import Model.Player;
import Model.ServerModel;
import common.DestCard;
import common.ICommand;
import common.IServer;
import common.Results;
import common.TrainCard;

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

        for (Player currPlayer : players) {
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
            returnValue = new Results(true, tempPlayer.getPlayerID(), "");
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

        // Don't log in someone who's already logged in (maybe on another device)
        ArrayList<Player> loggedIn = _serverModel.getLoggedInPlayers();
        if (loggedIn.contains(tempPlayer)) {
            return new Results(false, "", "This user already logged in");
        }

        // iterate through the registered users to find the requested User
        ArrayList<Player> registered = _serverModel.getRegisteredPlayers();

        for (Player currPlayer : registered){
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
    public Results createGame(String gameName, Integer numOfPlayers) {
        Results returnValue = new Results(false, "", "Unknown error occurred");

        Game newGame = new Game(gameName, numOfPlayers);
        if (_serverModel.gameExists(newGame)){
            returnValue = new Results(false, "", "The game \"" + gameName + "\" already exists. Please try a different name");
        }
        else {
            _serverModel.addGame(newGame);
            returnValue = new Results(true, "", "");
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

        for (Game game : availableGames){
            if (!game.hasStarted()){
                returnValue.add(game.getName());
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
}
