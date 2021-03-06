package Handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import Services.ServerCommandService;
import common.ICommand;
import common.Serializer;

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
        String[] requestBody = Serializer.getInstance().readInputStreamAsString(exchange.getRequestBody()).split("\n");
        String playerID = requestBody[0];

        // validate the player
        if (ServerCommandService.getInstance().playerIsValid(playerID)) {
            try {
                Integer commandIndex = Integer.parseInt(requestBody[1]);
                results = ServerCommandService.getInstance().getPlayerCommands(playerID, commandIndex);
            }
            catch (Exception exc) {
                // there was a problem receiving from body
            }
        }
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStream os = exchange.getResponseBody();
        os.write(Serializer.getInstance().serializeObject(results).getBytes());
        os.close();
    }
}
