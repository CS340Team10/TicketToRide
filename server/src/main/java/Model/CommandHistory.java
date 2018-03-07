package Model;

import java.util.ArrayList;
import java.util.List;

import Services.ClientCommandFactory;
import common.ICommand;

/**
 * Created by Joseph on 2/2/2018.
 *
 * Keeps a record of commands in sequential order, or the order they need to execute
 */

public class CommandHistory
{
    private List<ICommand> history = new ArrayList<>();

    public CommandHistory(){

    }

    /**
     * Add a command to the command history
     * @param cmd the command you want to register in the history
     */
    public void addCommand(ICommand cmd)
    {
        history.add(cmd);
    }

    /**
     * Creates a Start Game command to the queue
     */
    public void addStartGameCommand(){
        history.add(ClientCommandFactory.createStartGameCommand());
    }

    /**
     * Get all the commands starting at and after a certain given position
     * @param position starting position (inclusive) you want to get the command history from
     * @return a sublist containing all commands starting at and after the given index
     */
    public List<ICommand> historyFrom(int position)
    {

        return history.subList(position, history.size() );
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == getClass())
        {
            CommandHistory h = (CommandHistory) o;
            return history.equals(h.history);
        }
        return false;
    }
}
