package States;

import java.util.ArrayList;
import java.util.List;

import ClientModel.ClientModel;
import Presenters.IPresenter;
import Services.GamePlayService;
import common.DestCard;

/**
 * Created by ephraimkunz on 3/19/18.
 */

public class StartGameState extends IState {
    @Override
    public void choseDestCards(IPresenter presenter, List<DestCard> cards)
    {
        ClientModel.getInstance().setOfferedDestCards(new ArrayList<DestCard>()); // Must do this here, so that pick dest card modal is not re-presented.
        GamePlayService.getInstance().keepDestCards(presenter, cards); //This method will request these Dest Cards from the server
        if(ClientModel.getInstance().getUser().isMyTurn()) {
            ClientModel.getInstance().setState(new MyTurnState());
        } else {
            ClientModel.getInstance().setState(new NotMyTurnState());
        }
    }
}
