package Handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import Server.Serializer;
import common.Command;
import common.Results;

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

        Command request = (Command) Serializer.deserializeInputStream(exchange.getRequestBody(), Command.class);
        Results results = request.execute();

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStream os = exchange.getResponseBody();
        os.write(Serializer.serializeObject(results).getBytes());
        os.close();
    }
}
