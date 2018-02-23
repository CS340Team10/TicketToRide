package common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Brian on 2/6/18.
 */

public class Serializer {
    private static Serializer instance = new Serializer();

    private ObjectMapper mapper;

    private Serializer() {
        this.mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY); // Avoid annotating types
        mapper.enableDefaultTyping(); // Handle heterogeneous collections
    }

    public static Serializer getInstance() {
        return instance;
    }

    /**
     * Converts the Object into a serialized String
     *
     * @param obj the Object to serialize
     *
     * @return a serialized version of the Object passed in
     */
    public String serializeObject(Object obj){
        try {
            return mapper.writeValueAsString(obj);
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
    public void serializeObject(Object obj, OutputStreamWriter writer){
        try {
            mapper.writeValue(writer, obj);
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
    public Object deserializeInputStream(InputStream stream, Class classType){

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            Object tempObject = mapper.readValue(inputStreamReader, classType);

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
    public Object deserializeString(String objString, Class classType){
        try {
            Object tempObject = mapper.readValue(objString, classType);

            return tempObject;
        }
        catch (Exception exc){
            exc.printStackTrace();
            return null;
        }
    }

    public String readInputStreamAsString(InputStream stream){
        java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

//    public static void main(String[] args) {
//        ArrayList<DestCard> list = new ArrayList<>();
//        list.add(new DestCard());
//        ICommand c = ClientCommandFactory.createDestCardsChosenCommand("player", list);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping();
//        Object tempObject = null;
//        try {
//            String obj = om.writeValueAsString(c);
//            tempObject = om.readValue(obj, Command.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Object o = tempObject;
//    }
}