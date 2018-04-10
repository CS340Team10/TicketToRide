package flatfile_plugin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import plugin_common.ICommandDAO;

/**
 * Created by ephraimkunz on 4/5/18.
 */

public class FlatFileCommandDAO implements ICommandDAO {
    private String getCommandFilepath() {
        final String base = System.getProperty("user.dir");
        return String.format("%s/server/config/flatfile/commands/", base);
    }

    @Override
    public void save(String gameName, byte[] commandBytes) {
        String commandPath = getCommandFilepath() + gameName + "_" + System.currentTimeMillis();
        try {
            FileOutputStream fos = new FileOutputStream(commandPath);
            fos.write(commandBytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[][] getCommands(final String gameName) {
        File dir = new File(getCommandFilepath());
        FilenameFilter ff = new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.startsWith(gameName);
            }
        };

        List<File> files = Arrays.asList(dir.listFiles(ff));
        files.sort(new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                long time1 = Long.parseLong(f1.getName().split("_")[1]);
                long time2 = Long.parseLong(f2.getName().split("_")[1]);

                return time1 < time2 ? -1 : time1 == time2 ? 0 : 1; // Sort by smallest first
            }
        });

        byte[][] results = new byte[files.size()][];
        for (int i = 0; i < files.size(); ++i) {
            File f = files.get(i);
            results[i] = getCommand(f.getAbsolutePath());
        }

        return results;
    }

    private byte[] getCommand(String filepath) {
        try {
            FileInputStream fis = new FileInputStream(filepath);
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
    public void clearCommands(final String gameName) {
        File dir = new File(getCommandFilepath());
        FilenameFilter ff = new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.startsWith(gameName);
            }
        };

        for(File f : dir.listFiles(ff)) {
            f.delete();
        }
    }

    @Override
    public void clearCommands() {
        File dir = new File(getCommandFilepath());

        for(File f : dir.listFiles()) {
            f.delete();
        }
    }
}
