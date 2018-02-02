package common;

/**
 * Created by David on 2/2/2018.
 */

public abstract class Command implements ICommand
{
    protected String className;

    @Override
    public abstract Results execute();
}
