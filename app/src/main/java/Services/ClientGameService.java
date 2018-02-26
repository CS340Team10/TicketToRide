package Services;

import java.util.List;

import ClientModel.ClientModel;

/**
 * Created by Joseph on 2/7/2018.
 */

public class ClientGameService
{
    private static ClientGameService _instance = new ClientGameService();

    private ClientGameService(){}

    public static ClientGameService getInstance() {
        return _instance;
    }

    /**
     *
     * set that the game did start
     */
    public void gameDidStart()
    {
        ClientModel.getInstance().startGame();
    }

    /**
     * update the list of games held by the model
     * @param games
     */
    public void updateGameList(List<String> games)
    {
        ClientModel.getInstance().setAvailableGames(games);
    }
}
