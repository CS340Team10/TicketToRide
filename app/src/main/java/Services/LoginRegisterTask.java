package Services;

import android.os.AsyncTask;
import android.util.Log;

import Communication.ServerProxy;
import Presenters.IPresenter;
import common.Results;

/**
 * Created by Joseph on 2/3/2018.
 *
 * Calls login or register on the Server Proxy asynchronously
 *
 * When calling execute() on this task, the first parameter should be the username and the
 * second parameter should be the password
 */

public class LoginRegisterTask extends AsyncTask<String, Void, Results>
{
    /**
     * This enum is used to define whether this task is meant to login or register the user
     *
     * Upon construction of this task, pass in REGISTER if you want to register the user
     * otherwise, if you want to login a previously registered user, pass in LOGIN
     */
    public enum OPTIONS
    {
        REGISTER,
        LOGIN
    }

    /**
     * This will be the object that is notified when the task is complete
     */
    private IPresenter myPresenter;
    private OPTIONS option;
    private final String tag = "LoginRegisterTask";

    /**
     * Create a new Login or Register Task
     * @param presenter the object to be notified when the login/register is complete
     * @param option whether to LOGIN or REGISTER the user should be indicated with the enum OPTIONS
     */
    public LoginRegisterTask(IPresenter presenter, OPTIONS option)
    {
        myPresenter = presenter;
        this.option = option;
    }

    @Override
    protected Results doInBackground(String... strings)
    {
        Results result = new Results(false, "",
                "ERROR: could not login or register!");//By default, assume it doesn't work
                                                        // "Non-functioning until proven otherwise"
        //Attempt to login or register the user
        try
        {
            String username = strings[0];   //Get the username (the first parameter in execute())
            String password = strings[1];   //Get the password (the second parameter in execute())
            Results temp_res = null;
            switch (option)
            {
                case LOGIN:                 //Login the user if that was the decided behavior
                    temp_res = ServerProxy.getInstance().login(username, password);
                    break;
                case REGISTER:              //Register the user if that was the decided behavior
                    temp_res = ServerProxy.getInstance().register(username,password);
                    break;
                default:                    //Otherwise do nothing and return the result with error
                    break;
            }
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
        catch (Exception e)                 //If the login/register failed
        {
            e.printStackTrace();            //Print out the exception
            return result;                  //Return the result with an error code
        }
    }

    /**
     * After login or register, notify the presenter that we have finished
     * @param results the object containing the results of the login/register, possibly an error
     */
    @Override
    protected void onPostExecute(Results results)
    {
        // Save playerId if there is one
        if (results.succeeded()) {
            GUIService.getInstance().getClientModel().getUser().setId(results.getData());
        }

        myPresenter.onPostExecute(results);
    }
}
