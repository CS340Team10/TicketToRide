package flatfile_plugin;

import java.io.FileOutputStream;

import plugin_common.IGameDAO;

/**
 * Created by ephraimkunz on 4/5/18.
 */

public class FlatFileGameDAO implements IGameDAO {
    private String getGameFilepath() {
        final String base = System.getProperty("user.dir");
        return String.format("%s/server/config/flatfile/games/", base);
    }

    @Override
    public void save(String gameName, byte[] gameBytes) {
        String gamePath = getGameFilepath() + gameName;
        try {
            FileOutputStream fos = new FileOutputStream(gamePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
