package Services;

import android.os.AsyncTask;

import java.util.List;

import ClientModel.ClientModel;
import Communication.Poller;
import Presenters.IClientRestorePresenter;
import States.MyTurnState;
import States.NotMyTurnState;
import States.PickedFirstTrainState;
import States.RequestedDestCardsState;
import States.StartGameState;
import common.ICommand;

/**
 * Created by Joseph on 4/11/2018.
 */

public class RestoreTask extends AsyncTask<Void, Void, Void>
{
    IClientRestorePresenter presenter;
    public RestoreTask(IClientRestorePresenter presenter)
    {
        this.presenter = presenter;
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
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
                    return null;
                }
                if(commands.get(i).toString().contains("trainCardChosen"))
                {
                    model.setState(new PickedFirstTrainState());
                    return null;
                }
                if(commands.get(i).toString().contains("turnBegan"))
                {
                    model.setState(new MyTurnState());
                    return null;
                }
            }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        presenter.onPostRestore();
    }
}
