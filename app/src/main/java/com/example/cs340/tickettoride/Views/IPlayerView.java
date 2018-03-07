package com.example.cs340.tickettoride.Views;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;

import ClientModel.Player;
import Presenters.IPlayerPresenter;

/**
 * Created by matto on 2/28/2018.
 */

public interface IPlayerView {
    public void update();
    public void setup(Activity activity, int viewNum);
    public void updatePlayerInfo(Player player);

    public Player.PlayerColors getColor();
    public void setColor(Player.PlayerColors color);

    public String getUsername();
    public void setUsername(String username);

    public int getScore();
    public void setScore(int score);

    public int getNumTrainsLeft();
    public void setNumTrainsLeft(int trainsLeft);

    public int getNumDestCards();
    public void setNumDestCards(int numDestCards);

    public int getNumTrainCards();
    public void setNumTrainCards(int numTrainCards);

    public String getInfoString();

    public IPlayerPresenter getPresenter();
    public void setPresenter(IPlayerPresenter presenter);

    public Activity getActivity();
    public void setActivity(Activity activity);

    public LinearLayout getLinearLayout();
    public void setLinearLayout(LinearLayout linearLayout);

    public TextView getTextView();
    public void setTextView(TextView textView);

    public int getViewNum();
    public void setViewNum(int viewNum);
}
