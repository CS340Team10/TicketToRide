package plugin_common;

/**
 * Created by ephraimkunz on 4/2/18.
 */

public interface IGameDAO {

    void save(String gameName, byte[] gameBytes);

    byte[] getGame(String gameName);

    byte[][] getGames();

    void clearGames();
}
