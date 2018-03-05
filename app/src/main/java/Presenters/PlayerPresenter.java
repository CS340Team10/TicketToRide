package Presenters;

import com.example.cs340.tickettoride.Views.IPlayerView;

import java.util.ArrayList;

import ClientModel.ClientModel;
import ClientModel.Player;

/**
 * Created by matto on 2/28/2018.
 */

public class PlayerPresenter implements IPlayerPresenter {
    private IPlayerView playerView;

    public PlayerPresenter(IPlayerView playerView)
    {
        this.playerView = playerView;
    }

    @Override
    public void update() {
        ClientModel clientModel = ClientModel.getInstance();
        String username = playerView.getUsername();
        for (Player p : clientModel.getGame().getPlayers()) {
            if (p.getUsername().equals(username)) {
                playerView.setScore(p.getPoints());
                playerView.setNumDestCards(p.getDestCards().size());
                playerView.setNumTrainCards(p.getTrainCards().size());
                playerView.setNumTrainsLeft(p.getTrainsLeft());
                playerView.update();
            }
        }
    }
    public IPlayerView getPlayerView() {
        return playerView;
    }

    public void setPlayerView(IPlayerView playerView) {
        this.playerView = playerView;
    }
}
