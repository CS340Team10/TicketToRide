package common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

/**
 * Created by Joseph on 2/2/2018.
 */

public class Command implements ICommand
{
    private String _className = "";
    private String _methodName = "";
    private String[] _paramTypeNames = new String[20];
    private Object[] _paramValues = new Object[20];
    private boolean _callStatic = false;

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
        _className = className;
        _methodName = methodName;
        _paramTypeNames = paramTypeNames;
        _paramValues = paramValues;
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
    public Command(String className, String methodName, String[] paramTypeNames, Object[] paramValues, boolean callStatic)
    {
        _className = className;
        _methodName = methodName;
        _paramTypeNames = paramTypeNames;
        _paramValues = paramValues;
        _callStatic = callStatic;
    }

    @Override
    public Results execute()
    {
        Results result = new Results(false, "", "ERROR: could not execute command!");//By default, assume it doesn't work; "Non-functioning until proven otherwise"
        try
        {
            System.out.println("Attempting to execute command. Getting class...");
            Class<?> receiverClass = Class.forName(_className);
            System.out.println("Class "+_className+" was found! Attempting to make parameter list...");
            Class<?>[] _paramTypes = new Class<?>[_paramTypeNames.length];  //Initialize an array of types
            for (int i = 0; i < _paramTypeNames.length; i++)                //Loop through the array of type names
            {
                _paramTypes[i] = Class.forName(_paramTypeNames[i]);         //Get each type
                System.out.println(_paramTypeNames[i] + "\t" + _paramValues[i].getClass() );
            }

            Object singleton = null;

            if (!_callStatic) {
                Method getInstance = receiverClass.getMethod("getInstance", new Class<?>[]{});
                singleton = getInstance.invoke(null, new Object[]{});
            }

            Method method = receiverClass.getMethod(_methodName, _paramTypes);//Get the indicated method
            Object o = method.invoke(singleton, _paramValues);                  //Call the indicated method
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
    public String toString(){
        String returnString = "";

        returnString += "Class:  " + _className + "\n";
        returnString += "Method: " + _methodName;

        return returnString;
    }
}
