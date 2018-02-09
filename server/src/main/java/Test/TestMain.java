package Test;

import Server.Serializer;
import Services.ServerCommandService;
import common.Command;
import common.Results;

/**
 * Created by Brian on 2/6/18.
 */

public class TestMain {

    public static void main(String[] args){

        Command testCommand = new Command("ServerCommandService", "login", new String[] {"String", "String"}, new String[] {"player1", "password"});
        String json = Serializer.serializeObject(testCommand);

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

        System.out.println(ServerCommandService.getModelString());
    }
}
