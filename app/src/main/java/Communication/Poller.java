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
    private int pollPeriodMS = 1000;
    private static ScheduledExecutorService _threadInstance;
    protected enum pollTypes {COMMAND, GAME}
    private static pollTypes currentPollType = pollTypes.GAME;

    public static Poller get_instance() {
        return _instance;
    }

    public void startCommandPoll() {
        if(_threadInstance != null)
        {
            _threadInstance.shutdown();
        }
        currentPollType = pollTypes.COMMAND;
        _threadInstance = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture future = _threadInstance.scheduleWithFixedDelay(new PollerThread(), 0, pollPeriodMS, TimeUnit.MILLISECONDS);
    }

    public void stopCommandPoll() {
        _threadInstance.shutdown();
    }

    public void startGamePoll()
    {
        if(_threadInstance != null)
        {
            _threadInstance.shutdown();
        }
        currentPollType = pollTypes.GAME;
        _threadInstance = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture future = _threadInstance.scheduleWithFixedDelay(new PollerThread(), 0, pollPeriodMS, TimeUnit.MILLISECONDS);
    }

    public void stopGamePoll()
    {
        _threadInstance.shutdown();
    }

    private List<Command> fetchCommands()
    {
        ClientCommunicator communicator = ClientCommunicator.get_instance();
        ServerCommand command = ServerCommandFactory.createGetCommandsCommand("playerID");
        String commandJSON = new Gson().toJson(command, ServerCommand.class);
        Results results = (Results) communicator.get("authToken", commandJSON, Results.class);
        if(!results.succeeded())
            return null;
        Type listType = new TypeToken<List<Command>>(){}.getType();
        List<Command> commandList = new Gson().fromJson(results.getData(), listType);
        return commandList;
    }

    private List<Game> fetchGames()
    {
        ClientCommunicator communicator = ClientCommunicator.get_instance();
        ServerCommand command = ServerCommandFactory.createGetGamesCommand();
        String commandJSON = new Gson().toJson(command, ServerCommand.class);
        Results results = (Results) communicator.get("authToken", commandJSON, Results.class);
        if(!results.succeeded())
            return null;
        Type listType = new TypeToken<List<Game>>(){}.getType();
        List<Game> gameList = new Gson().fromJson(results.getData(), listType);
        return gameList;
    }

    class PollerThread implements Runnable{

        public void run()
        {
            if(currentPollType == pollTypes.GAME)
            {
                List<Game> gameList = fetchGames();
                ClientGameService.get_instance().updateGameList(gameList);
            }
            else if(currentPollType == pollTypes.COMMAND)
            {
                List<Command> commandList = fetchCommands();
            }
        }
    }

    public static void main(String[] args)
    {
        Poller poller = Poller.get_instance();
        poller.startGamePoll();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        poller.stopGamePoll();
        poller.startCommandPoll();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        poller.stopCommandPoll();
    }
}
