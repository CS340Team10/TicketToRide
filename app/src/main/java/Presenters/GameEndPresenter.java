package Presenters;

import android.support.v7.app.AppCompatActivity;

import com.example.cs340.tickettoride.Views.IGameEndView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ClientModel.ClientModel;
import ClientModel.Player;
import common.PlayerPointSummary;

/**
 * Created by Joseph on 3/20/2018.
 */

public class GameEndPresenter implements IGameEndPresenter, Observer
{
    IGameEndView gameEndView;

    public GameEndPresenter(IGameEndView gameEndView)
    {
        this.gameEndView = gameEndView;
        ClientModel.getInstance().addObserver(this);
    }

    @Override
    public void setup(AppCompatActivity activity)
    {
        gameEndView.loadPointSummary(ClientModel.getInstance().getPointSummaries());
    }

    @Override
    public void update(Observable o, Object arg)
    {
        List<PlayerPointSummary> pointSummaries = ClientModel.getInstance().getPointSummaries();
        /*for (PlayerPointSummary pps : pointSummaries)
        {
            Player p = ClientModel.getInstance().getPlayerByID(pps.getPlayerId());
            if (p != null)
            {
                pps.setPlayerId(p.getUsername());
            }
        }*/
        gameEndView.loadPointSummary(pointSummaries);
    }
}
