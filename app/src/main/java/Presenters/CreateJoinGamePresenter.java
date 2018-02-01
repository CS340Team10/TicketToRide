package Presenters;

import android.util.Log;

import com.example.cs340.tickettoride.Views.ICreateJoinGameView;

import common.Results;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public class CreateJoinGamePresenter implements ICreateJoinGamePresenter, IPresenter {
    private ICreateJoinGameView view;
    private final String tag = "CreateJoinGamePresenter";

    public CreateJoinGamePresenter(ICreateJoinGameView view) {
        this.view = view;
    }

    @Override
    public void createButtonPressed() {
        Log.d(tag, "createButtonPressed");
    }

    @Override
    public void textChanged() {
        Log.d(tag, "textChanged");
        view.setAvailableGames(new String[]{"test 1", "test 2", "test 3"});

    }

    @Override
    public void joinGamePressed(String name) {
        Log.d(tag, "joinGamePressed");

    }

    @Override
    public void onPostExecute(Results result) {
        Log.d(tag, "onPostExecute");

    }
}
