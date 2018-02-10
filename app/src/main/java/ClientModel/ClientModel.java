package ClientModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

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

    public static ClientModel get_instance()
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
        if (available_games != null)
        {
            this.available_games = available_games;
            setChanged();
            notifyObservers();
        }
    }

    public Player getUser() {
        return user;
    }
}
