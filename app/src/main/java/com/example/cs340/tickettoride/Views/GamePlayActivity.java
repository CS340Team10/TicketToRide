package com.example.cs340.tickettoride.Views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.cs340.tickettoride.R;

import java.util.List;

import common.DestCard;
import common.TrainCard;

public class GamePlayActivity extends Activity implements IGamePlayView {

    IMapView mapView = new MapView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        ImageView map = findViewById(R.id.mapView);
        mapView.setParams(this, map);
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
