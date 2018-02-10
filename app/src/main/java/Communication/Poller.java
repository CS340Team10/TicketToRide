package Communication;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import Services.ClientGameService;
import Services.GUIService;
import common.Command;
import common.Endpoints;

/**
 * Created by Joseph on 2/2/2018.
 */

public class Poller {
    private static Poller _instance = new Poller();

    private int pollPeriodMS = 1000; // set poll period, default at 1 second
    private static ScheduledExecutorService _threadInstance; // separate thread to complete polling
    protected enum pollTypes {COMMAND, GAME}
    private static pollTypes currentPollType = pollTypes.GAME;

    public static Poller getInstance() {
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
        ClientCommunicator communicator = ClientCommunicator.getInstance(); // get communicator instance
        String playerID = GUIService.getInstance().getClientModel().getUser().getId();
        Type listType = new TypeToken<List<Command>>(){}.getType(); // create deserialization type

        List<Command> commandList = (List<Command>) communicator.get(Endpoints.POLL_ENDPOINT, "", playerID, listType); // send command, get results

        return commandList;
    }


    /**
     * Calls ClientCommunicator to get game list
     *
     * @return game list if successful, null otherwise
     */
    private List<String> fetchGames()
    {
        ClientCommunicator communicator = ClientCommunicator.getInstance();

        Type listType = new TypeToken<List<String>>(){}.getType(); // get deserialization type for List<String>
        List<String> gameList = (List<String>) communicator.get(Endpoints.GAME_LIST_ENDPOINT, "authToken", "", listType); //send command, get result

        return gameList;
    }

    class PollerThread implements Runnable{

        public void run()
        {
            try {
                if (currentPollType == pollTypes.GAME) {
                    final List<String> gameList = fetchGames(); // get game list

                    Handler mainHandler = new Handler(Looper.getMainLooper());

                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            ClientGameService.getInstance().updateGameList(gameList); // update client with fresh game list
                        }
                    };
                    mainHandler.post(myRunnable);

                } else if (currentPollType == pollTypes.COMMAND) {
                    List<Command> commandList = fetchCommands();
                    for (final Command c : commandList) {

                        Handler mainHandler = new Handler(Looper.getMainLooper());

                        Runnable myRunnable = new Runnable() {
                            @Override
                            public void run() {
                                c.execute();
                            }
                        };
                        mainHandler.post(myRunnable);
                    }
                }
            } catch(Exception e)
            {
                System.out.println(e.toString());
            }
        }
    }

}
