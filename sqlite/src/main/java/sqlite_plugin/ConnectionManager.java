package sqlite_plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by ephraimkunz on 4/5/18.
 */

public class ConnectionManager {
    public static Connection newConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:server/config/ttr.db");
    }
}
