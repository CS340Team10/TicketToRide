package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;

/**
 * Created by Brian on 2/1/18.
 */

public abstract class GenericHandler implements HttpHandler {

    /**
     * Handles the Event request
     *
     * @param exchange the HttpExchange Object to use in the request
     */
    @Override
    abstract public void handle(HttpExchange exchange) throws IOException;


    /**
     * Returns the path from the HttpExchange Object
     *
     * @param exchange the HttpExchange Object to use
     *
     * @return the path specified in the HttpExchange Object
     */
    public String getPathString(HttpExchange exchange){
        // Steps in method:
        // 1. Return the path from the exchange

        URI uri = exchange.getRequestURI();
        String pathString = uri.getPath();

        return pathString;
    }

    /**
     * Returns the path from the HttpExchange Object in a String array
     *
     * @param exchange the HttpExchange Object to use
     *
     * @return the path from the HttpExchange Object in a String array
     */
    public String[] getPathArray(HttpExchange exchange){
        // Steps in method:
        // 1. Get the path
        // 2. Split the path on the "/" character

        // get the path
        String path = getPathString(exchange);

        if (path.startsWith("/")){
            path = path.substring(1);
        }

        // split the path on the "/" character
        return path.split("/");
    }
}
