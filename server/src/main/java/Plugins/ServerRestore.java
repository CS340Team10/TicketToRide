package Plugins;

import org.apache.commons.lang3.SerializationUtils;

import Model.Game;
import common.Command;
import data_transfer.PlayerDTO;
import plugin_common.ICommandDAO;
import plugin_common.IGameDAO;
import plugin_common.IPersistanceProvider;
import plugin_common.IPlayerDAO;

/**
 * Created by ephraimkunz on 4/6/18.
 */

public class ServerRestore {
    public static void restoreIfNecessary(boolean clearFlag) {
        IPersistanceProvider provider = PluginLoader.getInstance().getPersistanceProvider();
        IGameDAO gameDAO = provider.getGameDao();
        IPlayerDAO playerDAO = provider.getPlayerDao();
        ICommandDAO commandDAO = provider.getCommandDao();

        if (clearFlag) {
            // Wipe out the contents of each table
            gameDAO.clearGames();
            playerDAO.clearPlayers();
            commandDAO.clearCommands();
        }

        // Restore players if any are in database.
        PlayerDTO[] players = playerDAO.getPlayers();
        for (PlayerDTO pdto : players) {
            // TODO: Create player from PlayerDTO
            // TODO: Save player in model
        }

        // Restore games if any are in database.
        byte[][] games = gameDAO.getGames();
        for (byte[] gameBytes : games) {
            Game game = SerializationUtils.deserialize(gameBytes);

            byte[][] commands = commandDAO.getCommands(game.getName());
            for(byte[] commandBytes : commands) {
                Command command = SerializationUtils.deserialize(commandBytes);

                // TODO: Replay commands onto game
            }

            // TODO: Store game in client model
            // TODO: Delete old game and commands, store newly restored game
        }
    }
}
