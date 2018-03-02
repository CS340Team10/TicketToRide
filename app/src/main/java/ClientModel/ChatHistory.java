package ClientModel;

import java.util.ArrayList;

/**
 * Created by matto on 2/25/2018.
 */

public class ChatHistory {
    ArrayList<Chat> history = new ArrayList<>();

    public void add(Chat chat)
    {
        history.add(chat);
    }

    public ArrayList<Chat> getHistory()
    {
        return history;
    }
}
