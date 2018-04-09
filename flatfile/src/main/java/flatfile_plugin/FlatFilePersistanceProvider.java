package flatfile_plugin;

import plugin_common.ICommandDAO;
import plugin_common.IGameDAO;
import plugin_common.IPersistanceProvider;
import plugin_common.IPlayerDAO;

/**
 * Created by ephraimkunz on 4/5/18.
 */

public class FlatFilePersistanceProvider implements IPersistanceProvider {
    @Override
    public IPlayerDAO getPlayerDao() {
        return new FlatFilePlayerDAO();
    }

    @Override
    public IGameDAO getGameDao() {
        return new FlatFileGameDAO();
    }

    @Override
    public ICommandDAO getCommandDao() {
        return new FlatFileCommandDAO();
    }
}
