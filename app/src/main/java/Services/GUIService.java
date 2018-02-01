package Services;

import android.util.Log;

import Presenters.IPresenter;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public class GUIService {
    private static GUIService instance = new GUIService();
    private final String tag = "GUIService";

    private GUIService(){}

    public static GUIService getInstance() { return instance; }

    public void login (IPresenter presenter, String username, String password) {
        Log.d(tag, "Login with username: " + username + " password: " + password);
    }

    public void register(IPresenter presenter, String username, String password) {
        Log.d(tag, "Register with username: " + username + " password: " + password);
    }
}
