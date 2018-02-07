package Test;

import com.google.gson.Gson;

import Services.ServerCommandService;
import common.Command;
import common.Results;
import common.Serializer;

/**
 * Created by Brian on 2/6/18.
 */

public class TestMain {

    public static void main(String[] args){

        Gson bob = new Gson();

        /*Command testCommand = new Command("ServerCommandService", "login", new String[] {"String", "String"}, new String[] {"player1", "password"});
        String json = Serializer.serializeObject(testCommand);

        System.out.println(json);*/

        Results result = ServerCommandService.getInstance().login("player4", "password2");

        System.out.println(result);
    }
}
