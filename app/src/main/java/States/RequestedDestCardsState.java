package States;

import java.util.ArrayList;
import java.util.List;

import ClientModel.ClientModel;
import Presenters.IPresenter;
import Services.GamePlayService;
import common.DestCard;

/**
 * Created by matto on 3/14/2018.
 */

public class RequestedDestCardsState extends IState {

    @Override
    public void choseDestCards(IPresenter presenter, List<DestCard> cards)
    {
        ClientModel.getInstance().setOfferedDestCards(new ArrayList<DestCard>()); // Must do this here, so that pick dest card modal is not re-presented.
        GamePlayService.getInstance().keepDestCards(presenter, cards); //This method will request these Dest Cards from the server
        GamePlayService.getInstance().turnEnded(presenter);
        ClientModel.getInstance().setState(new NotMyTurnState());
    }
}
