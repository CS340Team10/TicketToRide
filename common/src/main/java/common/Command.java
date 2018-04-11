package common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Method;

/**
 * Created by Joseph on 2/2/2018.
 */
public class Command implements ICommand, Serializable
{
    private String className = "";
    private String methodName = "";
    private String[] paramTypeNames = new String[20];
    private Object[] paramValues = new Object[20];
    private boolean callStatic = false;

    /**
     * A generic command object that executes the given method on the given receiver with the
     * given parameters.
     * @param className the name of the receiver class, but in string form
     * @param methodName the name of the method to be run by the receiver instance
     * @param paramTypeNames A list of the names of each type of each parameter
     * @param paramValues A list of the parameters
     */
    public Command(String className, String methodName, String[] paramTypeNames, Object[] paramValues)
    {
        this.className = className;
        this.methodName = methodName;
        this.paramTypeNames = paramTypeNames;
        this.paramValues = paramValues;
    }

    /**
     * A generic command object that executes the given method on the given receiver with the
     * given parameters.
     * @param className the name of the receiver class, but in string form
     * @param methodName the name of the method to be run by the receiver instance
     * @param paramTypeNames A list of the names of each type of each parameter
     * @param paramValues A list of the parameters
     * @param callStatic whether the method in the execute() method should be called static
     */
    @JsonCreator
    public Command(@JsonProperty("className") String className, @JsonProperty("methodName") String methodName, @JsonProperty("paramTypeNames") String[] paramTypeNames, @JsonProperty("paramValues") Object[] paramValues, @JsonProperty("callStatic") boolean callStatic)
    {
        this.className = className;
        this.methodName = methodName;
        this.paramTypeNames = paramTypeNames;
        this.paramValues = paramValues;
        this.callStatic = callStatic;
    }

    @Override
    public Results execute()
    {
        Results result = new Results(false, "", "ERROR: could not execute command!");//By default, assume it doesn't work; "Non-functioning until proven otherwise"
        try
        {
            System.out.print("Executing " + methodName + "(");
            for (int count = 0; count < paramValues.length; count++) {
                if (count > 0){
                    System.out.print(", ");
                }
                System.out.print("\"" + paramValues[count] + "\"");
            }

            System.out.println(");");

            //System.out.println("Attempting to execute command. Getting class...");
            Class<?> receiverClass = Class.forName(className);
            //System.out.println("Class "+ className +" was found! Attempting to make parameter list...");
            Class<?>[] _paramTypes = new Class<?>[paramTypeNames.length];  //Initialize an array of types
            for (int i = 0; i < paramTypeNames.length; i++)                //Loop through the array of type names
            {
                _paramTypes[i] = Class.forName(paramTypeNames[i]);         //Get each type
            }

            Object singleton = null;

            if (!callStatic) {
                Method getInstance = receiverClass.getMethod("getInstance", new Class<?>[]{});
                singleton = getInstance.invoke(null, new Object[]{});
            }

            Method[] methods = receiverClass.getMethods();
            Method method = receiverClass.getMethod(methodName, _paramTypes);//Get the indicated method
            Object o = method.invoke(singleton, paramValues);                  //Call the indicated method
            if (o != null && o instanceof Results)                          //if the returned object is a result of some kind
            {
                result = (Results) o;
            }
            else
            {
                System.out.println("Method invoked, but no object was returned!");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String stackTrace = sw.toString();
            result = new Results(false, "", "Error: " + e.toString() + "\n" + stackTrace);
        }
        finally {
            return result;
        }
    }

    @Override
    public String playerId() {
        if (methodName.equals("login") || methodName.equals("register") || methodName.equals("createGame")) {
            return ""; // No playerId for these methods.
        }

        return (String) paramValues[0]; // First argument is a playerId.
    }

    @Override
    public String toString(){
        String returnString = "";

        returnString += "Class:  " + className + "\n";
        returnString += "Method: " + methodName;

        return returnString;
    }

    @Override
    public String methodName() {
        return methodName;
    }

    @Override
    public String gameName() {
        if (paramValues.length >= 2) {
            return (String)paramValues[1];
        }

        return "You did a very bad thing by not calling methodName() first";
    }
}
