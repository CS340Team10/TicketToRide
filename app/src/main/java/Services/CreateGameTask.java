package Services;

import android.os.AsyncTask;

import Communication.ServerProxy;
import Presenters.IPresenter;
import common.Results;

/**
 * Created by ephraimkunz on 2/9/18.
 */

public class CreateGameTask extends AsyncTask<CreateGameParams, Void, Results> {
    /**
     * This will be the object that is notified when the task is complete
     */
    private IPresenter myPresenter;
    private final String tag = "CreateGameTask";

    /**
     * Create a new Login or Register Task
     * @param presenter the object to be notified when the login/register is complete
     */
    public CreateGameTask(IPresenter presenter) {
        myPresenter = presenter;
    }

    @Override
    protected Results doInBackground(CreateGameParams... param) {
        Results result = new Results(false, "",
                "ERROR: could not execute command!");   //By default, assume it doesn't work

        try
        {
            CreateGameParams params = param[0];   //Get the username (the first parameter in execute())
            result = ServerProxy.get_instance().createGame(params.getName(), params.getNumPlayers());
            return result;
        }
        catch (Exception e)                 //If the login/register failed
        {
            e.printStackTrace();            //Print out the exception
            return result;                  //Return the result with an error code
        }
    }

    @Override
    protected void onPostExecute(Results results) {
        myPresenter.onPostExecute(results);
    }
}
