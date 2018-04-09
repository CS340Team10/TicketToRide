package flatfile_plugin;

import plugin_common.IGameDAO;

/**
 * Created by ephraimkunz on 4/5/18.
 */

public class FlatFileGameDAO implements IGameDAO {
    @Override
    public void save(String gameName, byte[] gameBytes) {

    }

    @Override
    public byte[] getGame(String gameName) {
        return new byte[0];
    }

    @Override
    public byte[][] getGames() {
        return new byte[0][];
    }

    @Override
    public void clearGames() {

    }
}
