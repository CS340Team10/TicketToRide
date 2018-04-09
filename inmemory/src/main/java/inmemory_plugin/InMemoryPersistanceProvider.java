package inmemory_plugin;

import plugin_common.ICommandDAO;
import plugin_common.IGameDAO;
import plugin_common.IPersistanceProvider;
import plugin_common.IPlayerDAO;

/**
 * Created by ephraimkunz on 4/4/18.
 */

public class InMemoryPersistanceProvider implements IPersistanceProvider {
    @Override
    public IPlayerDAO getPlayerDao() {
        return new InMemoryPlayerDAO();
    }

    @Override
    public IGameDAO getGameDao() {
        return new InMemoryGameDAO();
    }

    @Override
    public ICommandDAO getCommandDao() {
        return new InMemoryCommandDAO();
    }
}
