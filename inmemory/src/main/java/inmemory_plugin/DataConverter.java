package inmemory_plugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ephraimkunz on 4/4/18.
 */

public class DataConverter {
    public static List<Byte> toByteList(byte[] in) {
        Byte[] byteObjects = new Byte[in.length];

        int i=0;
        for(byte b: in)
            byteObjects[i++] = b;  // Autoboxing

        return Arrays.asList(byteObjects);
    }

    public static byte[] toByteArray(List<Byte> in) {
        byte[] bytes = new byte[in.size()];

        int i=0;
        for(Byte b: in)
            bytes[i++] = b.byteValue();

        return bytes;
    }
}
