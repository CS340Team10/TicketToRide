package ClientModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import common.DestCard;
import common.PlayerAttributes;
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
        // TODO
        // Implement this
        return new Route(null, null, null, 0, null);
    }

    public void updatePlayer(PlayerAttributes player) {
        // TODO
        // From list of players, find player with player.playerId
        // Update all relevant fields

        setChanged();
        notifyObservers();
    }

    public void playerTurnBegan(String playerId) {
        // TODO
        // Find player with this id
        // Set isMyTurn to true.
        // Set to false on all other players.

        setChanged();
        notifyObservers();
    }

    public void updateTrainCardDeck(List<TrainCard> visible, Integer invisible) {
        // TODO
        // Set visible list to visible
        // Set invisible count to invisible

        setChanged();
        notifyObservers();
    }

    public void updateDestCardDeck(Integer invisible) {
        // TODO
        // Set invisible count to invisible

        setChanged();
        notifyObservers();
    }

    public void setOfferedDestCards(List<DestCard> cards) {
        // TODO
        // Set offered cards

        setChanged();
        notifyObservers();
    }

    public void setChosenDestCards(List<DestCard> cards) {
        // TODO
        // Set chosen cards

        setChanged();
        notifyObservers();
    }

    public void addTrainCard(TrainCard card) {
        // TODO
        // Add card to my list of train cards

        setChanged();
        notifyObservers();
    }

    public void removeTrainCards(List<TrainCard> cards) {
        // TODO
        // For each card in cards, remove from list of train cards

        setChanged();
        notifyObservers();
    }

    public void addChat(String playerId, String message) {
        // TODO
        // Add to chat history

        setChanged();
        notifyObservers();
    }

    public void routeClaimed(String playerId, String routeId) {
        // TODO
        // Set this route to be claimed by this player

        setChanged();
        notifyObservers();
    }

    public void addHistory(String historyItem) {
        // TODO
        // Add this item to the ordered history
    }
}
