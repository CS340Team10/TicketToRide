package plugin_common;

/**
 * Created by ephraimkunz on 4/2/18.
 */

public interface ICommandDAO {

    void save(String gameName, byte[] commandBytes);

    byte[][] getCommands(String gameName);

    void clearCommands(String gameName);

    void clearCommands();
}
