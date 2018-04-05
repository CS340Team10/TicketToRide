package sqlite_plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data_transfer.PlayerDTO;
import plugin_common.IPlayerDAO;

/**
 * Created by ephraimkunz on 4/5/18.
 */

public class SqlitePlayerDAO implements IPlayerDAO {

    public SqlitePlayerDAO() {
        try {

            Connection connection = ConnectionManager.newConnection();
            String createStatement = "CREATE TABLE IF NOT EXISTS Players (" +
                    "username TEXT NOT NULL," +
                    "password TEXT NOT NULL," +
                    "loggedIn TEXT NOT NULL," +
                    "PRIMARY KEY (username));";
            Statement statement = connection.createStatement();
            statement.execute(createStatement);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(PlayerDTO player) {
        String insertStatement = "INSERT into Players(username, password, loggedIn) VALUES (?, ?, ?)";
        try {
            Connection connection = ConnectionManager.newConnection();
            PreparedStatement ps = connection.prepareStatement(insertStatement);
            ps.setString(1, player.username);
            ps.setString(2, player.password);
            ps.setString(3, Boolean.toString(player.isLoggedIn));
            ps.executeUpdate();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PlayerDTO[] getPlayers() {
        String selectStatement = "SELECT * FROM Players";
        try {
            Connection connection = ConnectionManager.newConnection();
            PreparedStatement ps = connection.prepareStatement(selectStatement);
            ResultSet rs = ps.executeQuery();

            List<PlayerDTO> dtoList = new ArrayList<>();
            while (rs.next()) {
                PlayerDTO dto = new PlayerDTO();
                dto.username = rs.getString("username");
                dto.password = rs.getString("password");
                dto.isLoggedIn = Boolean.parseBoolean(rs.getString("loggedIn"));
                dtoList.add(dto);
            }

            rs.close();
            connection.close();

            PlayerDTO[] dtoArray = new PlayerDTO[dtoList.size()];
            dtoList.toArray(dtoArray);
            return dtoArray;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new PlayerDTO[0];
    }

    @Override
    public PlayerDTO getPlayer(String username) {
        String selectStatement = "SELECT * FROM Players WHERE username = ?";
        try {
            Connection connection = ConnectionManager.newConnection();
            PreparedStatement ps = connection.prepareStatement(selectStatement);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();

            PlayerDTO dto = new PlayerDTO();
            dto.username = rs.getString("username");
            dto.password = rs.getString("password");
            dto.isLoggedIn = Boolean.parseBoolean(rs.getString("loggedIn"));

            rs.close();
            connection.close();
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new PlayerDTO();
    }

    @Override
    public void clearPlayers() {
        String deleteStatement = "DELETE FROM Players";
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
