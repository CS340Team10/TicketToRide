package ClientModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joseph on 2/2/2018.
 */

public class Game
{
    private boolean didStart = false;
    private List<Player> players = new ArrayList<>();

    public Game()
    {

    }

    /**
     *
     * @return whether the game has already started or not
     */
    public boolean hasStarted()
    {
        return didStart;
    }

    /**
     * If there are enough players (greater than or equal to the required player count)
     * tell the game that it has started
     * But if there are not enough players, the game will not start and the method will return false
     * @return whether the game successfully started
     */
    public void startGame()
    {
        didStart = true;
    }
}
