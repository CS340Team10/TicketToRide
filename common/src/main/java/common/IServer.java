package common;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public interface IServer {

    public Results register(String username, String password);

    public Results login(String username, String password);

    public Results createGame(String gameName, int numPlayers);

    public Results joinGame(String gameName, String playerID);

}
