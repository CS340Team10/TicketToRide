package States;

import com.example.cs340.tickettoride.Views.IGamePlayView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ClientModel.ClientModel;
import Presenters.IPresenter;
import Services.GamePlayService;
import common.ICard;
import common.Route;
import common.TrainCard;

/**
 * Created by matto on 3/14/2018.
 */

public class MyTurnState extends IState {
    @Override
    public void enableDisableButtons(IGamePlayView view) {
        view.enableClaimRouteButton();
        if(ClientModel.getInstance().getGame().getDestCardDeckNum() > 0) { // There are still dest cards to draw
            view.enableDrawRouteButton();
        }

        if(ClientModel.getInstance().getGame().getFaceupTrainCards().size() + ClientModel.getInstance().getGame().getTrainCardDeckNum() > 0) {
            view.enableTrainCardButton();
        }
    }

    @Override
    public void choseTrainCard(IPresenter presenter, TrainCard card){
        GamePlayService.getInstance().selectTrainCard(presenter, card);
        if(
                (card != null && card.getColor().equals(TrainCard.Colors.wildcard)) ||
                (noMoreFaceupToDraw()))
        {
            GamePlayService.getInstance().turnEnded(presenter);
            ClientModel.getInstance().setState(new NotMyTurnState());
        }
        else {
            ClientModel.getInstance().setState(new PickedFirstTrainState());
        }
    }

    private boolean noMoreFaceupToDraw() {
        return (ClientModel.getInstance().getGame().getFaceupTrainCards().size() == 1);

        // TODO: If the last cards are wild and red and I draw red, make sure that the turn ends and I don't get stuck.
    }

    @Override
    public void requestedDestCards(IPresenter presenter) {
        GamePlayService.getInstance().requestDestCards(presenter);
        ClientModel.getInstance().setState(new RequestedDestCardsState());
    }

    @Override
    public void claimedRoute(IPresenter presenter, Route route, Map<ICard, Integer> usedCards) {
        GamePlayService.getInstance().claimRoute(presenter, route.getRouteID(), toCardList(usedCards));
        GamePlayService.getInstance().turnEnded(presenter);
        ClientModel.getInstance().setState(new NotMyTurnState());
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
}
