package Presenters;

import com.example.cs340.tickettoride.Views.IPlayerView;
import com.example.cs340.tickettoride.Views.PlayerView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ClientModel.ClientModel;
import ClientModel.Player;
import common.Results;

/**
 * Created by matto on 2/28/2018.
 */

public class PlayerPresenter implements IPlayerPresenter, IPresenter, Observer {
    IPlayerView playerView;

    public PlayerPresenter(IPlayerView playerView)
    {
        this.playerView = playerView;
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void update() {
        playerView.update();
    }
    public IPlayerView getPlayerView() {
        return playerView;
    }

    public void setPlayerView(IPlayerView playerView) {
        this.playerView = playerView;
    }

    @Override
    public void onPostExecute(Results result) {
        // do nothing
    }

    @Override
    public void update(Observable observable, Object o) {
        ClientModel clientModel = ClientModel.getInstance();
        int userTurnNum = clientModel.getUser().getTurnOrder();
        for (int i = 0; i < clientModel.getGame().getPlayers().size(); i++) {
            Player p = clientModel.getGame().getPlayers().get(i);
            int thisPlayerViewNum = p.getTurnOrder();
            if(thisPlayerViewNum > userTurnNum)
                thisPlayerViewNum--;
            if(playerView.getUsername().equals("") && thisPlayerViewNum == playerView.getViewNum())
            {
                playerView.updatePlayerInfo(p);
                update();
            }
            if (p.getUsername().equals(playerView.getUsername())) {
                playerView.updatePlayerInfo(p);
                update();
            }
        }
    }
}
