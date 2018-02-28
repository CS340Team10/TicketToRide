package Model;

import java.util.ArrayList;
import java.util.List;

import Services.ClientCommandFactory;
import common.Deck;
import common.DestCard;
import common.ICommand;
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
     * The number of non-colored train cards in the deck
     */
    public final static int UNCOLORED_TRAIN_CARDS_COUNT = 0;

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

    private CommandHistory _gameHistory = new CommandHistory();
    private List<Player> _players = new ArrayList<Player>();
    private List<Route> _routes = new ArrayList<Route>();

    // set up the decks of cards to use
    private Deck _trainCards = new Deck();
    private Deck _destinationCards = new Deck();
    private Deck _faceUpTrainCards = new Deck();
    private Deck _discardedTrainCards = new Deck();
    private Deck _discardedDestinationCards = new Deck();

    private String _name;
    private int _numOfPlayers;
    private boolean _didStart = false;

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

                String playerID = _players.get(playerCount).getPlayerID();

                // give train cards
                for (int cardCount = 0; cardCount < BEGINNING_TRAIN_HAND_COUNT; cardCount++){
                    _gameHistory.addCommand(ClientCommandFactory.createTrainCardChosenCommand(playerID, (TrainCard)_trainCards.drawCard()));
                }

                // give destination cards
                ArrayList<DestCard> destCards = new ArrayList<DestCard>();
                for (int cardCount = 0; cardCount < DESTINATION_CARD_DEAL; cardCount++){
                    destCards.add((DestCard)_destinationCards.drawCard());
                }
                _gameHistory.addCommand(ClientCommandFactory.createOfferDestCardsCommand(playerID, destCards));

            }

            // set up the face-up cards
            repopulateFaceUpCards();


            // start the game
            _gameHistory.addStartGameCommand();
            _didStart = true;
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
     * @param player the Player to use to retrieve the commands
     * @return
     */
    public ICommand[] getCommandsForPlayer(Player player) {

        ICommand[] returnValue = new ICommand[]{};

        // get the Player in the Game
        int playerIndex = -1;
        for (int count = 0; count < _players.size(); count++) {
            if (_players.get(count).equals(player)) {
                playerIndex = count;
            }
        }

        if (playerIndex == -1) {
            // the Player does not exist in the game
            return returnValue;
        }

        // get the commands from the game
        List<ICommand> commands = _gameHistory.historyFrom(_players.get(playerIndex).getGameHistoryIndex());
        returnValue = new ICommand[commands.size()];

        for (int count = 0; count < commands.size(); count++) {
            returnValue[count] = commands.get(count);
        }

        // get the new position of commands
        int commandIndex = 0;
        if (commands.size() > 0) {
            commandIndex = commands.size();
        }

        // increment the index of the commands
        commandIndex += _players.get(playerIndex).getGameHistoryIndex();

        // update the position of commands for the Player
        _players.get(playerIndex).setGameHistoryIndex(commandIndex);

        // return the commands
        return returnValue;
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


    private void setUpTrainCards() {
        for (TrainCard.Colors color : TrainCard.Colors.values()) {
            int cardCount = 0;
            if (color == TrainCard.Colors.none) {
                // do nothing with this color
                cardCount = UNCOLORED_TRAIN_CARDS_COUNT;
            } else if (color == TrainCard.Colors.wildcard) {
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

        _gameHistory.addCommand(ClientCommandFactory.createTrainCardDeckUpdatedCommand(faceUpList, _trainCards.size()));

    }
}
