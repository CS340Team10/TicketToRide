package Presenters;

import com.example.cs340.tickettoride.Views.IPickDestCardView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import ClientModel.ClientModel;
import Services.GamePlayService;
import common.Deck;
import common.DestCard;
import common.Results;

import static Testing.TestService.IS_TESTING;

/**
 * Created by Joseph on 3/5/2018.
 *
 * This presenter manages an IPickDestCardView
 */

public class PickDestCardPresenter implements IPresenter, IPickDestCardPresenter, Observer
{
    private IPickDestCardView mView;
    private final int MIN_DEST_CARD_REQ = 2;//The minimum number of dest cards a user must have
    private Deck keepers;//The destCards that the user is attempting to keep
    public PickDestCardPresenter(IPickDestCardView view)
    {
        this.mView = view;
        update(null, null);//Check to see if any cards have been offered before this presenter was created
    }

    @Override
    public void onPickDestCards(Deck cards)
    {
        keepers = cards;
        if (IS_TESTING)
        {
            onPostExecute(new Results(true, "", "")); //Use this for testing only
        }
        else
        {
            GamePlayService.getInstance().keepDestCards(this, (List<DestCard>) cards.toList(DestCard.class)); //This method will request these Dest Cards from the server
        }
    }

    private static boolean firstTime = true; //ONLY USED FOR TESTING THIS METHOD
    @Override
    public void update(Observable o, Object arg)
    {
        Deck offeredCards;
        if (IS_TESTING && firstTime)
        {
            firstTime = false;
            offeredCards = new Deck();
            offeredCards.addCard(new DestCard("Albuquerque1", "Santa Fe1", 10));
            offeredCards.addCard(new DestCard("Albuquerque2", "Santa Fe2", 20));
            offeredCards.addCard(new DestCard("Albuquerque3", "Santa Fe3", 30));
            mView.offerDestCards(offeredCards, 2);
            mView.dialogCreateAndShow();
        }
        else
        {
            offeredCards = ClientModel.getInstance().getUser().getOfferedDestCards();
            Deck cardsInHand = ClientModel.getInstance().getUser().getDestCards();
            int minSelect = MIN_DEST_CARD_REQ - cardsInHand.size();
            if (minSelect < 0)
            {
                minSelect = 0;
            }
            if (offeredCards != null && offeredCards.size() > 0)
            {
                mView.offerDestCards(offeredCards, minSelect);
                mView.dialogCreateAndShow();
            }
        }
    }

    @Override
    public void onPostExecute(Results result)
    {
        mView.dismissDialog();//Dismiss the dialog
        //Show a Toast on whether it worked or not
        String msg = "Cards could not be selected!";//Assume it didn't work
        if (result.succeeded())
        {
            msg = "Cards were successfully selected!";//If it worked, say so
            ClientModel.getInstance().addDestCards(keepers);
        }
        mView.showToast(msg);
    }
}
