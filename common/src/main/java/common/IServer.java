package common;

import java.util.List;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public interface IServer {

    public Results register(String username, String password);

    public Results login(String username, String password);

    public Results createGame(String gameName, Integer numPlayers);

    public Results joinGame(String playerID, String gameName);

    public Results claimRoute(String playerId, String routeId, List<TrainCard> cardsUsed);

    public Results turnEnded(String playerId);

    public Results requestDestCards(String playerId);

    public Results keepDestCards(String playerId, List<DestCard> keep);

    public Results selectTrainCard(String playerId, TrainCard card, Boolean pickFromFaceUp);

    public Results chat(String playerId, String message);
}