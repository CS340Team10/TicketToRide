package Services;

import android.os.AsyncTask;
import android.util.Log;

import Communication.ServerProxy;
import Presenters.IPresenter;
import common.Results;

/**
 * Created by Joseph on 2/9/2018.
 */

public class JoinGameTask extends AsyncTask<String, Void, Results>
{
    private IPresenter myPresenter;
    private final String tag = "LoginRegisterTask";

    public JoinGameTask(IPresenter presenter)
    {
        this.myPresenter = presenter;
    }

    @Override
    protected Results doInBackground(String... strings)
    {
        Results result = new Results(false, "", "ERROR: Could not Join Game.");    //Assume failure until proven otherwise
        try
        {
            String gameName = strings[0];
            String playerID = strings[1];
            Results temp_res = ServerProxy.get_instance().joinGame(gameName,playerID);
            if (temp_res == null)
            {
                Log.d(tag, "doInBackground: ERROR - null Results returned from ServerProxy");
                result.setError("ERROR - null Results returned from ServerProxy");
            }
            else
            {
                result = temp_res;
            }
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return result;
        }
    }

    @Override
    protected void onPostExecute(Results results)
    {
        myPresenter.onPostExecute(results);
    }
}
