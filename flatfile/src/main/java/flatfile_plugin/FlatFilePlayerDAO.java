package flatfile_plugin;

import data_transfer.PlayerDTO;
import plugin_common.IPlayerDAO;

/**
 * Created by ephraimkunz on 4/5/18.
 */

public class FlatFilePlayerDAO implements IPlayerDAO {
    @Override
    public void save(PlayerDTO player) {

    }

    @Override
    public PlayerDTO[] getPlayers() {
        return new PlayerDTO[0];
    }

    @Override
    public PlayerDTO getPlayer(String username) {
        return null;
    }

    @Override
    public void clearPlayers() {

    }
}
