package Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Model.Game;
import Model.Player;
import Services.ServerCommandService;
import common.Command;
import common.DestCard;
import common.ICard;
import common.Results;
import common.Serializer;

/**
 * Created by Brian on 2/6/18.
 */

public class TestMain {

    public static void main(String[] args){

        /*Game newGame = new Game("test", 2);

        Command testCommand = new Command("ServerCommandService", "login", new String[] {"String", "String"}, new String[] {"player1", "password"});
        String json = Serializer.getInstance().serializeObject(testCommand);

        System.out.println(json);

        Results result = ServerCommandService.getInstance().login("player1", "password");
        result = ServerCommandService.getInstance().register("player1", "password");
        result = ServerCommandService.getInstance().register("player4", "password");
        result = ServerCommandService.getInstance().register("player5", "password");

        result = ServerCommandService.getInstance().createGame("My First Game", 5);
        result = ServerCommandService.getInstance().createGame("My Second Game", 4);
        result = ServerCommandService.getInstance().createGame("My Third Game", 3);
        result = ServerCommandService.getInstance().createGame("My Fourth Game", 2);
        result = ServerCommandService.getInstance().createGame("My Fifth Game", 1);

        result = ServerCommandService.getInstance().joinGame("My First Game", "player5_registered");

        System.out.println(ServerCommandService.getModelString());*/

        Player tempPlayer = new Player("user1", "password");

        ArrayList<ICard> destinationCards = new ArrayList<ICard>();
        destinationCards.add(new DestCard("test1", "test2", 3));
        destinationCards.add(new DestCard("test3", "test4", 4));
        destinationCards.add(new DestCard("test5", "test6", 10));
        tempPlayer.offerDestinationCards(destinationCards);

        destinationCards.remove(0);
        tempPlayer.acceptDestinationCards(destinationCards);

        System.out.println(tempPlayer);
    }
}
