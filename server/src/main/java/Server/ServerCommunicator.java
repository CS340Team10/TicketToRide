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
import Services.ServerCommandService;

public class ServerCommunicator {


    private static final int SERVER_PORT_NUMBER = 8080;
    private static final int MAX_WAITING_CONNECTIONS = 10;

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
    public void run(int port) {
        // Steps in method:
        // 1. Initilize the server
        // 2. Set the contexts for the server
        // 3. Start the server
        // 4. Make sure that the database has been created correctly

        // initilize the server
        if (port < 0) {
            port = SERVER_PORT_NUMBER;
        }

        System.out.println("Starting Ticket To Ride Server on port " + port + "...");

        try {
            server = HttpServer.create(new InetSocketAddress(port), MAX_WAITING_CONNECTIONS);
        } catch (IOException exc) {
            System.err.println("Could not create server: " + exc.getMessage());
            exc.printStackTrace();
            return;
        }

        server.setExecutor(null); // use the default executor

        server.createContext(common.Endpoints.GAME_LIST_ENDPOINT, new GameListHandler());
        server.createContext(common.Endpoints.POLL_ENDPOINT, new PollHandler());
        server.createContext(common.Endpoints.EXEC_COMMAND_ENDPOINT, new ExecCommandHandler());

        server.createContext(common.Endpoints.DEFAULT_ENDPOINT, new DefaultHandler());

        server.start();

        System.out.println("\nServer running\n");

        // auto login the default players
        ServerCommandService.getInstance().login("player1", "password");
        ServerCommandService.getInstance().login("player2", "secret");
        ServerCommandService.getInstance().login("player3", "my_precious");

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

}