package sqlite_plugin;

import plugin_common.ICommandDAO;
import plugin_common.IGameDAO;
import plugin_common.IPersistanceProvider;
import plugin_common.IPlayerDAO;

/**
 * Created by ephraimkunz on 4/5/18.
 */

public class SqlitePersistanceProvider implements IPersistanceProvider {
    @Override
    public IPlayerDAO getPlayerDao() {
        return new SqlitePlayerDAO();
    }

    @Override
    public IGameDAO getGameDao() {
        return new SqliteGameDAO();
    }

    @Override
    public ICommandDAO getCommandDao() {
        return new SqliteCommandDAO();
    }
}
