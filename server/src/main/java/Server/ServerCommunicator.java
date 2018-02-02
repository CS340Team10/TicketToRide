package Server;

/*
  Created by Brian on 2/1/18.
 */

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import Handlers.DefaultHandler;
import Handlers.ExecCommandHandler;
import Handlers.GameListHandler;
import Handlers.PollHandler;

public class ServerCommunicator {


    private static final int SERVER_PORT_NUMBER = 8080;
    private static final int MAX_WAITING_CONNECTIONS = 10;

    // define the values for the handlers
    public static final String DEFAULT_DESIGNATOR          = "/";
    public static final String GAME_LIST_DESIGNATOR        = "/gameList/";
    public static final String POLL_DESIGNATOR             = "/poll/";
    public static final String EXEC_COMMAND_DESIGNATOR     = "/execCommand/";

    private HttpServer server;

    /*
     * Sets up the variables needed and starts the server
     */
    public void run(){
        // Steps in method:
        // 1. Run the server on the default port

        run(-1);
    }

    /**
     * Sets up the variables needed and starts the server
     */
    public void run(int port){
        // Steps in method:
        // 1. Initilize the server
        // 2. Set the contexts for the server
        // 3. Start the server
        // 4. Make sure that the database has been created correctly

        // initilize the server
        if (port < 0){
            port = SERVER_PORT_NUMBER;
        }

        System.out.println("Starting Full Server on port " + port + "...");

        try {
            server = HttpServer.create(new InetSocketAddress(port), MAX_WAITING_CONNECTIONS);
        }
        catch (IOException exc) {
            System.err.println("Could not create server: " + exc.getMessage());
            exc.printStackTrace();
            return;
        }

        server.setExecutor(null); // use the default executor

        server.createContext(GAME_LIST_DESIGNATOR, new GameListHandler());
        server.createContext(POLL_DESIGNATOR, new PollHandler());
        server.createContext(EXEC_COMMAND_DESIGNATOR, new ExecCommandHandler());

        server.createContext(DEFAULT_DESIGNATOR, new DefaultHandler());

        server.start();

        System.out.println("\nServer running\n");
    }

    /**
     * Returns the server port number
     *
     * @return the server port number
     */
    public static int getServerPortNumber(){
        // Steps in method:
        // 1. Return the server port number

        return SERVER_PORT_NUMBER;
    }

    /**
     * Returns the maximum number of connections available
     *
     * @return the maximum number of connections available
     */
    public static int getMaxAvailableConnections(){
        // Steps in method:
        // 1. Return the max number of server connections

        return MAX_WAITING_CONNECTIONS;
    }

    /**
     * Attempts to turn the String into an integer. Returns a 0 if this is not possible.
     *
     * @param intString the String to convert to an integer.
     *
     * @return the parameter converted to an integer, or a zero if this is not possible.
     */
    public static int parseInt(String intString){
        int returnValue = 0;

        try {
            returnValue = Integer.parseInt(intString);
        }
        catch (Exception exc){
            returnValue = 0;
        }

        return returnValue;
    }

    /**
     * Starts the server
     *
     * @param args the arguements for the server
     */
    public static void main(String[] args){
        // Steps in method:
        // 1. Start the server

        if (args.length > 0){
            new ServerCommunicator().run(parseInt(args[0]));
        }
        else {
            new ServerCommunicator().run();
        }
    }

}