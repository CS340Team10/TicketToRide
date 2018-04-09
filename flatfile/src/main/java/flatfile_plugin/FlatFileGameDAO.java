package flatfile_plugin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import plugin_common.IGameDAO;

/**
 * Created by ephraimkunz on 4/5/18.
 */

public class FlatFileGameDAO implements IGameDAO {
    private String getGameFilepath() {
        final String base = System.getProperty("user.dir");
        return String.format("%s/server/config/flatfile/games/", base);
    }

    @Override
    public void save(String gameName, byte[] gameBytes) {
        String gamePath = getGameFilepath() + gameName;
        try {
            FileOutputStream fos = new FileOutputStream(gamePath);
            fos.write(gameBytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] getGame(String gameName) {
        String gamePath = getGameFilepath() + gameName;
        try {
            FileInputStream fis = new FileInputStream(gamePath);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = fis.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();

            fis.close();
            return buffer.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    @Override
    public byte[][] getGames() {
        File dir = new File(getGameFilepath());
        byte[][] result = new byte[dir.listFiles().length][];

        int i = 0;
        for(File f : dir.listFiles()) {
            result[i] = getGame(f.getName());
            i++;
        }

        return result;
    }

    @Override
    public void clearGames() {
        File dir = new File(getGameFilepath());
        for(File f: dir.listFiles()) {
            f.delete();
        }
    }
}
