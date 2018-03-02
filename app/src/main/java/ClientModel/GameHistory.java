package ClientModel;

import java.util.ArrayList;

/**
 * Created by matto on 2/25/2018.
 */

public class GameHistory {
    ArrayList<String> history = new ArrayList<>();

    public void add(String action)
    {
        history.add(action);
    }

    public ArrayList<String> getHistory()
    {
        return history;
    }
}
