package ClientModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joseph on 2/2/2018.
 */

public class Game
{
    String name;
    int requiredPlayerCount;
    boolean didStart = false;
    List<Player> players = new ArrayList<>();

    public Game()
    {

    }

    /**
     * If the given player is not already participating in the game, then the player is
     *  added to the game
     * @param player the player to add to the game
     */
    public void join(Player player)
    {
        if (!players.contains(player))
        {
            players.add(player);
        }
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
    public boolean startGame()
    {
        if (players.size() >= requiredPlayerCount)
        {
            didStart = true;
        }
        return didStart;
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

    @Override
    public boolean equals(Object o)
    {
        if (o.getClass() == getClass())
        {
            Game g = (Game) o;
            return name.equals(g.name)
                    && requiredPlayerCount==g.requiredPlayerCount
                    && didStart == g.didStart;
        }
        return false;
    }
}
