package Presenters;

import com.example.cs340.tickettoride.Views.IClaimRouteView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import ClientModel.ClientModel;
import Services.GamePlayService;
import States.IState;
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
    IState state;

    public ClaimRoutePresenter(IClaimRouteView claimRouteView)
    {
        this.claimRouteView = claimRouteView;
        this.claimRouteView.offerRoutes(getAvailableRoutes());
        this.claimRouteView.setAvailableCards(getAvailableCards());
        ClientModel.getInstance().addObserver(this);
        update(null, null);
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
        state.claimedRoute(this, route, usedCards);
    }

    @Override
    public void onClickClaimRoute()
    {
        update(null, null);
        claimRouteView.dialogCreateAndShow();
    }

    /**
     * Just converts a map of cards to a list of train cards
     * @param cards a map of cards
     * @return a list of train cards
     */
    private List<TrainCard> toCardList(Map<ICard, Integer> cards)
    {
        List<TrainCard> cardList = new ArrayList<>();
        for (ICard card : cards.keySet())
        {
            Integer numCards = cards.get(card);
            if (card != null && numCards != null && card.getClass() == TrainCard.class)
            {
                for (int cnt = 0; cnt < numCards; cnt++)
                {
                    cardList.add((TrainCard) card);
                }
            }
        }
        return cardList;
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

        state = ClientModel.getInstance().getState();
    }
}
