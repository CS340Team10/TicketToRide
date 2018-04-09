package inmemory_plugin;

import java.util.HashMap;
import java.util.Map;

import data_transfer.PlayerDTO;
import plugin_common.IPlayerDAO;

/**
 * Created by ephraimkunz on 4/4/18.
 */

public class InMemoryPlayerDAO implements IPlayerDAO {
    private Map<String, PlayerDTO> data = new HashMap<>();

    @Override
    public void save(PlayerDTO player) {
        data.put(player.username, player);
    }

    @Override
    public PlayerDTO[] getPlayers() {
        PlayerDTO[] results = new PlayerDTO[data.values().size()];
        data.values().toArray(results);
        return results;
    }

    @Override
    public PlayerDTO getPlayer(String username) {
        return data.get(username);
    }

    @Override
    public void clearPlayers() {
        data.clear();
    }
}
