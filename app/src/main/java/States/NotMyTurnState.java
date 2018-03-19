package States;

import com.example.cs340.tickettoride.Views.IGamePlayView;

/**
 * Created by matto on 3/14/2018.
 */

public class NotMyTurnState extends IState {
    @Override
    public void enableDisableButtons(IGamePlayView view) {
        view.disableTrainCardButton();
        view.disableDrawRouteButton();
        view.disableClaimRouteButton();
    }
}
