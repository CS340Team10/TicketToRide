package common;

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

    @Override
    public Results execute()
    {
        Results result = new Results(false, "", "ERROR: could not execute command!");//By default, assume it doesn't work; "Non-functioning until proven otherwise"
        try
        {
            System.out.println("Attempting to execute command. Getting class...");
            Class<?> receiverClass = Class.forName(_className);             //Get the class by using the name
            System.out.println("Class "+_className+" was found! Attempting to make parameter list...");
            Class<?>[] _paramTypes = new Class<?>[_paramTypeNames.length];  //Initialize an array of types
            for (int i = 0; i < _paramTypeNames.length; i++)                //Loop through the array of type names
            {
                _paramTypes[i] = Class.forName(_paramTypeNames[i]);         //Get each type
            }
            Method method = receiverClass.getMethod(_methodName, _paramTypes);//Get the indicated method
            Object o = method.invoke(null, _paramValues);                  //Call the indicated method
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
        }
        return result;
    }
}
