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

    public ArrayList<String> getDisplayHistory() {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Player> players = ClientModel.getInstance().getGame().getPlayers();

        for (Chat chat: history) {
            for (Player player: players) {
                if (chat.getPlayerID().equals(player.getId())) {
                    result.add(player.getUsername() + " : " + chat.getMessage());
                    break;
                }
            }
        }

        return result;
    }
}
