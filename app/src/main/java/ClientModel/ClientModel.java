package ClientModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import common.Deck;
import common.DestCard;
import common.GameRoutes;
import common.PlayerAttributes;
import common.PlayerPointSummary;
import common.Route;
import common.TrainCard;

/**
 * Created by ephraimkunz on 2/3/18.
 * Modified by Joseph on 2/3/18
 */

public class ClientModel extends Observable
{
    private ClientModel() {}
    private static ClientModel _instance = new ClientModel();
    private Player user = new Player();
    private Game game = new Game();
    private List<String> available_games = new ArrayList<>();
    private List<Route> gameRoutes = GameRoutes.getAllRoutes();
    private ChatHistory chatHistory = new ChatHistory();
    private GameHistory gameHistory = new GameHistory();

    public static ClientModel getInstance()
    {
        return _instance;
    }

    public Game getGame() {
        return game;
    }

    public void startGame() {
        game.startGame();
        setChanged();
        notifyObservers();
    }

    public List<String> getAvailableGames() {
        return available_games;
    }

    /**
     * Sets available_games to any non-null List of strings holding the names of available games
     * If null value is passed in, then this method will do nothing
     * @param available_games the list holding the names of available games
     */
    public void setAvailableGames(List<String> available_games)
    {
        this.available_games = available_games;
        setChanged();
        notifyObservers();
    }

    public Player getUser() {
        return user;
    }

    public Route getRouteById(String routeId) {
        // Implement this
        for(Route route : gameRoutes)
        {
            if(route.getRouteID().equals(routeId))
            {
                return route;
            }
        }
        return null;
    }

    public void updatePlayer(PlayerAttributes player) {
        // From list of players, find player with player.playerId
        // Update all relevant fields

        Player thisPlayer;
        thisPlayer = getPlayerByID(player.playerId);

        if(thisPlayer == null) {
            // Add the player
            if (player.playerId.equals(user.getId())) {
                thisPlayer = user;
            } else {
                thisPlayer = new Player();
            }

            game.addPlayer(thisPlayer);
            thisPlayer.setId(player.playerId);
            thisPlayer.setTurnOrder(game.getPlayers().size() - 1);
        }

        thisPlayer.setUsername(player.username);

        Player.PlayerColors playerColor;
        switch(player.color)
        {
            case red:
                playerColor = Player.PlayerColors.red;
                break;
            case blue:
                playerColor = Player.PlayerColors.blue;
                break;
            case green:
                playerColor = Player.PlayerColors.green;
                break;
            case yellow:
                playerColor = Player.PlayerColors.yellow;
                break;
            case black:
                playerColor = Player.PlayerColors.black;
                break;
            default:
                playerColor = Player.PlayerColors.red;
                break;
        }
        thisPlayer.setColor(playerColor);
        thisPlayer.setPoints(player.points);
        thisPlayer.setTrainsLeft(player.trainCarNum);

        setChanged();
        notifyObservers();
    }

    public void playerTurnBegan(String playerId) {
        // Find player with this id
        // Set isMyTurn to true.
        // Set to false on all other players.

        for(Player p : game.getPlayers())
        {
            p.setMyTurn(false);
        }
        Player player = getPlayerByID(playerId);
        player.setMyTurn(true);


        setChanged();
        notifyObservers();
    }

    public void updateTrainCardDeck(List<TrainCard> visible, Integer invisible) {
        // Set visible list to visible
        // Set invisible count to invisible

        game.setFaceupTrainCards((ArrayList<TrainCard>) visible);
        game.setTrainCardDeckNum(invisible);

        setChanged();
        notifyObservers();
    }

    public void updateDestCardDeck(Integer invisible) {
        // Set invisible count to invisible

        game.setDestCardDeckNum(invisible);

        setChanged();
        notifyObservers();
    }

    public void setOfferedDestCards(List<DestCard> cards) {
        // Set offered cards here


        Deck deck = new Deck();
        for(DestCard card : cards)
            deck.addCard(card);
        user.setOfferedDestCards(deck);

        setChanged();
        notifyObservers();
    }

    public void setChosenDestCards(String playerId, List<DestCard> cards) {
        // Set chosen cards

        Player player = getPlayerByID(playerId);
        Deck destCardDeck = player.getDestCards();
        for(DestCard card : cards)
            destCardDeck.addCard(card);

        setChanged();
        notifyObservers();
    }

    public void addTrainCard(String playerId, TrainCard card) {
        // Add card to my list of train cards
        Player player = getPlayerByID(playerId);
        player.getTrainCards().addCard(card);

        setChanged();
        notifyObservers();
    }

    public void removeTrainCards(List<TrainCard> cards) {
        // For each card in cards, remove from list of train cards
        Deck deck = user.getTrainCards();
        for(TrainCard card : cards)
        {
            TrainCard drawnCard = (TrainCard) deck.drawCard();
            while(!drawnCard.getColor().equals(card.getColor()))
            {
                   deck.addCard(drawnCard);
                   drawnCard = (TrainCard) deck.drawCard();
            }
        }
        setChanged();
        notifyObservers();
    }

    public void addDestCards(Deck cards) {
        // Add card to my list of dest cards

        user.getDestCards().addCards(cards);

        setChanged();
        notifyObservers();
    }

    public void addChat(String playerId, String message) {
        // Add to chat history
        Chat chat = new Chat(playerId, message);
        chatHistory.add(chat);

        setChanged();
        notifyObservers();
    }

    public void routeClaimed(String playerId, String routeId) {
        // Set this route to be claimed by this player

        Route route = getRouteById(routeId);
        route.setOwnedByPlayerID(playerId);

        setChanged();
        notifyObservers();
    }

    public List<Route> getGameRoutes() {
        return gameRoutes;
    }

    public void addHistory(String historyItem) {
        // Add this item to the ordered history
        gameHistory.add(historyItem);
    }

    public GameHistory getGameHistory()
    {
        return gameHistory;
    }

    public ChatHistory getChatHistory()
    {
        return chatHistory;
    }

    public Player getPlayerByID(String playerID)
    {
        for (Player player : game.getPlayers())
        {
            if(player.getId().equals(playerID))
            {
                return player;
            }
        }
        return null;
    }

    public void gameOver(List<PlayerPointSummary> pointSummaries) {
        // TODO: Store summaries

        setChanged();
        notifyObservers();
    }

    public void lastRoundBegan() {
        // TODO: Store the fact that we are now on the last round.

        setChanged();
        notifyObservers();
    }
}
