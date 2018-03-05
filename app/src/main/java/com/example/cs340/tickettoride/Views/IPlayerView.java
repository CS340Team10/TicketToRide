package com.example.cs340.tickettoride.Views;

import android.app.Activity;

import ClientModel.Player;

/**
 * Created by matto on 2/28/2018.
 */

public interface IPlayerView {
    public void update();
    public void setup(Activity activity, int viewNum);

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
}
