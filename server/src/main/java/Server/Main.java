package Server;

import Plugins.PluginLoader;

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

        new ServerCommunicator().run();
    }
}
