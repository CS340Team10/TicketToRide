package common.Model;

import java.util.List;

/**
 * Created by Joseph on 2/1/2018.
 */

public class Game
{
    List<Player> players;
    String name;
    int requiredPlayerCount;
    CommandHistory gameHistory;
    boolean didStart;

    public Game()
    {

    }
}
