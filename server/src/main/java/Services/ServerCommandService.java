package Services;

import java.util.ArrayList;
import java.util.List;

import Model.Game;
import Model.Player;
import Model.ServerModel;
import Plugins.PluginLoader;
import common.Deck;
import common.DestCard;
import common.ICommand;
import common.IServer;
import common.Results;
import common.TrainCard;
import data_transfer.PlayerDTO;
import plugin_common.IPlayerDAO;

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

    public ServerModel getServerModel() {
        return _serverModel;
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
     * Returns the game name for a player ID
     *
     * @param playerID the player ID to use to search
     *
     * @return the name of the game associated with the player
     */
    public static String getGameNameForPlayerID(String playerID){

        ServerModel model = getInstance().getServerModel();

        return model.getGameNameForPlayerID(playerID);
    }

    /**
     * Returns the bytes for a Game from the model
     *
     * @param gameName the name of the game to return for
     *
     * @return the bytes for a Game from the model, or an empty byte array if there is no such game
     */
    public static byte[] getGameBytes(String gameName){
        ServerModel model = getInstance().getServerModel();

        return model.getGameBytes(gameName);
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
            tempPlayer.setPlayerID(username);// + "_registered");
            _serverModel.register(tempPlayer);
            _serverModel.setLoggedIn(tempPlayer);
            returnValue = new Results(true, tempPlayer.getPlayerID(), "");
        }

        // save the user to the database
        PlayerDTO dto = new PlayerDTO();
        dto.isLoggedIn = true;
        dto.password = password;
        dto.username = username;

        IPlayerDAO playerDAO = PluginLoader.getInstance().getPersistanceProvider().getPlayerDao();
        if (playerDAO.getPlayer(username) == null) {
            playerDAO.save(dto);
        }

        return returnValue;
    }

    /**
     * Attempts to log the user in
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return
     */
    public Results login(String username, String password){
        Results returnValue = new Results(false, "", "Invalid username");
        Player tempPlayer = new Player(username, password);

        // Don't log in someone who's already logged in (maybe on another device)
        /*ArrayList<Player> loggedIn = _serverModel.getLoggedInPlayers();
        if (loggedIn.contains(tempPlayer)) {
            return new Results(false, "", "This user already logged in");
        }*/

        // iterate through the registered users to find the requested User
        ArrayList<Player> registered = _serverModel.getRegisteredPlayers();

        for (Player currPlayer : registered){
            if (currPlayer.equals(tempPlayer)){
                tempPlayer.setPlayerID(username);// + "_loggedIn");
                _serverModel.setLoggedIn(tempPlayer);

                // check if the player is already involved in a game
                if (_serverModel.isPlayerInGame(tempPlayer.getPlayerID())){
                    returnValue = new Results(true, tempPlayer.getPlayerID(), "The player is already in a game.");
                }
                else {
                    returnValue = new Results(true, tempPlayer.getPlayerID(), "");
                }
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
     * @param gameName the name of the game
     * @param numOfPlayers the number of players allowed in the game
     * @return
     */
    public Results createGame(String gameName, Integer numOfPlayers) {
        Results returnValue = new Results(false, "", "Unknown error occurred");

        if (_serverModel.gameExists(gameName)){
            returnValue = new Results(false, "", "The game \"" + gameName + "\" already exists. Please try a different name");
        }
        else {
            Game newGame = new Game(gameName, numOfPlayers);
            _serverModel.addGame(newGame);
            returnValue = new Results(true, "", "");
        }

        return returnValue;
    }

    /**
     * Joins a user to a game on the Server
     *
     * @param gameName the name of the game to join
     * @param playerID the player ID of the player to join the game
     * @return
     */
    public Results joinGame(String playerID, String gameName){
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

    /**
     * Returns a list of available games
     *
     * @return a list of available games
     */
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

    /**
     * Returns whether the player is valid in this game (ie does the player exist?)
     *
     * @param playerID the player ID to use
     *
     * @return true if the player exists in this game, false otherwise
     */
    public boolean playerIsValid(String playerID){
        return _serverModel.isPlayerLoggedIn(playerID);
    }

    /**
     * Returns the commands that have not been executed by the client
     *
     * @param playerID the player ID used to find the Game in the model
     * @param commandIndex the index of the first command to be returned
     *
     * @return an array of ICommand object that have not been executed by the client yet
     */
    public ICommand[] getPlayerCommands(String playerID, int commandIndex){

        ICommand[] returnValue = _serverModel.getPlayerCommands(playerID, commandIndex);
        return returnValue;
    }

    /**
     * Returns the server model as a String
     *
     * @return a String representation of the server model
     */
    @Override
    public String toString(){
        return _serverModel.toString();
    }

    /**
     * Returns the server model as a String
     *
     * @return the server model as a String
     */
    public static String getModelString(){
        return getInstance().toString();
    }

    /**
     * Claims the route for the player specified by the player_Id
     *
     * @param playerID the player ID of the player that claimed the route
     * @param routeID the route ID that is being claimed
     * @param cardsUsed the cards that are being used to claim the route
     *
     * @return
     */
    @Override
    public Results claimRoute(String playerID, String routeID, List<TrainCard> cardsUsed) {
        String returnString = _serverModel.claimRoute(playerID, routeID, cardsUsed);

        if (returnString.equals("")){
            // there were no errors
            return new Results(true, "", "");
        }
        else {
            // there was an error
            return new Results(false, "", returnString);
        }
    }

    /**
     * Signals to the game that the turn has ended for the player
     *
     * @param playerID the player ID of the player that ended their turn
     *
     * @return
     */
    @Override
    public Results turnEnded(String playerID) {
        String returnString = _serverModel.endTurn(playerID);
        if (returnString.equals("")){
            return new Results(true, "", "");
        }
        else {
            // there was an error ending the turn (ie the turn did not belong to this player
            return new Results(false, "", returnString);
        }
    }

    /**
     * Requests destination cards for the player specified by the player ID
     *
     * @param playerID the player that is requesting the destination cards
     *
     * @return
     */
    @Override
    public Results requestDestCards(String playerID) {

        String returnString = _serverModel.requestDestCards(playerID);

        if (returnString.equals("")){
            // the cards will be returned in a command
            return new Results(true, "", "");
        }
        else {
            return new Results(false, "", "An error occurred: " + returnString);
        }
    }

    /**
     * Signals to the game the destination cards that are being kept by the player
     *
     * @param playerID the player ID of the player
     * @param keep a list of destination cards that are being kept by the player
     *
     * @return
     */
    @Override
    public Results keepDestCards(String playerID, List<DestCard> keep) {
        String returnString = _serverModel.keepDestCards(playerID, keep);

        if (returnString.equals("")){
            return new Results(true, "", "");
        }
        else {
            return new Results(false, "", returnString);
        }
    }

    /**
     * Takes one of the train cards that is being selected by the player
     *
     * @param playerID the player ID of the player selecting the card
     * @param card the card that is being selected
     * @param pickFromFaceUp whether the card is drawn from the face up deck
     *
     * @return
     */
    @Override
    public Results selectTrainCard(String playerID, TrainCard card, Boolean pickFromFaceUp) {
        String returnString = _serverModel.selectTrainCard(playerID, card, pickFromFaceUp);

        if (returnString.equals("")){
            return new Results(true, "", "");
        }
        else {
            return new Results(false, "", returnString);
        }
    }

    /**
     * Submits a chat message for a player
     *
     * @param playerID the player ID of the player submitting a chat message
     * @param message the message that is being submitted
     *
     * @return
     */
    @Override
    public Results chat(String playerID, String message) {
        boolean result = _serverModel.addChatCommand(playerID, message);

        if (result){
            // the chat was successful
            return new Results(true, "The message was sent successfully", "");
        }
        else {
            // there was an error sending the message
            return new Results(false, "", "The message was not sent");
        }
    }

    public void restoreTrainDeck(String gameName, Deck faceup, Deck facedown) {
        _serverModel.restoreTrainDeck(gameName, faceup, facedown);
    }
}
