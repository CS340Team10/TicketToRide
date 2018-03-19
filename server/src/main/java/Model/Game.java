package Model;

import java.util.ArrayList;
import java.util.List;

import Services.ClientCommandFactory;
import common.Deck;
import common.DestCard;
import common.GameRoutes;
import common.ICard;
import common.ICommand;
import common.PlayerAttributes;
import common.PlayerPointSummary;
import common.Route;
import common.TrainCard;

/**
 * Created by Brian on 2/1/18.
 */

public class Game {

    /**
     * The number of cards that are face up at any point in the game
     */
    public final static int FACE_UP_CARD_MAX = 5;

    /**
     * The number of train cards initially dealt to each player
     */
    public final static int BEGINNING_TRAIN_HAND_COUNT = 4;

    /**
     * The number of destination cards dealt to each player
     */
    public final static int DESTINATION_CARD_DEAL = 3;

    /**
     * The number of wild train cards in the deck
     */
    public final static int WILD_TRAIN_CARDS_COUNT = 14;

    /**
     * The number of normal train cards of each color in the deck
     */
    public final static int NORMAL_TRAIN_CARDS_COUNT = 12;

    /**
     * The maximum number of wild cards that are available in the face up deck
     */
    public final static int MAX_WILDS_AVALABLE = 2;

    /**
     * The minimum number of train cars that can be held by a player to continue game play
     */
    public final static int MIN_TRAIN_CARS_IN_GAME = 3;

    private CommandHistory _gameHistory = new CommandHistory();
    private List<Player> _players = new ArrayList<Player>();
    private int currPlayerTurn = 0;
    private List<Route> _routes = GameRoutes.getAllRoutes();

    // set up the decks of cards to use
    private Deck _trainCards = new Deck();
    private Deck _destinationCards = new Deck();
    private Deck _faceUpTrainCards = new Deck();
    private Deck _discardedTrainCards = new Deck();
    private Deck _discardedDestinationCards = new Deck();

    private String _name;
    private int _numOfPlayers;
    private boolean _didStart = false;

    // keeps track of whether the game is in the last round
    private boolean _isLastRound = false;

    // keeps track of which player triggered the last round
    private int _endGameTriggerPlayerIndex = -1;

    public Game(String name, int numOfPlayers) {
        _name = name;
        _numOfPlayers = numOfPlayers;

        // ------------------------------------------------ //
        // initialize the decks
        // ------------------------------------------------ //

        // initialize the train cards
        setUpTrainCards();

        // initialize the destination cards
        setUpDestCards();

    }

    /**
     * If the given player is not already participating in the game, then the player is
     * added to the game
     *
     * @param player the player to add to the game
     * @return true if the Player was added to the game, false otherwise
     */
    public boolean join(Player player) {

        if (_numOfPlayers - _players.size() > 0) {

            if (!_players.contains(player)) {
                // the player has not been added to the game yet
                _players.add(player);

                // check if the game should start
                if (_players.size() == _numOfPlayers) {
                    // start the game
                    startGame();
                }
                return true;
            } else {
                // the player is already in the game
                return false;
            }
        }

        // there are already enough players
        return false;
    }

    /**
     * @return whether the game has already started or not
     */
    public boolean hasStarted() {
        return _didStart;
    }

    /**
     * If there are enough players (greater than or equal to the required player count)
     * tell the game that it has started
     * But if there are not enough players, the game will not start and the method will return false
     *
     * @return whether the game successfully started
     */
    public boolean startGame() {
        if (_players.size() >= _numOfPlayers) {

            // give the players their train and destination cards
            for (int playerCount = 0; playerCount < _players.size(); playerCount++){

                Player currPlayer = _players.get(playerCount);
                currPlayer.setColor(PlayerAttributes.Color.values()[playerCount]);
                String playerID = currPlayer.getPlayerID();

                // set up the PlayerAttributes to send back for each player
                PlayerAttributes currPlayerAttributes = getPlayerAttributes(playerCount);

                _gameHistory.addCommand(ClientCommandFactory.createPlayerUpdatedCommand(currPlayerAttributes));

                // give train cards
                for (int cardCount = 0; cardCount < BEGINNING_TRAIN_HAND_COUNT; cardCount++){

                    giveTrainCard(playerID, null, false);

                    /*TrainCard currCard = (TrainCard)_trainCards.drawCard();
                    currPlayer.takeTrainCard(currCard);

                    _gameHistory.addCommand(ClientCommandFactory.createTrainCardChosenCommand(playerID, currCard));*/
                }

                // give destination cards
                giveDestCards(playerID);
            }

            // set up the face-up cards
            repopulateFaceUpCards();


            // start the game
            _gameHistory.addStartGameCommand();
            _didStart = true;

            // notify the first player that it is their turn
            _gameHistory.addCommand(ClientCommandFactory.createTurnBeganCommand(_players.get(0).getPlayerID()));
        }
        return _didStart;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public void setRequiredPlayerCount(int requiredPlayerCount) {
        _numOfPlayers = requiredPlayerCount;
    }

    public int getRequiredPlayerCount() {
        return _numOfPlayers;
    }

    public CommandHistory getGameHistory() {
        return _gameHistory;
    }

    /**
     * Returns whether the Player is part of this Game
     *
     * @param player the Player to check for
     * @return true if the Player is found, false otherwise
     */
    public boolean hasPlayer(Player player) {
        return _players.contains(player);
    }

    /**
     * Returns the commands that have not been sent to the Player yet
     *
     * @param commandIndex the index of the first ICommand to return
     *
     * @return
     */
    public ICommand[] getCommands(int commandIndex) {

        ICommand[] returnValue = new ICommand[]{};

        // get the commands from the game
        List<ICommand> commands = _gameHistory.historyFrom(commandIndex);
        returnValue = new ICommand[commands.size()];

        for (int count = 0; count < commands.size(); count++) {
            returnValue[count] = commands.get(count);
        }

        // return the commands
        return returnValue;
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
        if (!isPlayersTurn(playerID)){
            return "It is not your turn.";
        }
        else {
            // verify that the route has not been claimed
            Route currRoute = getRoute(routeID);

            if (currRoute.getOwnedByPlayerID() == null){

                // check that there are enough cards
                if (currRoute.getRouteLength() != cardsUsed.size()){
                    return "You did not provide enough cards to claim that route.";
                }

                // check that the cards are valid
                boolean cardsValid = true;
                for (TrainCard card : cardsUsed){
                    if ((card.getColor() == TrainCard.Colors.wildcard) || (card.getColor() == currRoute.getPathColor())){
                        // the card is valid
                    }
                    else {
                        // the card is not valid
                        cardsValid = false;
                        break;
                    }
                }

                if (cardsValid) {

                    Player currPlayer = getPlayer(playerID);

                    // make sure that the player has enough train cars to claim the route
                    if (currPlayer.getTrainCars() < cardsUsed.size()){
                        return "You do not have enough trains left to claim that route.";
                    }

                    // claim the route for the player and discard the cards
                    if (currPlayer.useTrainCars(cardsUsed.size())) {

                        // mark the rout eas claimed
                        currRoute.setOwnedByPlayerID(playerID);

                        // remove the used cards from the player
                        currPlayer.useTrainCards(cardsUsed);

                        // discard the used cards
                        _discardedTrainCards.addCards(cardsUsed);

                        // award the player points for the route
                        currPlayer.incrementPoints(currRoute.getPointValue());
                        _gameHistory.addCommand(ClientCommandFactory.createPlayerUpdatedCommand(getPlayerAttributes(currPlayer)));

                        // inform the clients that the route has been claimed
                        _gameHistory.addCommand(ClientCommandFactory.createRouteClaimedCommand(playerID, routeID, cardsUsed));

                        return "";
                    }
                    else {
                        return "You do not have enough trains left to claim that route.";
                    }
                }
                else {
                    return "The supplied cards cannot claim that route.";
                }
            }
            else {
                return "That route has already been claimed.";
            }
        }
    }


    /**
     * Ends the turn for the player specified by the player ID
     *
     * @param playerID the player ID of the player that is ending their turn
     *
     * @return a String with any possible error messages
     */
    public String endTurn(String playerID){

        if (!isPlayersTurn(playerID)){
            return "It is not your turn.";
        }
        else {
            // move the gameplay turn to the next players
            incrementCurrPlayer();
            return "";
        }
    }

    /**
     * Returns new destination cards for a player
     *
     * @param playerID the player ID of the player to recieve the new cards
     *
     * @return a String with any possible error messages
     */
    public String requestDestCards(String playerID){

        // verify that it is the players turn
        if (!isPlayersTurn(playerID)){
            return "It is not your turn.";
        }
        else {
            return giveDestCards(playerID);
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
        Player currPlayer = getPlayer(playerID);

        List<? extends ICard> returnCards = currPlayer.acceptDestinationCards(keep);

        _gameHistory.addCommand(ClientCommandFactory.createDestCardsChosenCommand(playerID, keep));

        return "";
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
        if (!isPlayersTurn(playerID)){
            return "It is not your turn.";
        }
        else {
            return giveTrainCard(playerID, card, pickFromFaceUp);
        }
    }

    /**
     * Adds a chat command to the queue for this Game
     *
     * @param playerID the player ID of the player that sent the message
     * @param message the message to send out
     */
    public void addChatCommand(String playerID, String message){
        _gameHistory.addCommand(ClientCommandFactory.createChatCommand(playerID, message));
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == getClass()) {
            Game g = (Game) o;
            return _name.equals(g.getName())
                    && _numOfPlayers == g.getRequiredPlayerCount()
                    && _gameHistory.equals(g.getGameHistory())
                    && _didStart == g.hasStarted();
        }
        return false;
    }

    @Override
    public String toString() {
        String returnString = "";

        returnString += "Game Name:\t\t\t" + _name + "\n";
        returnString += "Required Players:\t" + _numOfPlayers + "\n";
        returnString += "Empty Spots:\t\t" + (_numOfPlayers - _players.size()) + "\n";
        returnString += "Started:\t\t\t" + hasStarted() + "\n";
        returnString += "Players:\n";

        for (int count = 0; count < _players.size(); count++) {
            String currPlayerString = _players.get(count).toString();
            returnString += "\t[Player " + count + "]\n";
            returnString += "\t\t" + currPlayerString.replace("\n", "\n\t\t") + "\n";
        }

        returnString += "Commands:\n";
        List<ICommand> commands = _gameHistory.historyFrom(0);
        for (int count = 0; count < commands.size(); count++) {
            String currCommandString = commands.get(count).toString();
            returnString += "\t[Command " + count + "]\n";
            returnString += "\t\t" + currCommandString.replace("\n", "\n\t\t") + "\n";
        }

        return returnString;
    }


    /**
     * Returns the player identified by the player ID
     *
     * @param playerID the player ID for the requested Player
     *
     * @return the Player Object represented by the player ID, or null if the Player cannot be found
     */
    private Player getPlayer(String playerID){
        for (int count = 0; count < _players.size(); count++){
            if (_players.get(count).getPlayerID().equals(playerID)){
                return _players.get(count);
            }
        }

        return null;
    }

    /**
     * Returns the Route specified by the ID
     *
     * @param routeID the route ID to look for
     *
     * @return the Route specified by the ID, or null if no such Route exists
     */
    private Route getRoute(String routeID){
        for (Route route: _routes){
            if (route.getRouteID().equals(routeID)){
                return route;
            }
        }

        return null;
    }

    /**
     * Returns whether it is currently the specified player's turn
     *
     * @param playerID the player ID of the player in question
     *
     * @return true if it is the specified player's turn, false otherwise
     */
    private boolean isPlayersTurn(String playerID){
        if (!_didStart){
            // the game has not started, so it is nobody's turn
            return false;
        }

        // return whether the requested player is the active player
        return (_players.get(currPlayerTurn).getPlayerID().equals(playerID));
    }

    /**
     * Sets the turn of the next player and triggers a command to notify the clients
     */
    private void incrementCurrPlayer(){

        // ----------------------------------------------------------------------------
        // check if the game state needs to change
        // ----------------------------------------------------------------------------
        // check how many train cars the former "currPlayerTurn" has
        if (!_isLastRound){
            if (_players.get(currPlayerTurn).getTrainCars() < MIN_TRAIN_CARS_IN_GAME){
                // the player that just ended their turn triggered the last round
                _isLastRound = true;
                _gameHistory.addCommand(ClientCommandFactory.createLastRoundBeganCommand());
                _endGameTriggerPlayerIndex = currPlayerTurn;
            }
        }
        else {
            if (currPlayerTurn == _endGameTriggerPlayerIndex){
                // the game is over
                endGame();
                return;
            }
        }


        if (currPlayerTurn == _players.size() - 1){
            // reset to 0
            currPlayerTurn = 0;
        }
        else {
            currPlayerTurn++;
        }

        // notify the clients that the turn has changed
        _gameHistory.addCommand(ClientCommandFactory.createTurnBeganCommand(_players.get(currPlayerTurn).getPlayerID()));
    }

    /**
     * Sets up the original train cards deck
     */
    private void setUpTrainCards() {
        for (TrainCard.Colors color : TrainCard.Colors.values()) {
            int cardCount = 0;

            if (color == TrainCard.Colors.wildcard) {
                // there are 14 wild cards
                cardCount = WILD_TRAIN_CARDS_COUNT;
            } else {
                // there are only 12 of these cards
                cardCount = NORMAL_TRAIN_CARDS_COUNT;
            }

            for (int count = 0; count < cardCount; count++) {
                _trainCards.addCard(new TrainCard(color));
            }
        }

        _trainCards.shuffle();

    }

    /**
     * Sets up the original destination cards deck
     */
    private void setUpDestCards(){
        ArrayList<String> startCity = new ArrayList<String>();
        ArrayList<String> endCity = new ArrayList<String>();
        ArrayList<Integer> points = new ArrayList<Integer>();

        // define the destination card info
        startCity.add("Denver");
        endCity.add("El Paso");
        points.add(4);

        startCity.add("Kansas City");
        endCity.add("Houston");
        points.add(5);

        startCity.add("New York");
        endCity.add("Atlanta");
        points.add(6);

        startCity.add("Chicago");
        endCity.add("New Orleans");
        points.add(7);

        startCity.add("Calgary");
        endCity.add("Salt Lake City");
        points.add(7);

        startCity.add("Sault Ste Marie");
        endCity.add("Nashville");
        points.add(8);

        startCity.add("Helena");
        endCity.add("Los Angeles");
        points.add(8);

        startCity.add("Duluth");
        endCity.add("Houston");
        points.add(8);

        startCity.add("Seattle");
        endCity.add("Los Angeles");
        points.add(9);

        startCity.add("Chicago");
        endCity.add("Santa Fe");
        points.add(9);

        startCity.add("Montreal");
        endCity.add("Atlanta");
        points.add(9);

        startCity.add("Sault Ste Marie");
        endCity.add("Oklahoma City");
        points.add(9);

        startCity.add("Duluth");
        endCity.add("El Paso");
        points.add(10);

        startCity.add("Toronto");
        endCity.add("Miami");
        points.add(10);

        startCity.add("Dallas");
        endCity.add("New York");
        points.add(11);

        startCity.add("Winnipeg");
        endCity.add("Little Rock");
        points.add(11);

        startCity.add("Denver");
        endCity.add("Pittsburgh");
        points.add(11);

        startCity.add("Portland");
        endCity.add("Phoenix");
        points.add(11);

        startCity.add("Boston");
        endCity.add("Miami");
        points.add(12);

        startCity.add("Winnipeg");
        endCity.add("Houston");
        points.add(12);

        startCity.add("Montreal");
        endCity.add("New Orleans");
        points.add(13);

        startCity.add("Vancouver");
        endCity.add("Santa Fe");
        points.add(13);

        startCity.add("Calgary");
        endCity.add("Phoenix");
        points.add(13);

        startCity.add("Los Angeles");
        endCity.add("Chicago");
        points.add(16);

        startCity.add("Portland");
        endCity.add("Nashville");
        points.add(17);

        startCity.add("San Francisco");
        endCity.add("Atlanta");
        points.add(17);

        startCity.add("Los Angeles");
        endCity.add("Miami");
        points.add(20);

        startCity.add("Vancouver");
        endCity.add("Montreal");
        points.add(20);

        startCity.add("Los Angeles");
        endCity.add("New York");
        points.add(21);

        startCity.add("Seattle");
        endCity.add("New York");
        points.add(22);

        for (int count = 0; count < startCity.size(); count++){
            _destinationCards.addCard(new DestCard(startCity.get(count), endCity.get(count), points.get(count)));
        }

        _destinationCards.shuffle();
    }

    /**
     * Resets the face up cards to have 5 cards showing and triggers an update command
     */
    private void repopulateFaceUpCards(){

        boolean triggerCommand = false;

        for (int count = _faceUpTrainCards.size(); count < FACE_UP_CARD_MAX; count++){
            triggerCommand = true;
            _faceUpTrainCards.addCard(_trainCards.drawCard());
        }

        // check if there are three wild cards in the deck
        int numWilds = 0;
        ArrayList<TrainCard> faceUpList = new ArrayList<TrainCard>();
        for (int count = 0; count < _faceUpTrainCards.size(); count++){

            TrainCard tempCard = (TrainCard)_faceUpTrainCards.viewCard(count);
            faceUpList.add(tempCard);

            if (tempCard.getColor() == TrainCard.Colors.wildcard){
                numWilds++;
            }
        }

        if (numWilds > MAX_WILDS_AVALABLE){
            // there are too many wilds, so discard all of them and redraw
            _faceUpTrainCards.copyToDeck(_discardedTrainCards);
            _faceUpTrainCards.clear();
            repopulateFaceUpCards();
        }

        // update the face up cards on the clients
        _gameHistory.addCommand(ClientCommandFactory.createTrainCardDeckUpdatedCommand(faceUpList, _trainCards.size()));

    }

    /**
     * Enforces all of the rules associated with drawing TrainCards
     *
     * @param playerID the player ID of the player selecting a TrainCard
     * @param card the TrainCard to draw from the face-up Deck
     * @param pickFromFaceUp whether the card is coming from the face-up Deck
     *
     * @return a String with any errors that occurred
     */
    private String giveTrainCard(String playerID, TrainCard card, Boolean pickFromFaceUp) {

        Player currPlayer = getPlayer(playerID);

        if (pickFromFaceUp){
            card = (TrainCard)_faceUpTrainCards.drawCard(card);

            if (card == null){
                return "That card is not in the face-up deck.";
            }
        }
        else {
            // draw from the deck


            if (_trainCards.size() < 1) {
                // shuffle the discarded train cards
                _discardedTrainCards.copyToDeck(_trainCards);
                _discardedTrainCards.clear();
                _trainCards.shuffle();
            }

            if (_trainCards.size() < 1) {
                // there are not enough cards
                return "There are not enough cards to choose from. Please try a different action.";
            }

            card = (TrainCard)_trainCards.drawCard();
            currPlayer.takeTrainCard(card);
        }

        // trigger the event for the clients
        _gameHistory.addCommand(ClientCommandFactory.createTrainCardChosenCommand(playerID, card));

        // make sure that the face up cards is full
        repopulateFaceUpCards();

        return "";
    }

    /**
     * Sends new destination cards to the player to choose from
     *
     * @param playerID the player ID of the player to receive new cards
     *
     * @return a String with any errors encountered
     */
    private String giveDestCards(String playerID){

        Player currPlayer = getPlayer(playerID);

        if (currPlayer == null){
            return "There is no such player.";
        }

        // ensure that there are enough destination cards left
        if (_destinationCards.size() < DESTINATION_CARD_DEAL){
            _discardedDestinationCards.copyToDeck(_destinationCards);
            _discardedDestinationCards.clear();
            _destinationCards.shuffle();
        }

        if (_destinationCards.size() < DESTINATION_CARD_DEAL){
            // there are not enough cards left on the server
            return "There are not enough cards to choose from. Please try a different action.";
        }

        ArrayList<DestCard> destCards = new ArrayList<DestCard>();
        for (int cardCount = 0; cardCount < DESTINATION_CARD_DEAL; cardCount++){
            destCards.add((DestCard)_destinationCards.drawCard());
        }

        currPlayer.offerDestinationCards(destCards);

        _gameHistory.addCommand(ClientCommandFactory.createOfferDestCardsCommand(playerID, destCards));
        _gameHistory.addCommand(ClientCommandFactory.createDestCardDeckUpdatedCommand(_destinationCards.size()));

        // everything was successful
        return "";
    }

    /**
     * Creates a PlayerAttribute Object for the specified Player
     *
     * @param playerIndex the index of the Player to return for
     *
     * @return a PlayerAttributes Object for the specified Player
     */
    private PlayerAttributes getPlayerAttributes(int playerIndex) {
        Player currPlayer = _players.get(playerIndex);

        return getPlayerAttributes(currPlayer);
    }
    /**
     * Creates a PlayerAttribute Object for the specified Player
     *
     * @param currPlayer the Player to return the PlayerAttributes for
     *
     * @return a PlayerAttributes Object for the specified Player
     */
    private PlayerAttributes getPlayerAttributes(Player currPlayer){

        PlayerAttributes returnValue = new PlayerAttributes();
        returnValue.playerId = currPlayer.getPlayerID();
        returnValue.username = currPlayer.getUsername();
        returnValue.color = currPlayer.getColor();
        returnValue.points = currPlayer.getPoints();
        returnValue.destCardNum = currPlayer.getDestCardsNum();
        returnValue.trainCardNum = currPlayer.getTrainCardsNum();
        returnValue.trainCarNum = currPlayer.getTrainCars();

        return returnValue;
    }

    /**
     * Ends the game and calculates the points for each player
     */
    private void endGame(){

        // get the destination card points
        for (Player currPlayer : _players){
            currPlayer.calculateFinalPoints(_routes);
        }

        // determine who has the longest route
        calculateLongestRoute();

        // determine the winner
        determineWinner();

        ArrayList<PlayerPointSummary> playerPoints = new ArrayList<PlayerPointSummary>();
        for (Player currPlayer : _players){
            playerPoints.add(currPlayer.getPlayerPoints());
        }

        _gameHistory.addCommand(ClientCommandFactory.createGameOverStatisticsCommand(playerPoints));
    }

    /**
     * Calculates which player has the longest route and awards them bonus points
     */
    private void calculateLongestRoute(){

        // get the player(s) that have the longest route
        List<Integer> longestPathIndexes = PlayerGraph.calculateLongestPath(_players, _routes);
        for (int index : longestPathIndexes) {
            // apply the bonus
            _players.get(index).applyLongestPathBonus();
        }
    }

    /**
     * Determines which Player is the winner
     */
    private void determineWinner(){

        int highestPoints = 0;
        List<Integer> winnerIndexes = new ArrayList<Integer>();
        int finalWinnerIndex = -1;

        for (int count = 0; count < _players.size(); count++){
            Player currPlayer = _players.get(count);

            int currPoints = currPlayer.getPlayerPoints().getTotalPoints();

            if (currPoints == highestPoints) {
                winnerIndexes.add(count);
            }
            if (currPoints > highestPoints) {
                highestPoints = currPoints;
                winnerIndexes.clear();
                winnerIndexes.add(count);
            }
        }

        if (winnerIndexes.size() > 1){
            // there was a tie, so go to the destination cards to determine the winner

            int mostDestCardsFinished = -1;
            List<Integer> destWinnerIndexes = new ArrayList<Integer>();

            for (int winnerIndex : winnerIndexes){
                int currDestCardCount = _players.get(winnerIndex).getCompletedDestCardCount();

                if (currDestCardCount == mostDestCardsFinished){
                    // the number of finished dest cards is the same
                    destWinnerIndexes.add(winnerIndex);
                }
                else if (currDestCardCount > mostDestCardsFinished){
                    // this player finished more destination cards
                    mostDestCardsFinished = currDestCardCount;
                    destWinnerIndexes.clear();
                    destWinnerIndexes.add(winnerIndex);
                }
            }

            if (destWinnerIndexes.size() > 1){

                // there was still a tie, so go to the Longest Route Bonus to determine the winner

                List<Integer> longestRouteWinnerIndex = new ArrayList<Integer>();
                for (int destWinnerIndex : destWinnerIndexes){
                    if (_players.get(destWinnerIndex).getPlayerPoints().getLongestRoutePoints() > 0){
                        longestRouteWinnerIndex.add(destWinnerIndex);
                    }
                }

                if (longestRouteWinnerIndex.size() > 1){

                    // if there is still a tie at this point, then choose the first player in the list as the winner
                    finalWinnerIndex = longestRouteWinnerIndex.get(0);
                }
                else {
                    // chose the only winner
                    finalWinnerIndex = longestRouteWinnerIndex.get(0);
                }

            }
            else {
                // there is only one winner at this point
                finalWinnerIndex = destWinnerIndexes.get(0);
            }
        }
        else {
            // there were no ties at all, so there is only one winner
            finalWinnerIndex = winnerIndexes.get(0);
        }

        _players.get(finalWinnerIndex).setWinner();

    }
}
