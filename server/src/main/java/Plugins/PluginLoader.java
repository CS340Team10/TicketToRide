package Plugins;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Scanner;

import plugin_common.IPersistanceProvider;

/**
 * Created by ephraimkunz on 4/2/18.
 */

public class PluginLoader {
    private final String configFilePath = "server/config/config.json";
    private IPersistanceProvider persistanceProvider = null;
    private static PluginLoader instance = new PluginLoader();

    private PluginLoader(){
    }

    public static PluginLoader getInstance() {
        return instance;
    }

    private PluginConfig findPluginByName(PluginConfig[] plugins, String name) {
        for (PluginConfig plugin: plugins) {
            if (plugin.getName().equals(name)) {
                return plugin;
            }
        }

        return null;
    }

    public void loadPersistancePlugin(String pluginName) {
        assert persistanceProvider == null;
        assert pluginName != null && !pluginName.isEmpty();

        PluginConfig[] plugins = new PluginConfig[]{};

        try {
            String content = new Scanner(new File(configFilePath)).useDelimiter("\\Z").next();
            ObjectMapper mapper = new ObjectMapper();
            plugins = mapper.readValue(content, PluginConfig[].class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        PluginConfig plugin = findPluginByName(plugins, pluginName);

    }

    public IPersistanceProvider getPersistanceProvider() {
        assert persistanceProvider != null;
        return null;
    }
}
