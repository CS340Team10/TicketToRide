package Model;

import java.util.ArrayList;
import java.util.List;

import common.Deck;
import common.DestCard;
import common.ICommand;
import common.TrainCard;

/**
 * Created by Brian on 2/1/18.
 */

public class ServerModel {
    private ArrayList<Player> _registeredPlayers = new ArrayList<Player>();
    private ArrayList<Player> _loggedInPlayers = new ArrayList<Player>();
    private ArrayList<Game> _currentGames = new ArrayList<Game>();

    /**
     * Constructs the ServerModel
     */
    public ServerModel(){
        // add some default registered players for now
        /*Player player1 = new Player("player1", "password");
        Player player2 = new Player("player2", "secret");
        Player player3 = new Player("player3", "my_precious");

        _registeredPlayers.add(player1);
        _registeredPlayers.add(player2);
        _registeredPlayers.add(player3);*/
    }

    /**
     * Returns the list of all registered players
     *
     * @return an ArrayList of registered Players
     */
    public ArrayList<Player> getRegisteredPlayers(){
        return _registeredPlayers;
    }

    public void register(Player player){
        _registeredPlayers.add(player);
    }

    /**
     * Returns a list of all players currently logged in
     *
     * @return an ArrayList of all currently logged in Players
     */
    public ArrayList<Player> getLoggedInPlayers(){
        return _loggedInPlayers;
    }

    public void setLoggedIn(Player player){
        _loggedInPlayers.add(player);
    }

    /**
     * Returns whether the player represented by the player ID is currently logged in
     *
     * @param playerID the player ID to check for
     *
     * @return true if the Player with the player ID is logged in, false otherwise
     */
    public boolean isPlayerLoggedIn(String playerID){

        for (Player player : _loggedInPlayers){

            if (player.getPlayerID().equals(playerID)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of all current games
     *
     * @return an ArrayList of current Games
     */
    public ArrayList<Game> getGames(){
        return _currentGames;
    }

    /**
     * Adds the new game to the list of current games
     *
     * @param newGame the new Game to add
     */
    public void addGame(Game newGame){
        _currentGames.add(newGame);
    }

    /**
     * Returns whether the game already exists
     *
     * @param gameName the Game to compare to
     *
     * @return true if the Game already exists, false otherwise
     */
    public boolean gameExists(String gameName){

        for (Game game : _currentGames){
            if (game.getName().equals(gameName)){
                return true;
            }
        }

        return false;
    }

    /**
     * Attempts to add the player to the game
     *
     * @param gameName the name of the Game to add the player to
     * @param playerID the player ID that should be added to the Game
     *
     * @return an empty string if the Player was added to the Game, or an error explaination otherwise
     */
    public String addPlayerToGame(String gameName, String playerID){
        String returnValue = "";

        // get the game
        int gameIndex = -1;
        for (int count = 0; count < _currentGames.size(); count++){
            if (_currentGames.get(count).getName().equals(gameName)){
                gameIndex = count;
                break;
            }
        }

        if (gameIndex == -1){
            // the game was not found
            return "The game \"" + gameName + "\" could not be found";
        }

        // get the player
        int playerIndex = -1;
        for (int count = 0; count < _loggedInPlayers.size(); count++){
            if (_loggedInPlayers.get(count).getPlayerID().equals(playerID)){
                playerIndex = count;
                break;
            }
        }

        if (playerIndex == -1){
            return "The player \"" + playerID + "\" could not be found";
        }

        // add the player to the game
        if (_currentGames.get(gameIndex).join(_loggedInPlayers.get(playerIndex))){
            returnValue = "";
        }
        else {
            returnValue = "The player \"" + playerID + "\" was not added to \"" + gameName + "\"";
        }

        return returnValue;
    }

    /**
     * Returns an array of Commands that the Player needs to execute
     *
     * @param playerID the Player to return Commands for
     * @param commandIndex the index of the first command to return
     *
     * @return an array of Commands for the Player to execute
     */
    public ICommand[] getPlayerCommands(String playerID, int commandIndex){

        ICommand[] returnValue = new ICommand[]{};

        // get the Player for the player ID
        Player player = null;
        for (Player loggedIn : _loggedInPlayers){
            if (loggedIn.getPlayerID().equals(playerID)){
                player = loggedIn;
                break;
            }
        }

        if (player == null){
            // the player does not exist
            return returnValue;
        }

        // get the Game for the Player
        /*Game game = null;
        for (Game current : _currentGames){
            if (current.hasPlayer(player)){
                game = current;
                break;
            }
        }*/
        Game game = getGameForPlayer(player);

        if (game == null){
            // the Player is not part of any Game
            return returnValue;
        }

        // get the Commands from the Game
        returnValue = game.getCommands(commandIndex);

        //System.out.println(toString());

        return returnValue;
    }

    public String endTurn(String playerID){
        Game gameForPlayer = getGameForPlayer(playerID);

        if (gameForPlayer != null){
            return gameForPlayer.endTurn(playerID);
        }
        else {
            return "The player does not belong to a game.";
        }
    }

    /**
     * Attempts to claim the route for the player
     *
     * @param playerID the player ID of the player claiming the route
     * @param routeID the route ID of the route being claimed
     * @param cardsUsed the cards that were used to claim the route
     *
     * @return a String with any error that occurred
     */
    public String claimRoute(String playerID, String routeID, List<TrainCard> cardsUsed){
        Game currGame = getGameForPlayer(playerID);

        if (currGame != null){
            return currGame.claimRoute(playerID, routeID, cardsUsed);
        }
        else {
            return "The player does not belong to a game.";
        }
    }

    /**
     * Requests destination cards for the specified player
     *
     * @param playerID the player ID of the player requesting destination cards
     *
     * @return a blank String if the request was successful, or an error message otherwise
     */
    public String requestDestCards(String playerID){
        Game currGame = getGameForPlayer(playerID);

        if (currGame != null){
            return currGame.requestDestCards(playerID);
        }
        else {
            return "The player is not in any game.";
        }
    }

    /**
     * Tells the server what destination cards the user is choosing to keep
     *
     * @param playerID the player ID that is keeping the cards
     * @param keep the DestCards that are being kept
     *
     * @return any error that occurred in claiming the destination cards
     */
    public String keepDestCards(String playerID, List<DestCard> keep){
        Game currGame = getGameForPlayer(playerID);

        if (currGame != null){
            return currGame.keepDestCards(playerID, keep);
        }
        else {
            return "The player is not in any game.";
        }
    }

    /**
     * Selects a TrainCard for the player
     * @param playerID the player ID of the player claiming the TrainCard
     * @param card the TrainCard that is being selected
     * @param pickFromFaceUp whether the TrainCard is coming from the faceup Deck or not
     *
     * @return any error that occurred in selecting a TrainCard
     */
    public String selectTrainCard(String playerID, TrainCard card, Boolean pickFromFaceUp) {
        Game currGame = getGameForPlayer(playerID);

        if (currGame != null){
            return currGame.selectTrainCard(playerID, card, pickFromFaceUp);
        }
        else {
            return "The player is not in any game.";
        }
    }

    /**
     * Adds a chat message to the history for the appropriate game
     *
     * @param playerID the player ID of the player that sent the message
     * @param message the message text
     *
     * @return an error String from submitting the message, a blank String if there were no errors
     */
    public boolean addChatCommand(String playerID, String message){
        Game currGame = getGameForPlayer(playerID);

        if (currGame != null){
            currGame.addChatCommand(playerID, message);
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Returns whether a player is in a game
     * @param playerID the player ID of the player in question
     *
     * @return true if there is a Game that involves the player, false otherwise
     */
    public boolean isPlayerInGame(String playerID){
        return (getGameForPlayer(playerID) != null);
    }

    /**
     * Returns the name of the game that the player is in, or a blank string if there is no game for the player
     *
     * @param playerID the player ID to use to search
     *
     * @return the name of the game associated with the player, or a blank string if there is no such game
     */
    public String getGameNameForPlayerID(String playerID){
        Game game = getGameForPlayer(playerID);

        if (game != null){
            return game.getName();
        }
        else {
            return "";
        }
    }

    /**
     * Returns the bytes for the requested game, or an empty array if there is no such game
     *
     * @param gameName the name of the game to look for
     *
     * @return an array of bytes representing the Game, or an emtpy array if there was no such game
     */
    public byte[] getGameBytes(String gameName){
        Game currGame = null;

        for (int count = 0; count < _currentGames.size(); count++){
            if (_currentGames.get(count).getName().equals(gameName)){
                currGame = _currentGames.get(count);
                break;
            }
        }

        if (currGame != null){
            return currGame.getSerialized();
        }
        else {
            return new byte[0];
        }
    }

    /**
     * Returns a String representation of the sever model
     *
     * @return a String representation of the server model
     */
    @Override
    public String toString(){
        String returnString = "";

        returnString += "Registered Players:\n";

        for (int count = 0; count < _registeredPlayers.size(); count++){
            String currPlayerString = _registeredPlayers.get(count).toString();

            returnString += "\t[Player " + count + "]:\n\t\t";
            returnString += currPlayerString.replace("\n", "\n\t\t");
            returnString += "\n";
        }

        returnString += "Logged In Players:\n";
        for (int count = 0; count < _loggedInPlayers.size(); count++){
            String currPlayerString = _loggedInPlayers.get(count).toString();

            returnString += "\t[Player " + count + "]:\n\t\t";
            returnString += currPlayerString.replace("\n", "\n\t\t");
            returnString += "\n";
        }

        returnString += "Games:\n";
        for (int count = 0; count < _currentGames.size(); count++){
            String currGame = _currentGames.get(count).toString();

            returnString += "\t[Game " + count + "]:\n\t\t";
            returnString += currGame.replace("\n", "\n\t\t");
            returnString += "\n";
        }

        return returnString;
    }

    /**
     * Returns the game that has the player identified by the player ID
     *
     * @param playerID the player ID for the player
     *
     * @return the Game that the player is in, or null if there is no such game found
     */
    private Game getGameForPlayer(String playerID) {

        // get the Player object for the player ID
        Player currPlayer = null;
        for (Player player : _loggedInPlayers){
            if (player.getPlayerID().equals(playerID)){
                currPlayer = player;
                break;
            }
        }

        // return the game for the player
        return getGameForPlayer(currPlayer);
    }
    /**
     * Returns the game that has the player identified by the player Object
     *
     * @param currPlayer the Player object that represents the player
     *
     * @return the Game that the player is in, or null if there is no such game found
     */
    private Game getGameForPlayer(Player currPlayer){

        // verify that the player exists
        if (currPlayer == null){
            // the player does not exist
            return null;
        }

        // get the game that the player is in
        for (Game current : _currentGames){
            if (current.hasPlayer(currPlayer)){
                // the game was found
                return current;
            }
        }

        // if this point is reached, there was no game with the player in it
        return null;
    }


    public void restoreDeck(String gameName, Game.DeckShufflType deckType, Deck restoreDeck){
        // get the correct game
        Game modifyGame = null;
        for (int count = 0; count < _currentGames.size(); count++){
            if (_currentGames.get(count).getName().equals(gameName)){
                modifyGame = _currentGames.get(count);
                break;
            }
        }

        // restore the deck
        if (modifyGame != null){
            modifyGame.restoreDeck(deckType, restoreDeck);
        }
    }
}
