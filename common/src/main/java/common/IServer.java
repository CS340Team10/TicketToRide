package common;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public interface IServer {

    /**
     * Attempts to register the user in the system
     *
     * @param username the username
     * @param password the password
     *
     * @return a Result from the server
     */
    public Results register(String username, String password);

    /**
     * Attempts to log the user in
     *
     * @param username
     * @param password
     * @return
     */
    public Results login(String username, String password);

    /**
     * Creates a new game on the Server
     *
     * @param gameName
     * @param numPlayers
     * @return
     */
    public Results createGame(String gameName, int numPlayers);

    /**
     * Joins a user to a game on the Server
     *
     * @param gameName
     * @param playerID
     * @return
     */
    public Results joinGame(String gameName, String playerID);
}
