package Services;

/**
 * Created by ephraimkunz on 2/9/18.
 */

public class CreateGameParams {
    private int numPlayers;
    private String name;

    public CreateGameParams(int numPlayers, String name) {
        this.numPlayers = numPlayers;
        this.name = name;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public String getName() {
        return name;
    }
}
