package Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ClientModel.ClientModel;
import Presenters.IPresenter;
import common.Deck;
import common.DestCard;
import common.ICard;
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

    public void claimRoute(IPresenter presenter, String routeId, List<TrainCard> cardsUsed) {
        GenericAsyncTask task = new GenericAsyncTask(presenter, "claimRoute", new String[]{"java.lang.String", "java.lang.String", "java.util.List"});
        task.execute(getPlayerId(), routeId, cardsUsed);
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
        List<Route> routes = ClientModel.getInstance().getGameRoutes();
        List<Route> claimable = new ArrayList<>();

        for (Route route: routes) {
            // Claimable = not already claimed + have enough of the right cards to claim it.
            if (route.getOwnedByPlayerID() == null && cardsSufficient(route, ClientModel.getInstance().getUser().getTrainCards())) {
                claimable.add(route);
            }
        }

        return claimable;
    }

    private boolean cardsSufficient(Route route, Deck deck) {
        TrainCard.Colors routeColor = route.getPathColor();
        int routeLength = route.getRouteLength();

        List<TrainCard> cards = (List<TrainCard>) deck.toList(TrainCard.class);
        Map<TrainCard.Colors, Integer> cardsByColor = getCardsByColor(cards);

        if (haveEnoughCardsOfColor(routeLength, routeColor, cardsByColor)) { // Have enough cards of the right type
            return true;
        }

        // Have enough of any color
        if (routeColor == TrainCard.Colors.wildcard) {
            for (TrainCard.Colors color : TrainCard.Colors.values()) {
                if (haveEnoughCardsOfColor(routeLength, color, cardsByColor)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean haveEnoughCardsOfColor(int routeLength, TrainCard.Colors color,  Map<TrainCard.Colors, Integer> cards) {
        if (color == TrainCard.Colors.wildcard) {
            return cards.get(color) >= routeLength;
        }

        return cards.get(color) + cards.get(TrainCard.Colors.wildcard) >= routeLength;
    }

    private Map<TrainCard.Colors, Integer> getCardsByColor(List<TrainCard> cards) {
        Map<TrainCard.Colors, Integer> results = new HashMap<>();

        for (TrainCard.Colors color : TrainCard.Colors.values()) {
            results.put(color, 0);
        }

        for (TrainCard card : cards) {
            results.put(card.getColor(), results.get(card.getColor()) + 1);
        }

        return results;
    }

    public Map<ICard,Integer> getAvailableCardsAndCount()
    {
        Deck tCards = ClientModel.getInstance().getUser().getTrainCards();
        Map<ICard,Integer> availableCards = new HashMap<>();
        for (int i = 0; i < tCards.size(); i++)
        {
            ICard card = tCards.viewCard(i);
            Integer count = 1;
            if (availableCards.containsKey(card))
            {
                count = availableCards.get(card)+1;
            }
            availableCards.put(card, count);
        }
        return availableCards;
    }

    public boolean isValidTrade(Route selectedRoute, Map<ICard, Integer> selectedCards)
    {
        if (selectedCards.size() == selectedRoute.getRouteLength())
        {
            if (selectedRoute.getPathColor().equals(TrainCard.Colors.wildcard))
            {
                return true;
            }
            Set<ICard> cards = selectedCards.keySet();
            for (ICard card : cards)
            {
                if (card.getClass() == TrainCard.class)
                {
                    TrainCard tCard = (TrainCard) card;
                    if (tCard.getColor() != selectedRoute.getPathColor() && tCard.getColor() != TrainCard.Colors.wildcard)
                    {
                        return false;
                    }
                }
                else
                {
                    return false;
                }
            }
            return true;//if all the cards match colors or are wildcards, and there is the right #, then it is a valid trade
        }
        return false;
    }
}
