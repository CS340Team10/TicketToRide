package com.example.cs340.tickettoride.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import com.example.cs340.tickettoride.R;

import java.util.List;

import common.DestCard;
import common.TrainCard;

public class GamePlayActivity extends AppCompatActivity implements IGamePlayView {

    IMapView mapView = new MapView();
    IChatHistoryView chatHistoryView = new ChatHistoryView();
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        mapView.setup(this);
        chatHistoryView.setup(this);
        drawerLayout = findViewById(R.id.gamePlayDrawers);
        if (getFragmentManager().findFragmentById(R.id.leftDrawer) == null)
        {
            IHandView handView = new HandView();
            getSupportFragmentManager().beginTransaction().replace(R.id.leftDrawer, (Fragment) handView).commit();
        }
        drawerLayout.closeDrawers();
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
