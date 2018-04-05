package sqlite_plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import plugin_common.ICommandDAO;

/**
 * Created by ephraimkunz on 4/5/18.
 */

public class SqliteCommandDAO implements ICommandDAO {
    private Connection connection;

    public SqliteCommandDAO() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = ConnectionManager.newConnection();
            String createStatement = "CREATE TABLE IF NOT EXISTS Commands (" +
                    "gameName TEXT NOT NULL," +
                    "seqNumber INTEGER NOT NULL," +
                    "commandBlob BLOB NOT NULL," +
                    "PRIMARY KEY (gameName, seqNumber));";
            Statement statement = connection.createStatement();
            statement.execute(createStatement);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(String gameName, byte[] commandBytes, int seqNumber) {
        String insertStatement = "INSERT into Commands(gameName, seqNumber, commandBlob) VALUES (?, ?, ?)";
        try {
            Connection connection = ConnectionManager.newConnection();
            PreparedStatement ps = connection.prepareStatement(insertStatement);
            ps.setString(1, gameName);
            ps.setInt(2, seqNumber);
            ps.setBytes(3, commandBytes);
            ps.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[][] getCommands(String gameName) {
        String selectStatement = "SELECT commandBlob FROM Commands WHERE gameName = ? ORDER BY seqNumber ASC";
        try {
            Connection connection = ConnectionManager.newConnection();
            PreparedStatement ps = connection.prepareStatement(selectStatement);
            ps.setString(1, gameName);
            ResultSet rs = ps.executeQuery();

            List<byte[]> results = new ArrayList<>();
            while(rs.next()) {
                byte[] blob = rs.getBytes("commandBlob");
                results.add(blob);
            }

            rs.close();
            connection.close();

            byte[][] resultArray = new byte[results.size()][];
            results.toArray(resultArray);
            return resultArray;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new byte[0][];
    }

    @Override
    public void clearCommands(String gameName) {
        String deleteStatement = "DELETE FROM Commands WHERE gameName = ?";
        try {
            Connection connection = ConnectionManager.newConnection();
            PreparedStatement ps = connection.prepareStatement(deleteStatement);
            ps.setString(1, gameName);
            ps.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearCommands() {
        String deleteStatement = "DELETE FROM Commands";
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
