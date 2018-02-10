package Services;

import java.util.List;

import ClientModel.ClientModel;

/**
 * Created by Joseph on 2/7/2018.
 */

public class ClientGameService
{
    public static ClientGameService _instance = new ClientGameService();

    public static ClientGameService getInstance() {
        return _instance;
    }

    /**
     *
     * set that the game did start
     */
    public void gameDidStart()
    {
        ClientModel.get_instance().startGame();
    }

    /**
     * update the list of games held by the model
     * @param games
     */
    public void updateGameList(List<String> games)
    {
        ClientModel.get_instance().setAvailableGames(games);
    }
}
