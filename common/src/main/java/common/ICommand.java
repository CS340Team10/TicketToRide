package common;

/**
 * Created by Joseph on 2/2/2018.
 */

public interface ICommand {
    Results execute();
    String playerId();
}
