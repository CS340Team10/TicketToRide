package com.example.cs340.tickettoride.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cs340.tickettoride.R;

import java.util.List;

import Presenters.GameEndPresenter;
import Presenters.IGameEndPresenter;
import common.PlayerPointSummary;

public class GameEndActivity extends AppCompatActivity implements IGameEndView
{
    IGameEndPresenter presenter = new GameEndPresenter();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);
        presenter.setup(this); //Call setup on the presenter to notify it that the view has been initialized
    }

    /**
     * This will take the player point summaries and load them into the view.
     * This method should not be called until after onCreate() has happened
     * @param playerPointSummaries the list of objects containing the summary of players' points and achievements to show at the end of the game
     */
    @Override
    public void loadPointSummary(List<PlayerPointSummary> playerPointSummaries) {
        //load the point summaries so they are displayed in the grid view

    }
}
