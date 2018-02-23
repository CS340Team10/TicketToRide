package Services;

import android.util.Log;

import ClientModel.ClientModel;
import Presenters.IPresenter;


/**
 * Created by ephraimkunz on 1/31/18.
 * Modified by Joseph on 2/3/18
 *
 * This class provides services for the GUI, such as login, register, create and join game
 * capabilities.
 */
public class GUIService
{
    private static GUIService instance = new GUIService();
    private final String tag = "GUIService";

    private GUIService(){}

    public static GUIService getInstance() { return instance; }

    public void login (IPresenter presenter, String username, String password) {
        Log.d(tag, "Login with username: " + username + " password: " + password);

        GenericAsyncTask task = new GenericAsyncTask(presenter, "login", new String[]{"java.lang.String", "java.lang.String"});
        task.execute(username, password);
    }

    public void register(IPresenter presenter, String username, String password) {
        Log.d(tag, "Register with username: " + username + " password: " + password);

        GenericAsyncTask task = new GenericAsyncTask(presenter, "register", new String[]{"java.lang.String", "java.lang.String"});
        task.execute(username, password);
    }

    public void createGame(IPresenter presenter, String gameName, int numPlayers) {
        Log.d(tag, "Create game with name: " + gameName + " players: " + numPlayers);

        GenericAsyncTask task = new GenericAsyncTask(presenter, "createGame", new String[]{"java.lang.String", "int"});
        task.execute(gameName, numPlayers);
    }

    public void joinGame(IPresenter presenter, String gameName)
    {
        Log.d(tag, "Join game with name: " + gameName);

        GenericAsyncTask task = new GenericAsyncTask(presenter, "joinGame", new String[]{"java.lang.String", "java.lang.String"});
        task.execute(gameName,getClientModel().getUser().getId());
    }

    public ClientModel getClientModel()
    {
        return ClientModel.getInstance();
    }
}
