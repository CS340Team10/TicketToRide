package Services;

import common.Command;
import common.Deck;
import common.ICommand;

/**
 * Created by Brian on 2/8/18.
 */

public class ServerCommandFactory {
    public static ICommand createRestoreTrainDeckCommand(String gameName, Deck faceup, Deck facedown) {
        Command command = new Command("Services.ServerCommandService", "restoreTrainDeck", new String[]{"java.lang.String", "common.Deck", "common.Deck"}, new Object[]{gameName, faceup, facedown} );
        return command;
    }
}
