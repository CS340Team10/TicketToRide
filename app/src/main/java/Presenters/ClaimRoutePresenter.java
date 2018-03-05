package Presenters;

import android.support.v4.util.Pair;

import com.example.cs340.tickettoride.Views.IClaimRouteView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
        ClientModel.getInstance().addObserver(this);
        this.claimRouteView.offerRoutes(getAvailableRoutes());
        this.claimRouteView.setAvailableCards(getAvailableCards());
    }

    private List<Pair<ICard,Integer>> getAvailableCards() {
        return new ArrayList<>();
    }

    private List<Route> getAvailableRoutes() {
        return new ArrayList<>();
    }

    @Override
    public void choseRoute(Route route, List<Pair<ICard, Integer>> usedCards)
    {
        GamePlayService.getInstance().claimRoute(this, route.getRouteID());
        ClientModel.getInstance().removeTrainCards(getDiscardList(usedCards));
    }

    /**
     * Just converts a list of usedCards
     * @param usedCards
     * @return
     */
    private List<TrainCard> getDiscardList(List<Pair<ICard, Integer>> usedCards)
    {
        List<TrainCard> discardPile = new ArrayList<>();
        for (int i = 0; i < usedCards.size(); i++)
        {
            ICard card = usedCards.get(i).first;
            Integer numCards = usedCards.get(i).second;
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
        if (result.succeeded())
        {
            msg = "Route claimed successfully!";
        }
        if (claimRouteView != null)
        {
            claimRouteView.dismissDialog();
            claimRouteView.showToast(msg);
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        //check to see if routes were offered, if so, show dialog
    }
}
