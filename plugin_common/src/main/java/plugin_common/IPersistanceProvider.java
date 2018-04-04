package plugin_common;

/**
 * Created by ephraimkunz on 4/2/18.
 */

public interface IPersistanceProvider {

    IPlayerDAO getPlayerDao();
    IGameDAO getGameDao();
    ICommandDAO getCommandDao();
}
