package Plugins;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ephraimkunz on 4/2/18.
 */

public class PluginConfig {
    private String name;
    private String jarPath;
    private String className;

    @JsonCreator
    public PluginConfig(@JsonProperty("name") String name, @JsonProperty("jarPath") String jarPath, @JsonProperty("className") String className) {
        this.name = name;
        this.jarPath = jarPath;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public String getJarPath() {
        return jarPath;
    }

    public String getClassName() {
        return className;
    }
}
