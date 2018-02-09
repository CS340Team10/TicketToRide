package Handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import Server.Serializer;
import Services.ServerCommandService;
import common.Command;

/**
 * Created by Brian on 2/1/18.
 */

public class GameListHandler extends GenericHandler {

    /**
     * Handles the Event request
     *
     * @param exchange the HttpExchange Object to use in the request
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException{

        String request = Serializer.readInputStreamAsString(exchange.getRequestBody());
        System.out.println(request);

        ArrayList<String> results = ServerCommandService.getInstance().getAvailableGames();

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStream os = exchange.getResponseBody();
        os.write(Serializer.serializeObject(results).getBytes());
        os.close();
    }
}
