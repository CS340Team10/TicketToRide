package inmemory_plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import plugin_common.ICommandDAO;

/**
 * Created by ephraimkunz on 4/4/18.
 */

public class InMemoryCommandDAO implements ICommandDAO {
    private Map<String, List<List<Byte>>> data = new HashMap<>();



    @Override
    public void save(String gameName, byte[] commandBytes, int seqNumber) {
        if (!data.containsKey(gameName)) {
            data.put(gameName, new ArrayList<List<Byte>>());
        }

        List<List<Byte>> commands = data.get(gameName);
        commands.add(DataConverter.toByteList(commandBytes));
    }

    @Override
    public byte[][] getCommands(String gameName) {
        List<List<Byte>> commands = data.get(gameName);

        byte[][] bytes = new byte[commands.size()][];

        int i = 0;
        for (List<Byte> b : commands) {
            bytes[i++] = DataConverter.toByteArray(b);
        }

        return bytes;
    }

    @Override
    public void clearCommands(String gameName) {
        data.remove(gameName);
    }

    @Override
    public void clearCommands() {
        data.clear();
    }
}
