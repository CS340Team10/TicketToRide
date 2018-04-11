package Handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import Plugins.DataFlush;
import Services.ServerCommandService;
import common.Command;
import common.Results;
import common.Serializer;

/**
 * Created by Brian on 2/1/18.
 */

public class ExecCommandHandler extends GenericHandler {

    /**
     * Handles the Event request
     *
     * @param exchange the HttpExchange Object to use in the request
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException{

        Command request = (Command) Serializer.getInstance().deserializeInputStream(exchange.getRequestBody(), Command.class);

        // save the command for the game
        if (!request.playerId().equals("")){
            System.out.println("saving for " + request.playerId());
            String gameName = ServerCommandService.getGameNameForPlayerID(request.playerId());

            if (!gameName.equals("")){
                DataFlush.saveCommand(gameName, request);
                System.out.println("saving command");
            }
            else {
                // check if this is a joinGame command
                if (request.methodName().equals("joinGame")){
                    System.out.println("joining game");
                    DataFlush.saveCommand(request.gameName(), request);
                }
            }
        }
        else {
            System.out.println("ignoring " + request.toString());
        }

        // execute the command against the running server
        Results results = request.execute();

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStream os = exchange.getResponseBody();
        os.write(Serializer.getInstance().serializeObject(results).getBytes());
        os.close();
    }
}
