package Server;

import java.util.ArrayList;

import common.Game;
import common.Player;

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
