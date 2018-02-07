package Server;

import java.util.ArrayList;

import Model.Game;
import Model.Player;

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
        Player player1 = new Player("player1", "password");
        Player player2 = new Player("player2", "secret");
        Player player3 = new Player("player3", "my_precious");

        _registeredPlayers.add(player1);
        _registeredPlayers.add(player2);
        _registeredPlayers.add(player3);
    }

    /**
     * Returns the list of all registered players
     *
     * @return an ArrayList of registered Players
     */
    public ArrayList<Player> getRegisteredPlayers(){
        return _registeredPlayers;
    }

    /**
     * Returns a list of all players currently logged in
     *
     * @return an ArrayList of all currently logged in Players
     */
    public ArrayList<Player> getLoggedInPlayers(){
        return _loggedInPlayers;
    }

    /**
     * Returns a list of all current games
     *
     * @return an ArrayList of current Games
     */
    public ArrayList<Game> getGames(){
        return _currentGames;
    }
}
