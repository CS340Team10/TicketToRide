package flatfile_plugin;

import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import data_transfer.PlayerDTO;
import plugin_common.IPlayerDAO;

/**
 * Created by ephraimkunz on 4/5/18.
 */

public class FlatFilePlayerDAO implements IPlayerDAO {
    private String getPlayersFilepath() {
        final String base = System.getProperty("user.dir");
        return String.format("%s/server/config/flatfile/players/", base);
    }

    @Override
    public void save(PlayerDTO player) {
        String gamePath = getPlayersFilepath() + player.username;
        try {
            FileOutputStream fos = new FileOutputStream(gamePath);
            fos.write(SerializationUtils.serialize(player));
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PlayerDTO[] getPlayers() {
        File dir = new File(getPlayersFilepath());

        PlayerDTO[] results = new PlayerDTO[dir.listFiles().length];
        int i = 0;
        for(File f : dir.listFiles()) {
            results[i] = getPlayer(f.getName());
            i++;
        }

        return results;
    }

    @Override
    public PlayerDTO getPlayer(String username) {
        String gamePath = getPlayersFilepath() + username;
        if (!new File(gamePath).exists()) {
            return null;
        }

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
            byte[] byteArray = buffer.toByteArray();
            return SerializationUtils.deserialize(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new PlayerDTO();
    }

    @Override
    public void clearPlayers() {
        File dir = new File(getPlayersFilepath());
        for(File f: dir.listFiles()) {
            f.delete();
        }
    }
}
