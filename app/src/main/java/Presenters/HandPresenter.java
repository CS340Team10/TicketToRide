package Presenters;

import android.util.Log;

import com.example.cs340.tickettoride.Views.IHandView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ClientModel.ClientModel;
import common.Deck;
import common.DestCard;
import common.TrainCard;

/**
 * Created by Joseph on 3/2/2018.
 */

public class HandPresenter implements IHandPresenter, Observer
{
    private final String TAG = "HandPresenter";
    private IHandView handView;

    public HandPresenter(IHandView handView)
    {
        this.handView = handView;
        ClientModel.getInstance().addObserver(this);
        update(null, null);
    }

    @Override
    public void onShowHand()
    {
        //Do nothing for now
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if (handView != null) {
            ClientModel model = ClientModel.getInstance();
            Deck destHand = model.getUser().getDestCards();
            List<DestCard> dCards = (List<DestCard>) destHand.toList(DestCard.class);
            handView.updateDestHand(dCards);
            Deck trainHand = model.getUser().getTrainCards();
            List<TrainCard> tCards = (List<TrainCard>) trainHand.toList(TrainCard.class);
            handView.updateTrainHand(tCards);
        }
        else
        {
            Log.e(TAG, "ERROR: cannot update! The HandPresenter has a NULL HandView");
        }
    }
}
