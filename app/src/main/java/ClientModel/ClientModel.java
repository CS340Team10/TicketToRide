package ClientModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by ephraimkunz on 2/3/18.
 */

public class ClientModel extends Observable {
    private Game game;

    public Game getGame() {
        return game;
    }

    public List<String> getGameList() {
        return new ArrayList<>();
    }
}
