package Server;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Brian on 2/6/18.
 */

public class Serializer {

    private static final Gson _GSON = new Gson();

    /**
     * Converts the Object into a serialized String
     *
     * @param obj the Object to serialize
     *
     * @return a serialized version of the Object passed in
     */
    public static String serializeObject(Object obj){
        try {

            return _GSON.toJson(obj);
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Writes the serialized Object to the OutputStreamWriter
     *
     * @param obj the Object to serialize
     * @param writer the OutputStreamWriter to write the Object to
     *
     * @return a serialized version of the Object passed in
     */
    public static void serializeObject(Object obj, OutputStreamWriter writer){
        try {
            _GSON.toJson(obj, writer);
        }
        catch (Exception e){

        }
    }

    /**
     * Converts a serialized OutputStream back into an Object
     *
     * @param stream the OutputStream with a serialized version of an Object
     * @param classType the type of Class that the Object should be returned as
     *
     * @return the deserialized version of the String passed in
     */
    public static Object deserializeInputStream(InputStream stream, Class classType){

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            Object tempObject = classType.cast(_GSON.fromJson(inputStreamReader, classType));

            return tempObject;
        }
        catch (Exception exc){
            exc.printStackTrace();
            return null;
        }
    }

    /**
     * Converts a serialized String back into an Object
     *
     * @param objString the OutputStream with a serialized version of an Object
     * @param classType the type of Class that the Object should be returned as
     *
     * @return the deserialized version of the String passed in
     */
    public static Object deserializeString(String objString, Class classType){
        try {
            Object tempObject = classType.cast(_GSON.fromJson(objString, classType));

            return tempObject;
        }
        catch (Exception exc){
            exc.printStackTrace();
            return null;
        }
    }

    public static String readInputStreamAsString(InputStream stream){
        java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}