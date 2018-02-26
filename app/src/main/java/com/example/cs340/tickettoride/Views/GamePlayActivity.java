package com.example.cs340.tickettoride.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cs340.tickettoride.R;

import java.util.List;

import common.DestCard;
import common.TrainCard;

public class GamePlayActivity extends AppCompatActivity implements IGamePlayView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
    }

    @Override
    public void enableTrainCardButton() {

    }

    @Override
    public void disableTrainCardButton() {

    }

    @Override
    public void enableDrawRouteButton() {

    }

    @Override
    public void disableDrawRouteButton() {

    }

    @Override
    public void enableClaimRouteButton() {

    }

    @Override
    public void disableClaimRouteButton() {

    }

    @Override
    public void dismissPickDestCardView() {

    }

    @Override
    public void offerDestCards(List<DestCard> cards) {

    }

    @Override
    public void dismissPickTrainCardView() {

    }

    @Override
    public void offerTrainCards(List<TrainCard> cards) {

    }

    @Override
    public void dismissClaimRouteView() {

    }

    @Override
    public void offerRoutes(List<String> routeIDs) {

    }

    @Override
    public void setDestDeckSize(int n) {

    }

    @Override
    public void setTrainDeckSize(int n) {

    }
}
