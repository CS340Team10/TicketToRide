package Plugins;

import plugin_common.IPersistanceProvider;

/**
 * Created by ephraimkunz on 4/2/18.
 */

public class PluginLoader {
    private final String configFilePath = "../config/config.json";
    private IPersistanceProvider persistanceProvider = null;
    private static PluginLoader instance = new PluginLoader();

    private PluginLoader(){}

    public static PluginLoader getInstance() {
        return instance;
    }

    public void loadPersistancePlugin(String pluginName) {
        assert persistanceProvider == null;
        assert pluginName != null && !pluginName.isEmpty();

        // TODO: Load from file
    }

    public IPersistanceProvider getPersistanceProvider() {
        return persistanceProvider;
    }
}
