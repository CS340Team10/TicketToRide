package sqlite_plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import plugin_common.IGameDAO;

/**
 * Created by ephraimkunz on 4/5/18.
 */

public class SqliteGameDAO implements IGameDAO {

    public SqliteGameDAO() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = ConnectionManager.newConnection();
            connection.setAutoCommit(true);
            String createStatement = "CREATE TABLE IF NOT EXISTS Games (" +
                "gameName TEXT NOT NULL," +
                "gameBlob BLOB NOT NULL," +
                "PRIMARY KEY (gameName));";
            Statement statement = connection.createStatement();
            statement.execute(createStatement);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(String gameName, byte[] gameBytes) {
        String insertStatement = "INSERT into Games(gameName, gameBlob) VALUES (?, ?)";
        try {
            Connection connection = ConnectionManager.newConnection();
            PreparedStatement ps = connection.prepareStatement(insertStatement);
            ps.setString(1, gameName);
            ps.setBytes(2, gameBytes);
            ps.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getGame(String gameName) {
        String selectStatement = "SELECT gameBlob FROM Games WHERE gameName = ?";
        try {
            Connection connection = ConnectionManager.newConnection();
            PreparedStatement ps = connection.prepareStatement(selectStatement);
            ps.setString(1, gameName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            byte[] results = rs.getBytes("gameBlob");
            rs.close();
            connection.close();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    @Override
    public void clearGames() {
        String deleteStatement = "DELETE FROM Games";
        try {
            Connection connection = ConnectionManager.newConnection();
            PreparedStatement ps = connection.prepareStatement(deleteStatement);
            ps.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
