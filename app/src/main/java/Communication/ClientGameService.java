package Communication;

import java.util.List;

import ClientModel.Game;

/**
 * Created by matto on 2/4/2018.
 */

public class ClientGameService {
    private static ClientGameService _instance;

    public static ClientGameService get_instance()
    {
        return _instance;
    }

    public void gameDidStart()
    {

    }

    public void updateGameList(List<Game> games)
    {

    }
}
