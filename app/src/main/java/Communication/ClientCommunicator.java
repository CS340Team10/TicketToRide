package Communication;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by matto on 2/1/2018.
 */

public class ClientCommunicator {

    private static ClientCommunicator _instance = null;
    private String IPAddress;
    private String port;

    public static ClientCommunicator get_instance()
    {
        if(_instance == null)
            _instance = new ClientCommunicator();
        return _instance;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Object post(String authToken, String body, Class ResultClass)
    {
        String response = getGSON("POST", authToken, body);
        return new Gson().fromJson(response, ResultClass);
    }

    public Object get(String authToken, String body, Class ResultClass)
    {
        String response = getGSON("GET", authToken, body);
        return new Gson().fromJson(response, ResultClass);
    }

    public String getURLString()
    {
        return "http://" + IPAddress + ":" + port + "/";
    }


    private String getGSON(String method, String authToken, String body) {
        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL(getURLString());

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            // Specify that we are sending an HTTP GET request
            http.setRequestMethod(method);
            // Indicate that this request will contain an HTTP request body
            http.setDoOutput(true);

            // Get the output stream containing the HTTP request body
            OutputStream reqBody = http.getOutputStream();
            // Write the JSON data to the request body
            writeString(body, reqBody);
            // Close the request body output stream, indicating that the
            // request is complete
            reqBody.close();

            http.setRequestProperty("Authorization", authToken);

            // Connect to the server and send the HTTP request
            http.connect();
            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = readString(respBody);
                // Display the JSON data returned from the server
                return respData;
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                throw(new IOException());
            }
        }
            catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return null;

    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /*
		The writeString method shows how to write a String to an OutputStream.
	*/
    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}
