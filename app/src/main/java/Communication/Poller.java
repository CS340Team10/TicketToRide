package Communication;

import java.util.List;

import ClientModel.Game;
import common.Command;

/**
 * Created by Joseph on 2/2/2018.
 */

public class Poller {
    private static Poller _instance = new Poller();
    private int pollPeriodMS = 1000;

    public void startCommandPoll() {
    }

    public void stopCommandPoll() {
    }

    public void startGamePoll()
    {}

    public void stopGamePoll()
    {}

    private List<Command> fetchCommands()
    {
        return null;
    }

    private List<Game> fetchGames()
    {
        return null;
    }
}
