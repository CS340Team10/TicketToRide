package Presenters;

import com.example.cs340.tickettoride.Views.ICreateJoinGameView;
import com.example.cs340.tickettoride.Views.WaitForGameActivity;

import java.util.Observable;
import java.util.Observer;

import Services.GUIService;
import common.Results;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public class CreateJoinGamePresenter implements ICreateJoinGamePresenter, IPresenter, Observer {
    private ICreateJoinGameView view;
    private final String tag = "CreateJoinGamePresenter";
    private boolean waitingForServer = false;

    public CreateJoinGamePresenter(ICreateJoinGameView view) {
        this.view = view;

        // TODO: Register as model observer

    }

    @Override
    public void createButtonPressed() {
        waitingForServer = true;
        view.setCreateGameButtonEnabled(false);

        String name = view.getNewGameName();
        int numPlayers = view.getNewGameNumPlayers();
        GUIService.getInstance().createGame(this, name, numPlayers);

        onPostExecute(new Results(true, "game5", ""));
    }

    @Override
    public void textChanged() {
        if (!waitingForServer) {
            view.setCreateGameButtonEnabled(!view.getNewGameName().isEmpty());
        }
    }

    @Override
    public void joinGamePressed(String name) {
        if(!waitingForServer) {
            waitingForServer = true;
            view.setCreateGameButtonEnabled(false);
            GUIService.getInstance().joinGame(this, name);
//            onPostExecute(new Results(true, "game5", ""));
        }
    }

    @Override
    public void onPostExecute(Results result) {
        if (result.succeeded() ) {
            if (result.getData().isEmpty()) { // Success in creating game
                waitingForServer = false;
                textChanged();
            } else { // Success in joining game
                view.switchToView(WaitForGameActivity.class);
            }
        } else {
            waitingForServer = false;
            textChanged();
            view.displayErrorMessage(result.getError());
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        // TODO: Get the list of games and call view.setGameList()
    }
}
