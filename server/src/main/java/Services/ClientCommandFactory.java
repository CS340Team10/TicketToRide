package Services;

import common.Command;
import common.ICommand;

/**
 * Created by Brian on 2/8/18.
 */

public class ClientCommandFactory {

    /**
     * Returns a command to inform the clients that the game is starting
     *
     * @return a command that informs the clients that the game is starting
     */
    public static ICommand createStartGameCommand(){
        Command returnCommand = new Command("", "", new String[]{}, new String[]{});

        return returnCommand;
    }
}
