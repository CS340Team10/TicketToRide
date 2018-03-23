package Model;

import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.List;

import common.Deck;
import common.DestCard;
import common.ICard;
import common.PlayerAttributes;
import common.PlayerPointSummary;
import common.Route;
import common.TrainCard;

/**
 * Created by Brian on 2/1/18.
 */

public class Player {

    private String _username;
    private String _password;
    private String _playerID;
    private PlayerAttributes.Color _color;
    private int _gameHistoryIndex;
    private Deck _trainCards = new Deck();

    // this Deck holds the DestCards that have been accepted by the player
    private Deck _destinationCards = new Deck();

    // this deck holds the DestCards that have been offered to the player
    private Deck _tempDestinationCards = new Deck();

    private int _currPoints = 0;
    private int _currTrainCars = 45;

    // these variables are used at the end of the game to determine the score of the Player
    private PlayerPointSummary _pointSummary = null;
    private Deck completedDestCards = new Deck();

    public Player(String username, String password){
        _username = username;
        _password = password;
        _playerID = "";
        _gameHistoryIndex = 0;
    }


    /**
     * Returns the username for the Player
     *
     * @return the username for the Player
     */
    public String getUsername() {
        return _username;
    }

    /**
     * Sets the username for the Player
     *
     * @param username the new username for the Player
     */
    public void setUsername(String username){
        _username = username;
    }

    /**
     * Returns the password for the Player
     *
     * @return the password for the Player
     */
    public String getPassword() {
        return _password;
    }

    /**
     * Sets the password for the Player
     *
     * @param password the new password for the Player
     */
    public void setPassword(String password){
        _password = password;
    }

    /**
     * Returns the player ID for the Player
     *
     * @return the player ID for the Player
     */
    public String getPlayerID() {
        return _playerID;
    }

    /**
     * Sets the Player ID for the Player
     *
     * @param playerID the new player ID for the Player
     */
    public void setPlayerID(String playerID){
        _playerID = playerID;
    }

    /**
     * Sets the Color for the Player
     */
    public void setColor(PlayerAttributes.Color color){
        _color = color;
    }

    /**
     * Returns the color of the Player
     *
     * @return the color of the Player
     */
    public PlayerAttributes.Color getColor(){
        return _color;
    }

    public int getPoints(){
        return _currPoints;
    }

    /**
     * Increment the number of points for the Player
     *
     * @param newPoints the new points to add to the Player
     */
    public void incrementPoints(int newPoints){
        _currPoints += newPoints;
    }

    public int getDestCardsNum(){
        return _destinationCards.size();
    }

    public int getTrainCardsNum(){
        return _trainCards.size();
    }

    public int getGameHistoryIndex(){
        return _gameHistoryIndex;
    }

    public void setGameHistoryIndex(int historyIndex){
        _gameHistoryIndex = historyIndex;
    }


    /**
     * Adds a train card to the Deck
     *
     * @param newCard the new TrainCard to add to the Deck
     */
    public void takeTrainCard(TrainCard newCard){
        _trainCards.addCard(newCard);
    }

    /**
     * Uses the TrainCard
     *
     * @param usedCard the TrainCard that was used
     */
    public void useTrainCard(TrainCard usedCard){
        _trainCards.drawCard(usedCard);
    }

    /**
     * Uses a list of TrainCards
     *
     * @param usedCards the TrainCards that were used
     */
    public void useTrainCards(List<TrainCard> usedCards){
        for (TrainCard card: usedCards){
            useTrainCard(card);
        }
    }


    /**
     * Offers the DestCards to the Player
     *
     * @param destCards a List of DestCards that are being offered to the Player
     */
    public void offerDestinationCards(List<? extends ICard> destCards){
        _tempDestinationCards.addCards(destCards);
    }

    /**
     * Sets the DestCards that were accepted by the Player
     *
     * @param acceptCards a List of DestCards that were accepted by the Player
     *
     * @return a List of ICards that were not accepted by the Player
     */
    public List<? extends ICard> acceptDestinationCards(List<? extends ICard> acceptCards){

        // go through the temporary destination cards and determine which cards should be returned
        for (int count = 0; count < acceptCards.size(); count++){
            // remove the accepted cards from the temp Deck
            _destinationCards.addCard(_tempDestinationCards.drawCard(acceptCards.get(count)));
        }

        // return any extra cards
        List<? extends ICard> returnCards = _tempDestinationCards.toList(ICard.class);
        _tempDestinationCards.clear();

        return returnCards;
    }

    /**
     * Returns the number of train cars currently available to this Player
     *
     * @return the number of train cars available to this player
     */
    public int getTrainCars(){
        return _currTrainCars;
    }

    /**
     * Decrements the available train cars for this Player
     *
     * @param numOfCars the number of train cars being used on a route
     */
    public boolean useTrainCars(int numOfCars){
        if (numOfCars < 0){
            // you cannot use less than 0 cars
            return false;
        }

        if (numOfCars > _currTrainCars){
            // you cannot use more train cars than you have
            return false;
        }

        // if this point is reached, then the train cars can be used
        _currTrainCars -= numOfCars;
        return true;

    }

    /**
     * Returns a PlayerPointSummary for the Player
     *
     * @param allRoutes a List of all available Routes in the game
     *
     * @return a PlayerPointSummary for the Player
     */
    public void calculateFinalPoints(List<Route> allRoutes){

        // clear this variable in case this method has been called before
        completedDestCards.clear();

        // determine the points from the routes
        int routePoints = 0;
        List<Route> routes = getPlayerRoutes(allRoutes);
        for (Route route : routes){
            routePoints += route.getPointValue();
        }

        // determine the points from the destination cards
        SimpleWeightedGraph g = PlayerGraph.getPlayerGraph(routes);
        ConnectivityInspector<String, DefaultWeightedEdge> ci = new ConnectivityInspector<>(g);
        int cardPoints = 0;

        Deck tempDestCards = new Deck();
        _destinationCards.copyToDeck(tempDestCards);
        for(int count = tempDestCards.size() - 1; count >= 0; count--){
            DestCard card = (DestCard)tempDestCards.viewCard(count);
            if(!(g.containsVertex(card.getStartCity()) && g.containsVertex(card.getEndCity()))) {
                continue;
            }
            if(ci.pathExists(card.getStartCity(), card.getEndCity())) {
                cardPoints += card.getPointValue();
                completedDestCards.addCard(tempDestCards.drawCard(count));
            }
        }

        // subtract out points for any destination card that was not completed
        for (int count = 0; count < tempDestCards.size(); count++){
            DestCard card = (DestCard)tempDestCards.viewCard(count);
            cardPoints -= card.getPointValue();
        }

        _pointSummary = new PlayerPointSummary(getPlayerID(), routePoints, cardPoints, 0, false);
    }

    /**
     * Returns the PlayerPointSummary for this Player
     *
     * @return the PlayerPointSummary for this Player
     */
    public PlayerPointSummary getPlayerPoints(){
        return _pointSummary;
    }

    /**
     * Applies the Longest Route bonus to the Player
     */
    public void applyLongestPathBonus(){
        _pointSummary.applyLongestPathBonus();
    }

    /**
     * Sets this Player as the winner
     */
    public void setWinner(){
        _pointSummary.setIsWinner(true);
    }

    /**
     * Returns the number of destination cards that were completed in the Game
     *
     * @return the number of destinatino cards that were completed in the Game
     */
    public int getCompletedDestCardCount(){
        return completedDestCards.size();
    }

    /**
     * Returns a list of all Routes claimed by the Player
     *
     * @param allRoutes a List of all Routes in the Game
     *
     * @return a List of all Routes claimed by the Player
     */
    public List<Route> getPlayerRoutes(List<Route> allRoutes){

        // get all of the routes claimed by the Player
        List<Route> routes = new ArrayList<Route>();
        for (Route route : allRoutes){
            if ((route.getOwnedByPlayerID() != null) && (route.getOwnedByPlayerID().equals(getPlayerID()))){
                routes.add(route);
            }
        }

        return routes;
    }

    /**
     * Returns whether the Object is equal to this Player
     *
     * @param compare an Object to compare to
     *
     * @return true if the Object is equal to the Player, false otherwise
     */
    @Override
    public boolean equals(Object compare){
        if (compare instanceof Player){
            // this is a Player Object, so compare the username and password
            Player comparePlayer = (Player)compare;

            if (!_username.equals(comparePlayer.getUsername())){
                // the usernames are not the same
                return false;
            }

            if (!_password.equals(comparePlayer.getPassword())){
                // the passwords are not the same
                return false;
            }

        }
        else {
            // the compare Object is not a Player, so they are not equal
            return false;
        }

        // if this point is reached, then the Objects are equal
        return true;
    }

    /**
     * Returns a String representation of the Player
     *
     * @return a String representation of the Player
     */
    @Override
    public String toString(){
        String returnString = "";

        returnString += "Username:\t\t\t" + _username + "\n";
        returnString += "Password:\t\t\t" + _password + "\n";
        returnString += "PlayerID:\t\t\t" + _playerID + "\n";
        returnString += "Train Cards:\t\t\n";
        returnString += _trainCards.toString();
        returnString += "Destination Cards:\t\n";
        returnString += _destinationCards.toString();
        returnString += "Temp Dest Cards:\t\t\n";
        returnString += _tempDestinationCards.toString();


        return returnString;
    }
}
