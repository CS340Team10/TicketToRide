package Communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import ClientModel.Game;
import common.Command;
import common.Results;

/**
 * Created by Joseph on 2/2/2018.
 */

public class Poller {
    private static Poller _instance = new Poller();

    private int pollPeriodMS = 1000; // set poll period, default at 1 second
    private static ScheduledExecutorService _threadInstance; // separate thread to complete polling
    protected enum pollTypes {COMMAND, GAME}
    private static pollTypes currentPollType = pollTypes.GAME;

    public static Poller get_instance() {
        return _instance;
    }


    /**
     * Starts a poller that will keep Command list updated
     */
    public void startCommandPoll() {
        if(_threadInstance != null)
        {
            _threadInstance.shutdown();
        }
        currentPollType = pollTypes.COMMAND; // set polling type
        _threadInstance = Executors.newSingleThreadScheduledExecutor(); //create polling thread
        ScheduledFuture future = _threadInstance.scheduleWithFixedDelay(new PollerThread(), 0, pollPeriodMS, TimeUnit.MILLISECONDS); //start polling thread
    }

    /**
     * Stops the poller that will keep Command list updated
     */
    public void stopCommandPoll() {
        _threadInstance.shutdown();
    }

    /**
     * Starts a poller that will keep Game list updated
     */
    public void startGamePoll()
    {
        if(_threadInstance != null)
        {
            _threadInstance.shutdown(); //shut down polling thread if it is active
        }
        currentPollType = pollTypes.GAME; // set polling type
        _threadInstance = Executors.newSingleThreadScheduledExecutor(); // create polling thread
        ScheduledFuture future = _threadInstance.scheduleWithFixedDelay(new PollerThread(), 0, pollPeriodMS, TimeUnit.MILLISECONDS); // start polling thread
    }

    /**
     * Stops a poller that will keep Game list updated
     */
    public void stopGamePoll()
    {
        _threadInstance.shutdown();
    }

    /**
     * Calls ClientCommunicator to get command list
     *
     * @return command list if successful, null otherwise
     */
    private List<Command> fetchCommands()
    {
        ClientCommunicator communicator = ClientCommunicator.get_instance(); // get communicator instance
        String playerID = "playerID";

        Results results = (Results) communicator.get("poll", "authToken", playerID, Results.class); // send command, get results

        if(!results.succeeded()) // will return null if there was some error
            return null;

        Type listType = new TypeToken<List<Command>>(){}.getType(); // create deserialization type
        List<Command> commandList = new Gson().fromJson(results.getData(), listType); // get list of commands from results JSON
        return commandList;
    }


    /**
     * Calls ClientCommunicator to get game list
     *
     * @return game list if successful, null otherwise
     */
    private List<Game> fetchGames()
    {
        ClientCommunicator communicator = ClientCommunicator.get_instance();

        Results results = (Results) communicator.get("gameList", "authToken", "", Results.class); //send command, get result

        if(!results.succeeded()) // return null if there was an error
            return null;

        Type listType = new TypeToken<List<Game>>(){}.getType(); // get deserialization type for List<Game>
        List<Game> gameList = new Gson().fromJson(results.getData(), listType); //get gameList from JSON
        return gameList;
    }

    class PollerThread implements Runnable{

        public void run()
        {
            if(currentPollType == pollTypes.GAME)
            {
                List<Game> gameList = fetchGames(); // get game list
                ClientGameService.get_instance().updateGameList(gameList); // update client with fresh game list
            }
            else if(currentPollType == pollTypes.COMMAND)
            {
                List<Command> commandList = fetchCommands();
                for(Command c : commandList)
                {
                    c.execute();
                }
            }
        }
    }

}
