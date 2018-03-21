package Presenters;

import com.example.cs340.tickettoride.Views.IPickDestCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ClientModel.ClientModel;
import States.IState;
import common.Deck;
import common.DestCard;
import common.Results;

/**
 * Created by Joseph on 3/5/2018.
 *
 * This presenter manages an IPickDestCardView
 */

public class PickDestCardPresenter implements IPresenter, IPickDestCardPresenter, Observer
{
    private IPickDestCardView mView;
    private IState state;
    private int minSelect = 2;

    public PickDestCardPresenter(IPickDestCardView view)
    {
        this.mView = view;
        ClientModel.getInstance().addObserver(this);
        update(null, null);//Check to see if any cards have been offered before this presenter was created
    }

    @Override
    public void onPickDestCards(Deck cards)
    {
        state.choseDestCards(this, (List<DestCard>) cards.toList(DestCard.class));
    }

    @Override
    public void requestDestCards()
    {
        state.requestedDestCards(this);
        minSelect = 1; // After the first pick to setup the game.
    }

    @Override
    public void update(Observable o, Object arg)
    {
        state = ClientModel.getInstance().getState();

        Deck offeredCards = ClientModel.getInstance().getUser().getOfferedDestCards();

        if (offeredCards != null && offeredCards.size() > 0)
        {
            mView.offerDestCards(offeredCards, minSelect);
            mView.dialogCreateAndShow();
        }

    }

    @Override
    public void onPostExecute(Results result)
    {
        if (result.succeeded()) {
            ClientModel.getInstance().setOfferedDestCards(new ArrayList<DestCard>());
        }
    }
}
