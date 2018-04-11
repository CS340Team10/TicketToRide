package Presenters;

import com.example.cs340.tickettoride.Views.ClientRestoreActivity;
import com.example.cs340.tickettoride.Views.GamePlayActivity;
import com.example.cs340.tickettoride.Views.IClientRestoreView;

import java.util.List;

import ClientModel.ClientModel;
import Communication.Poller;
import Services.RestoreTask;
import States.MyTurnState;
import States.NotMyTurnState;
import States.PickedFirstTrainState;
import States.RequestedDestCardsState;
import States.StartGameState;
import common.Command;
import common.ICommand;

/**
 * Created by matto on 4/6/2018.
 */

public class ClientRestorePresenter implements IClientRestorePresenter {
    private IClientRestoreView mView;

    public ClientRestorePresenter(IClientRestoreView view)
    {
        mView = view;

        beginRestore();
    }

    @Override
    public void beginRestore()
    {
        RestoreTask task = new RestoreTask(this);
        task.execute();
    }

    @Override
    public void onPostRestore() {
        mView.switchToView(GamePlayActivity.class);
    }
}
