package Services;

import java.util.ArrayList;
import java.util.List;

import ClientModel.ClientModel;
import Presenters.IPresenter;
import common.DestCard;
import common.Route;
import common.TrainCard;

/**
 * Created by ephraimkunz on 2/21/18.
 */

public class GamePlayService {
    private static GamePlayService _instance = new GamePlayService();
    private final String tag = "GamePlayService";

    private GamePlayService(){}

    public static GamePlayService getInstance() {
        return _instance;
    }

    private String getPlayerId() {
        return ClientModel.getInstance().getUser().getId();
    }

    public void claimRoute(IPresenter presenter, String routeId) {
        GenericAsyncTask task = new GenericAsyncTask(presenter, "claimRoute", new String[]{"java.lang.String", "java.lang.String"});
        task.execute(getPlayerId(), routeId);
    }

    public void turnEnded(IPresenter presenter) {
        GenericAsyncTask task = new GenericAsyncTask(presenter, "turnEnded", new String[]{"java.lang.String"});
        task.execute(getPlayerId());
    }

    public void requestDestCards(IPresenter presenter) {
        GenericAsyncTask task = new GenericAsyncTask(presenter, "requestDestCards", new String[]{"java.lang.String"});
        task.execute(getPlayerId());
    }

    public void keepDestCards(IPresenter presenter, List<DestCard> keep) {
        GenericAsyncTask task = new GenericAsyncTask(presenter, "keepDestCards", new String[]{"java.lang.String", "java.util.List"});
        task.execute(getPlayerId(), keep);
    }

    // Train card is null if the player desires to select from the facedown deck.
    public void selectTrainCard(IPresenter presenter, TrainCard card) {
        boolean cardValid = card != null;
        if (!cardValid) {
            card = new TrainCard(TrainCard.Colors.wildcard); // Fill it with new empty card
        }

        GenericAsyncTask task = new GenericAsyncTask(presenter, "selectTrainCard", new String[]{"java.lang.String", "common.TrainCard", "java.lang.Boolean"});
        task.execute(getPlayerId(), card, cardValid);
    }

    public void sendChat(IPresenter presenter, String message) {
        GenericAsyncTask task = new GenericAsyncTask(presenter, "chat", new String[]{"java.lang.String", "java.lang.String"});
        task.execute(getPlayerId(), message);
    }

    public List<Route> getClaimableRoutes() {
        // TODO: Actually implement this.
        return new ArrayList<Route>();
    }
}
