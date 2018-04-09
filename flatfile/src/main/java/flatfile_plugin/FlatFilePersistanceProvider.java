package flatfile_plugin;

import java.io.File;

import plugin_common.ICommandDAO;
import plugin_common.IGameDAO;
import plugin_common.IPersistanceProvider;
import plugin_common.IPlayerDAO;

/**
 * Created by ephraimkunz on 4/5/18.
 */

public class FlatFilePersistanceProvider implements IPersistanceProvider {
    @Override
    public IPlayerDAO getPlayerDao() {
        return new FlatFilePlayerDAO();
    }

    @Override
    public IGameDAO getGameDao() {
        return new FlatFileGameDAO();
    }

    @Override
    public ICommandDAO getCommandDao() {
        return new FlatFileCommandDAO();
    }

    public FlatFilePersistanceProvider() {
        // Create directory structure
        final String base = System.getProperty("user.dir");
        String[] subfolders = new String[]{"players", "games", "commands"};

        for(String subfolder: subfolders) {
            File directory = new File(String.format("%s/server/config/flatfile/%s", base, subfolder));
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }
    }
}
