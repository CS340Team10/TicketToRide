package common;

import java.util.List;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public interface IServer {

    public Results register(String username, String password);

    public Results login(String username, String password);

    public Results createGame(String gameName, Integer numPlayers);

    public Results joinGame(String gameName, String playerID);

    public Results claimRoute(String playerId, String routeId);

    public Results turnEnded(String playerId);

    public Results requestDestCards(String playerId);

    public Results keepDestCards(String playerId, List<DestCard> keep);

    public Results selectTrainCard(String playerId, TrainCard card, Boolean cardValid);

    public Results chat(String playerId, String message);
}
