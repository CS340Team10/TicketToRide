package Plugins;

import org.apache.commons.lang3.SerializationUtils;

import Model.Game;
import Services.ServerCommandService;
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

        // Restore players if any are in database. Do this first, because we will use these players
        // later as we try to restore the game.
        PlayerDTO[] players = playerDAO.getPlayers();
        for (PlayerDTO pdto : players) {
            ServerCommandService.getInstance().register(pdto.username, pdto.password);
        }

        // Restore games if any are in database.
        byte[][] games = gameDAO.getGames();
        for (byte[] gameBytes : games) {
            Game game = SerializationUtils.deserialize(gameBytes);
            ServerCommandService.getInstance().getServerModel().addGame(game);
        }

        // Replay each saved command. These should modify any Games accordingly.
        for (byte[] gameBytes : games) {
            Game game = SerializationUtils.deserialize(gameBytes);
            String name = game.getName();

            byte[][] commands = commandDAO.getCommands(name);
            for (byte[] commandBytes : commands) {
                Command command = SerializationUtils.deserialize(commandBytes);
                command.execute();
            }
        }

        // Cleanup old games and commands for it, then store new games.
        gameDAO.clearGames();
        commandDAO.clearCommands();
        for(Game g: ServerCommandService.getInstance().getServerModel().getGames()) {
            gameDAO.save(g.getName(), SerializationUtils.serialize(g));
        }
    }
}
