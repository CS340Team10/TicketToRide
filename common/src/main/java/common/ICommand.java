package common;

/**
 * Created by Joseph on 2/2/2018.
 */

public interface ICommand {
    Results execute();
    String playerId();
    // THIS IS A HACK!!!!!. We need to get the method name so we know if it is a joinGame command.
    // In this case, we need to get the game name here, because we can't fetch it from the model
    // because the player is not yet a part of the game.
    String getMethodName();
    String getGameName();
}
