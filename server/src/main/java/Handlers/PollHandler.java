package Handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;

import Server.Serializer;
import Services.ClientCommandFactory;
import Services.ServerCommandService;
import common.ICommand;

/**
 * Created by Brian on 2/1/18.
 */

public class PollHandler extends GenericHandler {

    /**
     * Handles the Event request
     *
     * @param exchange the HttpExchange Object to use in the request
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException{

        ICommand[] results = new ICommand[]{};

        // get the player requesting the commands
        String playerID = Serializer.readInputStreamAsString(exchange.getRequestBody());

        // validate the player
        if (ServerCommandService.getInstance().playerIsValid(playerID)){
            results = ServerCommandService.getInstance().getPlayerCommands(playerID);
        }

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStream os = exchange.getResponseBody();
        os.write(Serializer.serializeObject(results).getBytes());
        os.close();
    }
}
