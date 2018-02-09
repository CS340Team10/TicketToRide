package Services;

import java.util.List;

import ClientModel.ClientModel;
import ClientModel.Game;

/**
 * Created by Joseph on 2/7/2018.
 */

public class ClientGameService
{
    public static ClientGameService _instance = new ClientGameService();

    public static ClientGameService get_instance() {
        return _instance;
    }

    /**
     *
     * set that the game did start
     */
    public void gameDidStart()
    {
        Game game = ClientModel.get_instance().getGame();
        game.startGame();
        ClientModel.get_instance().notifyObservers();
    }

    /**
     * update the list of games held by the model
     * @param games
     */
    public void updateGameList(List<String> games)
    {
        if (games != null)
        {
            ClientModel.get_instance().setAvailableGames(games);
            ClientModel.get_instance().notifyObservers();
        }
    }
}
