package Services;

import Model.Game;
import common.Command;
import common.Deck;
import common.ICommand;

/**
 * Created by Brian on 2/8/18.
 */

public class ServerCommandFactory {


    public static ICommand createDeckShuffledCommand(String gameName, Game.DeckShufflType deckType, Deck currDeck) {
        Command command = new Command("Services.ServerCommandService", "restoreDeck", new String[]{"java.lang.String", "Model.Game.DeckShuffledType", "common.Deck"}, new Object[]{gameName, deckType, currDeck} );
        return command;
    }
}
