package flatfile_plugin;

import plugin_common.ICommandDAO;

/**
 * Created by ephraimkunz on 4/5/18.
 */

public class FlatFileCommandDAO implements ICommandDAO {
    @Override
    public void save(String gameName, byte[] commandBytes, int seqNumber) {

    }

    @Override
    public byte[][] getCommands(String gameName) {
        return new byte[0][];
    }

    @Override
    public void clearCommands(String gameName) {

    }

    @Override
    public void clearCommands() {

    }
}
