package Communication;

import java.util.ArrayList;
import java.util.List;

import ClientModel.ClientModel;
import ClientModel.Game;

/**
 * Created by matto on 2/4/2018.
 */

public class ClientGameService {
    private static ClientGameService _instance = new ClientGameService();

    public static ClientGameService get_instance()
    {
        return _instance;
    }

    public void gameDidStart()
    {

    }

    public void updateGameList(List<String> games)
    {
        ClientModel.get_instance().setAvailableGames(games);
        notify();
    }
}
