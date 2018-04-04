package plugin_common;

import data_transfer.PlayerDTO;

/**
 * Created by ephraimkunz on 4/2/18.
 */

public interface IPlayerDAO {

    void save(PlayerDTO player);

    PlayerDTO[] getPlayers();

    PlayerDTO getPlayer(String username);

    void clearPlayers();
}
