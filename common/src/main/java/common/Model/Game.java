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
    CommandHistory gameHistory = new CommandHistory();
    boolean didStart = false;

    public Game()
    {

    }

    public void join(Player player)
    {
        if (!players.contains(player))
        {
            players.add(player);
        }
    }

    public boolean hasStarted()
    {
        return didStart;
    }

    public void startGame()
    {
        didStart = true;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setRequiredPlayerCount(int requiredPlayerCount) {
        this.requiredPlayerCount = requiredPlayerCount;
    }

    public int getRequiredPlayerCount() {
        return requiredPlayerCount;
    }

    public CommandHistory getGameHistory() {
        return gameHistory;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o.getClass() == getClass())
        {
            Game g = (Game) o;
            return name.equals(g.name)
                    && requiredPlayerCount==g.requiredPlayerCount
                    && gameHistory.equals(g.gameHistory) && didStart == g.didStart;
        }
        return false;
    }
}
