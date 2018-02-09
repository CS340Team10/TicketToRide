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
     * @return whether the client's game has started already
     */
    public boolean gameDidStart()
    {
        Game game = ClientModel.get_instance().getGame();
        if (game != null)
        {
            return game.hasStarted();
        }
        return false;
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
        }
    }
}
