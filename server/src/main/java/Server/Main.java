package Server;

import org.apache.commons.lang3.SerializationUtils;

import Model.Game;
import Plugins.PluginLoader;
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
        boolean clear = args.length == 3;

        PluginLoader.getInstance().loadPersistancePlugin(pluginName);
        testPlugin();

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


        Command command = new Command("testClass", "testMethod", new String[]{"testType"}, new Object[]{"testValue"});
        bytes = SerializationUtils.serialize(command);
        cDAO.save(g.getName(), bytes);
        byte[][] commands = cDAO.getCommands(g.getName());

        assert commands.length == 1;
        for (byte[] b : commands) {
            Command backC = SerializationUtils.deserialize(b);
            assert backC.equals(command);
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
