package Handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import Services.ClientCommandFactory;
import Services.ServerCommandService;
import common.DestCard;
import common.ICommand;
import common.PlayerAttributes;
import common.Serializer;
import common.TrainCard;

/**
 * Created by Brian on 2/1/18.
 */

public class PollHandler extends GenericHandler {

    /**
     * Handles the Event request
     *
     * @param exchange the HttpExchange Object to use in the request
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException{

        ICommand[] results = new ICommand[]{};

        // get the player requesting the commands
        String playerID = Serializer.getInstance().readInputStreamAsString(exchange.getRequestBody());

        // validate the player
        if (ServerCommandService.getInstance().playerIsValid(playerID)){
            results = ServerCommandService.getInstance().getPlayerCommands(playerID);
        }

        // TODO: Remove - just for testing GameNotificationServices and associates.
        ArrayList<DestCard> destCards = new ArrayList<>();
        destCards.add(new DestCard("start", "end", 5));

        ArrayList<TrainCard> trainCards = new ArrayList<>();
        trainCards.add(new TrainCard(TrainCard.Colors.black));

        results = new ICommand[]{
                ClientCommandFactory.createChatCommand(playerID, "This is a message"),
                ClientCommandFactory.createDestCardDeckUpdatedCommand(15),
                ClientCommandFactory.createDestCardsChosenCommand(playerID, destCards),
                ClientCommandFactory.createOfferDestCardsCommand(playerID, destCards),
                ClientCommandFactory.createPlayerUpdatedCommand(new PlayerAttributes()),
                ClientCommandFactory.createRouteClaimedCommand(playerID, "new route"),
                ClientCommandFactory.createTrainCardChosenCommand(playerID, new TrainCard(TrainCard.Colors.black)),
                ClientCommandFactory.createTrainCardDeckUpdatedCommand(trainCards, 5),
                ClientCommandFactory.createTurnBeganCommand(playerID),
                ClientCommandFactory.createStartGameCommand()
        };

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        OutputStream os = exchange.getResponseBody();
        os.write(Serializer.getInstance().serializeObject(results).getBytes());
        os.close();
    }
}
