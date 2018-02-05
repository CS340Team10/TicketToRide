package ClientModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import common.Model.Player;

/**
 * Created by ephraimkunz on 2/3/18.
 * Modified by Joseph on 2/3/18
 */

public class ClientModel extends Observable
{
    private ClientModel() {}
    private static ClientModel _instance = new ClientModel();
    private List<Player> players = new ArrayList<>();
    private Game game = new Game();
    private List<String> available_games = new ArrayList<>();

    public static ClientModel get_instance()
    {
        return _instance;
    }

    public Game getGame() {
        return game;
    }

    public List<String> getAvailableGames() {
        return available_games;
    }
}
