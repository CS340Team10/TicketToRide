package Handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

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

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream os = exchange.getResponseBody();
        String outputString = "<html><title>ExecCommand Page</title><body><h1 style='text-align: center'>ExecCommand Page</h1></html>";

        os.write(outputString.getBytes());
        os.close();
    }
}
