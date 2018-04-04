package Plugins;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Arrays;
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

    private PluginConfig findPluginByName(PluginConfig[] configs, String name) {
        for (PluginConfig config: configs) {
            if (config.getName().equals(name)) {
                return config;
            }
        }

        throw new IllegalArgumentException(String.format("%s not found in list of configs: %s", name, Arrays.toString(configs)));
    }

    public void loadPersistancePlugin(String pluginName) {
        assert persistanceProvider == null;
        assert pluginName != null && !pluginName.isEmpty();

        PluginConfig[] configs = new PluginConfig[]{};

        try {
            String content = new Scanner(new File(configFilePath)).useDelimiter("\\Z").next();
            ObjectMapper mapper = new ObjectMapper();
            configs = mapper.readValue(content, PluginConfig[].class);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        PluginConfig pluginConfig = findPluginByName(configs, pluginName);

    }

    public IPersistanceProvider getPersistanceProvider() {
        assert persistanceProvider != null;
        return persistanceProvider;
    }
}
