package Communication;

import common.ICommand;
import common.Results;

/**
 * Created by matto on 2/1/2018.
 */

public class ServerCommand implements ICommand {

    private String _className;
    private String _methodName;
    private Object[] _paramTypes;
    private Object[] _paramValues;

    public ServerCommand(String className, String methodName, Object[] paramTypes, Object[] paramValues)
    {
        this._className = className;
        this._methodName = methodName;
        this._paramTypes = paramTypes;
        this._paramValues = paramValues;
    }

    public Results execute()
    {
        return null;
    }
}
