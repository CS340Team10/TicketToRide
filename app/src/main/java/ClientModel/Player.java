package ClientModel;

/**
 * Created by Joseph on 2/2/2018.
 */

public class Player {
    String id;

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == getClass())
        {
            Player p = (Player) o;
            return (id.equals(p.id));
        }
        return false;
    }
}
