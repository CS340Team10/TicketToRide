package Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // 1. if numPlayers <= 3, double routes disappear if one is already chosen.
        List<Route> rule1 = new ArrayList<>();
        for(Route r : routes) {
            if (ClientModel.getInstance().getGame().getPlayers().size() <= 3) {
                if (isDoubleRoute(r)) {
                    Route dual = getDual(r);
                    if (dual.getOwnedByPlayerID() == null && r.getOwnedByPlayerID() == null) {
                        // Both unclaimed, so send them.
                        rule1.add(r);
                    }
                } else {
                    rule1.add(r);
                }
            } else {
                rule1.add(r);
            }
        }

        List<Route> claimable = new ArrayList<>();
        for (Route route: rule1) {
            // Claimable = not already claimed + have enough of the right cards to claim it.
            if (route.getOwnedByPlayerID() == null && cardsSufficient(route, ClientModel.getInstance().getUser().getTrainCards())) {
                claimable.add(route);
            }
        }

        // 2. Player can claim only 1 of double routes

        List<Route> rule2 = new ArrayList<>();
        for (Route r : claimable) {
            if(isDoubleRoute(r)) {
                Route dup = getDual(r);
                if(dup.getOwnedByPlayerID() == null || !dup.getOwnedByPlayerID().equals(ClientModel.getInstance().getUser().getId())) {
                    rule2.add(r);
                }
            } else {
                rule2.add(r);
            }
        }

        // Rule 3 : Can't claim a route longer than number of train cards left
        List<Route> rule3 = new ArrayList<>();
        for (Route r : rule2) {
            if (ClientModel.getInstance().getUser().getTrainsLeft() >= r.getRouteLength()) {
                rule3.add(r);
            }
        }

        return rule3;
    }

    private boolean isDoubleRoute(Route r) {
        return r.getRouteID().endsWith("1") || r.getRouteID().endsWith("2");
    }

    // Return the dual of the double route passed in.
    private Route getDual(Route r) {
        assert isDoubleRoute(r);
        String routeId = r.getRouteID();

        if(routeId.endsWith("1")) {
            Route dup = ClientModel.getInstance().getRouteById(routeId.substring(0, routeId.length() - 1) + "2");
            return dup;
        } else if(r.getRouteID().endsWith("2")) {
            Route dup = ClientModel.getInstance().getRouteById(routeId.substring(0, routeId.length() - 1) + "1");
            return dup;
        }

        return null;
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

    /**
     * Tells you whether the given cards can be used to claim the given route
     * @param route the route you want to check
     * @param cards the cards you want to check against the route
     * @return whether the given cards can be used to claim the given route
     */
    public boolean isValidTrade(Route route, Map<ICard, Integer> cards)
    {
        return !(route == null || cards == null)
                && isMatchingColor(route, cards)
                && isMatchingNumber(route, cards);
    }

    /**
     * Tells you whether the given cards match the given route. Takes wildcards and wild routes into
     * account, so that if the route is a wild, cards of any color will match.
     * And wildcards can be applied to any route, even in combination with other valid colors.
     * @param route the route you want to check against
     * @param cards the cards you want to see if they can be used to claim the route
     * @return
     */
    private boolean isMatchingColor(Route route, Map<ICard, Integer> cards)
    {
        if (route.getPathColor().equals(TrainCard.Colors.wildcard))
        {
            return true;
        }
        for (ICard card : cards.keySet())
        {
            if (card.getClass() != TrainCard.class)
            {
                return false;
            }
            TrainCard tCard = (TrainCard) card;
            if (!tCard.getColor().equals(route.getPathColor())
                    && (!tCard.getColor().equals(TrainCard.Colors.wildcard)))
            {
                return false;
            }
        }
        return true;
    }

    public boolean isMatchingNumber(Route route, Map<ICard, Integer> cards)
    {
        int numCards = 0;
        for (ICard card : cards.keySet())
        {
            numCards += cards.get(card);
        }
        return numCards == route.getRouteLength();
    }
}
