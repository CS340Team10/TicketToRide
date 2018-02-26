package ClientModel;

/**
 * Created by matto on 2/25/2018.
 */

public class Chat {
    private String playerID;
    private String message;

    public Chat(String playerID, String message)
    {
        this.playerID = playerID;
        this.message = message;
    }

    public String getPlayerID() {
        return playerID;
    }

    public String getMessage() {
        return message;
    }
}
