package Presenters;

import android.support.v7.app.AppCompatActivity;

import com.example.cs340.tickettoride.Views.IGameEndView;

import java.util.Observable;
import java.util.Observer;

import ClientModel.ClientModel;

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
        gameEndView.loadPointSummary(ClientModel.getInstance().getPointSummaries(), ClientModel.getInstance().getGame().getPlayers());
    }

    @Override
    public void update(Observable o, Object arg)
    {
        gameEndView.loadPointSummary(ClientModel.getInstance().getPointSummaries(), ClientModel.getInstance().getGame().getPlayers());
    }
}
