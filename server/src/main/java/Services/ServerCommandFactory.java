package Services;

import java.util.List;

import Model.Game;
import common.Command;
import common.Deck;
import common.ICommand;
import static sun.audio.AudioPlayer.player;

/**
 * Created by Brian on 2/8/18.
 */

public class ServerCommandFactory {


    public static ICommand createDeckShuffledCommand(String gameName, Game.DeckShuffleEvent deckEvent, Deck currDeck) {
        Command command = new Command("Services.ServerCommandService", "restoreDeck", new String[]{"java.lang.String", "common.Game.DeckShuffledEvent", "common.Deck"}, new Object[]{gameName, deckEvent, currDeck} );
        return command;
    }
}
