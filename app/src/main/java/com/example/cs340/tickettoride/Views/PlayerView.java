package com.example.cs340.tickettoride.Views;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cs340.tickettoride.R;

import ClientModel.Player;
import Presenters.IPlayerPresenter;
import Presenters.PickTrainCardPresenter;
import Presenters.PlayerPresenter;

/**
 * Created by matto on 2/28/2018.
 */

public class PlayerView implements IPlayerView {
    IPlayerPresenter presenter;
    Activity activity;
    LinearLayout linearLayout = null;
    TextView textView = null;

    private Player.PlayerColors color = Player.PlayerColors.black;
    private String username = "";
    private int score = 0;
    private int numTrainsLeft = 0;
    private int numDestCards = 0;
    private int numTrainCards = 0;
    private int hexColor = ColorUtility.getColorFromPlayer(Player.PlayerColors.black);
    private String infoString = "";

    public PlayerView(Player player)
    {
        this.username = player.getUsername();
        this.score = player.getPoints();
        this.numTrainsLeft = player.getTrainsLeft();
        this.numDestCards = player.getDestCards().size();
        this.numTrainCards = player.getTrainCards().size();
        this.color = player.getColor();
        //this.hexColor = ColorUtility.getColorFromPlayer(color);
        this.infoString = getInfoString();
    }

    public void setup(final Activity activity, int viewNum) {
        this.activity = activity;
        presenter = new PlayerPresenter(this);
        switch(viewNum)
        {
            case 0:
                linearLayout = activity.findViewById(R.id.playerView1);
                textView = activity.findViewById(R.id.playerText1);
                break;
            case 1:
                linearLayout = activity.findViewById(R.id.playerView2);
                textView = activity.findViewById(R.id.playerText2);
                break;
            case 2:
                linearLayout = activity.findViewById(R.id.playerView3);
                textView = activity.findViewById(R.id.playerText3);
                break;
            case 3:
                linearLayout = activity.findViewById(R.id.playerView4);
                textView = activity.findViewById(R.id.playerText4);
                break;
            default:
                linearLayout = activity.findViewById(R.id.playerView1);
                textView = activity.findViewById(R.id.playerText1);
                break;
        }
        linearLayout.setBackgroundColor(hexColor);
        textView.setText(infoString);
    }

    public void update()
    {
        infoString = getInfoString();
        textView.setText(infoString);
    }

    @Override
    public String getInfoString()
    {
        String info = username + "   Points: " + score + "\nTR: "
                    + numTrainsLeft + " TC: " + numTrainCards + " DC: " + numDestCards;
        return info;
    }

    @Override
    public Player.PlayerColors getColor() {
        return color;
    }

    @Override
    public void setColor(Player.PlayerColors color){
        this.color = color;
        this.hexColor = ColorUtility.getColorFromPlayer(color);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int getNumTrainsLeft() {
        return numTrainsLeft;
    }

    @Override
    public void setNumTrainsLeft(int trainsLeft) {
        this.numTrainsLeft = trainsLeft;
    }

    @Override
    public int getNumDestCards() {
        return numDestCards;
    }

    @Override
    public void setNumDestCards(int numDestCards) {
        this.numDestCards = numDestCards;
    }

    @Override
    public int getNumTrainCards() {
        return numTrainCards;
    }

    @Override
    public void setNumTrainCards(int numTrainCards) {
        this.numTrainCards = numTrainCards;
    }

}
