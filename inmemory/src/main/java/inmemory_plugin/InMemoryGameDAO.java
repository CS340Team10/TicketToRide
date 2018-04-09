package inmemory_plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import plugin_common.IGameDAO;

/**
 * Created by ephraimkunz on 4/4/18.
 */

public class InMemoryGameDAO implements IGameDAO {
    private Map<String, List<Byte>> data = new HashMap<>();

    @Override
    public void save(String gameName, byte[] gameBytes) {
        data.put(gameName, DataConverter.toByteList(gameBytes));
    }

    @Override
    public byte[] getGame(String gameName) {
        List<Byte> game = data.get(gameName);
        return DataConverter.toByteArray(game);
    }

    @Override
    public byte[][] getGames() {
        byte[][] returnValues = new byte[data.values().size()][];
        data.values().toArray(returnValues);
        return returnValues;
    }

    @Override
    public void clearGames() {
        data.clear();
    }
}
