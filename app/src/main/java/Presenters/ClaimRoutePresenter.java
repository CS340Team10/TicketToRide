package Presenters;

import android.support.v4.util.Pair;

import com.example.cs340.tickettoride.Views.IClaimRouteView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import ClientModel.ClientModel;
import Services.GamePlayService;
import common.ICard;
import common.Results;
import common.Route;
import common.TrainCard;

/**
 * Created by Joseph on 3/5/2018.
 */

public class ClaimRoutePresenter implements IClaimRoutePresenter, IPresenter, Observer
{
    IClaimRouteView claimRouteView;

    public ClaimRoutePresenter(IClaimRouteView claimRouteView)
    {
        this.claimRouteView = claimRouteView;
        this.claimRouteView.offerRoutes(getAvailableRoutes());
        this.claimRouteView.setAvailableCards(getAvailableCards());
        ClientModel.getInstance().addObserver(this);
    }

    private Map<ICard,Integer> getAvailableCards()
    {
        return GamePlayService.getInstance().getAvailableCardsAndCount();
    }

    private List<Route> getAvailableRoutes()
    {
        return GamePlayService.getInstance().getClaimableRoutes();
    }

    @Override
    public void choseRoute(Route route, Map<ICard, Integer> usedCards)
    {
        List<TrainCard> cardsUsed = new ArrayList<>();
        for(Pair<ICard, Integer> pair : usedCards) {
            cardsUsed.add((TrainCard)pair.first);
        }
        GamePlayService.getInstance().claimRoute(this, route.getRouteID(), cardsUsed);
        ClientModel.getInstance().removeTrainCards(getDiscardList(usedCards));
    }

    @Override
    public void onClickClaimRoute()
    {
        update(null, null);
        claimRouteView.dialogCreateAndShow();
    }

    /**
     * Just converts a list of usedCards
     * @param usedCards
     * @return
     */
    private List<TrainCard> getDiscardList(Map<ICard, Integer> usedCards)
    {
        List<TrainCard> discardPile = new ArrayList<>();
        for (ICard card : usedCards.keySet())
        {
            Integer numCards = usedCards.get(card);
            if (card != null && numCards != null)
            {
                for (int cnt = 0; cnt < numCards; cnt++)
                {
                    discardPile.add(new TrainCard(((TrainCard) card).getColor()));
                }
            }
        }
        return discardPile;
    }

    @Override
    public void onPostExecute(Results result) {
        String msg = "ERROR: Failed to claim route!";
        if (result != null)
        {
            if (result.succeeded())
            {
                msg = "Route claimed successfully!";
            }
            else
            {
                msg = result.getError();
            }
        }
        if (claimRouteView != null)
        {
            claimRouteView.dismissDialog();
            claimRouteView.showToast(msg);
        }
    }

    @Override
    public void onChangeSelection(Route selectedRoute, Map<ICard, Integer> selectedCards)
    {
        boolean valid = GamePlayService.getInstance().isValidTrade(selectedRoute, selectedCards);
        //Check if the combination is valid
        //If it is, unlock the submit button, but lock all the number pickers EXCEPT for the ones
        //that were used in the trade. Allow them to decrease those particular number pickers.
        if (valid)
        {
            claimRouteView.enableSubmitButton();
            claimRouteView.disableCardNumberPickers();
            claimRouteView.enableCardNumberPickers(selectedCards.keySet());
        }
        else
        {
            //If the combination is not valid/enough, then lock the submit button, but unlock all the
            //number pickers.
            claimRouteView.disableSubmitButton();
            claimRouteView.enableCardNumberPickers();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        //When cards are added or taken from the hand, update view to know
        claimRouteView.offerRoutes(getAvailableRoutes());
        //When available routes are added or taken, update view to know
        claimRouteView.setAvailableCards(getAvailableCards());
    }
}
