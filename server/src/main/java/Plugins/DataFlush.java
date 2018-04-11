package Plugins;

import java.util.Map;
import java.util.TreeMap;

import Services.ServerCommandService;
import common.Command;
import plugin_common.IPersistanceProvider;


import org.apache.commons.lang3.SerializationUtils;

/**
 * Created by Brian on 4/6/18.
 */

public class DataFlush {

    private static DataFlush _instance = new DataFlush();
    private Map<String, Integer> _gameFlushControl = new TreeMap<String, Integer>();
    private int commandsBetweenCheckpoints = 0;

    /**
     * Sets the number of commands that should be saved between game flushes
     *
     * @param numOfCommands the number of commands to save before flushing the game
     */
    public static void setCommandsBetweenCheckpoints(int numOfCommands){
        if (numOfCommands >= 0) {
            _instance.commandsBetweenCheckpoints = numOfCommands;
        }
    }

    /**
     * Save the command via the persistence subsystem
     *
     * @param gameName the name of the game associated with this Command
     * @param command the Command to save
     */
    public static void saveCommand(String gameName, Command command){
        System.out.println("[saving] " + command.toString());
        _instance.saveCommandPrivate(gameName, command);
    }


    /**
     * Save the command via the persistence subsystem. This is the internal implementation
     *
     * @param gameName the name of the game associated with this Command
     * @param command the Command to save
     */
    private void saveCommandPrivate(String gameName, Command command){

        System.out.println("saving " + command.toString());

        // get the persistence provider
        IPersistanceProvider persistenceProvider = PluginLoader.getInstance().getPersistanceProvider();

        // check the number of commands that have been saved for the game
        Integer gameCommands = _gameFlushControl.get(gameName);
        if (gameCommands == null){
            // the game does not exist in the map
            gameCommands = 0;
        }

        if (gameCommands >= commandsBetweenCheckpoints){
            // flush the game, then save the new command
            byte[] gameBytes = ServerCommandService.getGameBytes(gameName);

            if (gameBytes.length > 0){
                persistenceProvider.getGameDao().save(gameName, gameBytes);
                persistenceProvider.getCommandDao().clearCommands(gameName);
            }

            // all of the commands should now be cleared for this game
            gameCommands = 0;
        }

        // save the new command
        byte[] bytes = SerializationUtils.serialize(command);
        persistenceProvider.getCommandDao().save(gameName, bytes);

        // update the number of commands that have been saved
        _gameFlushControl.put(gameName, gameCommands + 1);
    }

}