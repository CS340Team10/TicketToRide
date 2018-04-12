package Presenters;

import com.example.cs340.tickettoride.Views.IPlayerView;

import java.util.Observable;
import java.util.Observer;

import ClientModel.ClientModel;
import ClientModel.Player;
import common.Results;

/**
 * Created by matto on 2/28/2018.
 *
 */

public class PlayerPresenter implements IPlayerPresenter, IPresenter, Observer {
    IPlayerView playerView;

    /**
     * creates a new presenter with an associated player view
     * adds observer to ClientModel
     *
     * @pre none
     *
     * @param playerView which player view this presenter will correspond to
     *
     * @return a new PlayerPresenter linked to the given player view
     */
    public PlayerPresenter(IPlayerView playerView)
    {
        this.playerView = playerView;
        ClientModel.getInstance().addObserver(this);

        update(null, null);
    }

    /**
     * informs the view to update
     * @pre playerView != null
     *
     * @post playerView's model will be up to date
     */
    @Override
    public void update() {
        playerView.update();
    }

    /**
     * @pre none
     *
     * @post this presenter is uncanged
     *
     * @return instance of IPlayerView
     */
    public IPlayerView getPlayerView() {
        return playerView;
    }

    /**
     * sets the IPlayerView in this presenter to the given IPlayerView instance
     *
     * @pre none
     *
     * @post playerView in this presenter is now equal to the given playerView
     *
     * @param playerView instance of IPlayerView that will replace playerView
     */
    public void setPlayerView(IPlayerView playerView) {
        this.playerView = playerView;
    }


    /**
     * this is never implemented or used. It is only implemented to follow the IPresenter interface
     * @pre none
     * @post none
     * @param result not used
     */
    @Override
    public void onPostExecute(Results result) {
        // do nothing
    }


    /**
     * Gets information from ClientModel, updates playerView info, tells playerView to update display
     *
     * @pre playerView != null
     *
     * @post playerView model and display updated
     *
     * @param observable not used, but required to use Observer class
     * @param o not used, but required to use Observer class
     */
    @Override
    public void update(Observable observable, Object o) {
        ClientModel clientModel = ClientModel.getInstance();
        for (int i = 0; i < clientModel.getGame().getPlayers().size(); i++) {
            Player p = clientModel.getGame().getPlayers().get(i);
            int thisPlayerViewNum = playerView.getViewNum();
            if(thisPlayerViewNum == p.getTurnOrder())
            {
                playerView.updatePlayerInfo(p);
                playerView.setTextColor(p.isMyTurn());
                update();
            }
        }
    }
}
