package Presenters;

import com.example.cs340.tickettoride.Views.ClientRestoreActivity;
import com.example.cs340.tickettoride.Views.GamePlayActivity;
import com.example.cs340.tickettoride.Views.IClientRestoreView;

import java.util.List;

import ClientModel.ClientModel;
import Communication.Poller;
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

        onPostRestore();
    }

    @Override
    public void beginRestore() {
        ClientModel model = ClientModel.getInstance();
        List<ICommand> commands = Poller.getInstance().fetchCommands();
        for(ICommand command : commands) // execute all commands
        {
            command.execute();
        }
        if(model.getUser().getDestCards().size() == 0) // if we have not chosen original cards, game state is BeginGameState
        {
            model.setState(new StartGameState());
        }
        else if(!model.getUser().isMyTurn()) // if it's not our turn, state is NotMyTurnState
        {
            model.setState(new NotMyTurnState());
        }
        else for(int i = commands.size() - 1; i >= 0; i--)
        {
            if(commands.get(i).toString().contains("offerDestCards"))
            {
                model.setState(new RequestedDestCardsState());
                return;
            }
            if(commands.get(i).toString().contains("trainCardChosen"))
            {
                model.setState(new PickedFirstTrainState());
                return;
            }
            if(commands.get(i).toString().contains("turnBegan"))
            {
                model.setState(new MyTurnState());
                return;
            }
        }


    }

    @Override
    public void onPostRestore() {
        mView.switchToView(GamePlayActivity.class);
    }
}
