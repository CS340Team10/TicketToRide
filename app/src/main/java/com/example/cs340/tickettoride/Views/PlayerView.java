package com.example.cs340.tickettoride.Views;

import android.app.Activity;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cs340.tickettoride.R;

import ClientModel.Player;
import Presenters.IPlayerPresenter;
import Presenters.PlayerPresenter;

/**
 * Created by matto on 2/28/2018.
 */

public class PlayerView implements IPlayerView {
    private IPlayerPresenter presenter;
    private Activity activity;
    private LinearLayout linearLayout = null;
    private TextView textView = null;
    private int viewNum;

    private Player.PlayerColors color = Player.PlayerColors.black;
    private String username = "";
    private int score = 0;
    private int numTrainsLeft = 0;
    private int numDestCards = 0;
    private int numTrainCards = 0;
    private int hexColor = Color.WHITE;
    private String infoString = "";

    public PlayerView() {}

    public void updatePlayerInfo(Player player)
    {
        this.username = player.getUsername();
        this.score = player.getPoints();
        this.numTrainsLeft = player.getTrainsLeft();
        this.numDestCards = player.getDestCards().size();
        this.numTrainCards = player.getTrainCards().size();
        if(player.getColor() != null) {
            this.color = player.getColor();
        }
        this.hexColor = ColorUtility.getColorFromPlayer(color);
        this.infoString = getInfoString();
    }

    public void setup(final Activity activity, int viewNum) {
        this.activity = activity;
        this.presenter = new PlayerPresenter(this);
        this.viewNum = viewNum;
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
            case 4:
                linearLayout = activity.findViewById(R.id.playerView5);
                textView = activity.findViewById(R.id.playerText5);
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
        linearLayout.setBackgroundColor(hexColor);
        infoString = getInfoString();
        textView.setText(infoString);
    }

    @Override
    public String getInfoString()
    {
        if(username.equals("")) {
            return username;
        }
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

    public void setTextColor(boolean isMyTurn)
    {
        if(isMyTurn)
            textView.setTextColor(Color.WHITE);
        else
            textView.setTextColor(Color.BLACK);
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

    public IPlayerPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(IPlayerPresenter presenter) {
        this.presenter = presenter;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }


}
