package Server;

import org.apache.commons.lang3.SerializationUtils;

import Model.Game;
import Plugins.DataFlush;
import Plugins.PluginLoader;
import Plugins.ServerRestore;
import common.Command;
import data_transfer.PlayerDTO;
import plugin_common.ICommandDAO;
import plugin_common.IGameDAO;
import plugin_common.IPersistanceProvider;
import plugin_common.IPlayerDAO;

public class Main {

    /**
     * Attempts to turn the String into an integer. Returns a 0 if this is not possible.
     *
     * @param intString the String to convert to an integer.
     *
     * @return the parameter converted to an integer, or a zero if this is not possible.
     */
    public static int parseInt(String intString){
        int returnValue = 0;

        try {
            returnValue = Integer.parseInt(intString);
        }
        catch (Exception exc){
            returnValue = 0;
        }

        return returnValue;
    }

    /**
     * Starts the server
     *
     * @param args the arguments for the server
     */
    public static void main(String[] args){
        if (args.length < 2) {
            return;
        }

        String pluginName = args[0];
        String commandsBetweenCheckpoints = args[1];
        boolean clearDbData = args.length == 3 && args[2].equals("-c");

        PluginLoader.getInstance().loadPersistancePlugin(pluginName);
        ServerRestore.restoreIfNecessary(clearDbData);
//        testPlugin();

        DataFlush.setCommandsBetweenCheckpoints(Integer.parseInt(commandsBetweenCheckpoints));


        new ServerCommunicator().run();
    }

    private static void testPlugin() {
        IPersistanceProvider provider = PluginLoader.getInstance().getPersistanceProvider();
        ICommandDAO cDAO = provider.getCommandDao();
        IGameDAO gDAO = provider.getGameDao();
        IPlayerDAO pDAO = provider.getPlayerDao();


        Game g = new Game("gameName", 5);

        byte[] bytes = SerializationUtils.serialize(g);

        gDAO.save(g.getName(), bytes);
        Game back = SerializationUtils.deserialize(gDAO.getGame(g.getName()));
        boolean same = g.equals(back);
        assert same;

        byte[][] games = gDAO.getGames();

        assert games.length == 1;
        for (byte[] b : games) {
            Game backC = SerializationUtils.deserialize(b);
            assert backC.equals(g);
        }


        Command[] commandsIn = new Command[2];

        commandsIn[0] = new Command("testClass", "testMethod", new String[]{"testType"}, new Object[]{"testValue"});
        commandsIn[1] = new Command("test1", "test2", new String[]{"test3"}, new Object[]{"test4"});

        bytes = SerializationUtils.serialize(commandsIn[0]);
        cDAO.save(g.getName(), bytes);
        bytes = SerializationUtils.serialize(commandsIn[1]);
        cDAO.save(g.getName(), bytes);
        byte[][] commands = cDAO.getCommands(g.getName());

        assert commands.length == 1;
        int i = 0;
        for (byte[] b : commands) {
            Command backC = SerializationUtils.deserialize(b);
            assert backC.equals(commandsIn[i]);
            i ++;
        }

        PlayerDTO pdto = new PlayerDTO();
        pdto.isLoggedIn = false;
        pdto.username = "uname";
        pdto.password = "pass";

        pDAO.save(pdto);
        PlayerDTO pdtoBack = pDAO.getPlayer(pdto.username);
        assert pdto.equals(pdtoBack);
    }
}
