package Services;

import android.util.Log;

import ClientModel.ClientModel;
import Presenters.IPresenter;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public class GUIService {
    private static GUIService instance = new GUIService();
    private final String tag = "GUIService";
    private ClientModel model = new ClientModel();

    private GUIService(){}

    public static GUIService getInstance() { return instance; }

    public void login (IPresenter presenter, String username, String password) {
        Log.d(tag, "Login with username: " + username + " password: " + password);
    }

    public void register(IPresenter presenter, String username, String password) {
        Log.d(tag, "Register with username: " + username + " password: " + password);
    }

    public void createGame(IPresenter presenter, String gameName, int numPlayers) {
        Log.d(tag, "Create game with name: " + gameName + " players: " + numPlayers);
    }

    public void joinGame(IPresenter presenter, String gameName) {
        Log.d(tag, "Join game with name: " + gameName);
    }

    public ClientModel getClientModel() {
        return model;
    }
}
