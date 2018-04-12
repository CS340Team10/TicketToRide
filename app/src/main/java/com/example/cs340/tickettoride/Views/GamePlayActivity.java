package com.example.cs340.tickettoride.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cs340.tickettoride.R;

import java.util.ArrayList;

import Communication.Poller;
import Presenters.GamePlayPresenter;
import Presenters.IGamePlayPresenter;

public class GamePlayActivity extends AppCompatActivity implements IGamePlayView {

    IMapView mapView = new MapView();
    IChatHistoryView chatHistoryView = new ChatHistoryView();
    IPickTrainCardView pickTrainCardView = new PickTrainCardView();
    DrawerLayout drawerLayout;
    IPickDestCardView pickDestCardView = new PickDestCardView();
    IClaimRouteView claimRouteView = new ClaimRouteView();
    ArrayList<IPlayerView> playerViews = new ArrayList<>();
    Button trainCardButton;
    Button destCardButton;
    Button claimRouteButton;
    TextView lastRoundWarningTxt;

    IGamePlayPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        mapView.setup(this);
        chatHistoryView.setup(this);
        pickTrainCardView.setup(this);
        drawerLayout = findViewById(R.id.gamePlayDrawers);
        pickDestCardView.setup(this);
        claimRouteView.setup(this);
        for(int i = 0; i < 5; i++)
        {
            playerViews.add(new PlayerView());
            playerViews.get(i).setup(this, i);
        }
        trainCardButton = findViewById(R.id.drawTrainCardsButton);
        destCardButton = findViewById(R.id.drawDestCardsButton);
        claimRouteButton = findViewById(R.id.claimRouteButton);
        lastRoundWarningTxt = findViewById(R.id.lastRoundWarningTxt);
        hideLastRoundWarning();
        presenter = new GamePlayPresenter(this); // Must create after buttons inflated.

        if (getFragmentManager().findFragmentById(R.id.leftDrawer) == null)
        {
            IHandView handView = new HandView();
            getSupportFragmentManager().beginTransaction().replace(R.id.leftDrawer, (Fragment) handView).commit();
        }
        drawerLayout.closeDrawers();

        // Start command poller if not already started (coming from restore)
        Poller.getInstance().startCommandPoll();
    }

    @Override
    public void enableTrainCardButton() {
        if (trainCardButton != null)
        {
            trainCardButton.setEnabled(true);
        }
    }

    @Override
    public void disableTrainCardButton() {
        if (trainCardButton != null)
        {
            trainCardButton.setEnabled(false);
        }
    }

    @Override
    public void enableDrawRouteButton() {
        if (destCardButton != null)
        {
            destCardButton.setEnabled(true);
        }
    }

    @Override
    public void disableDrawRouteButton() {
        if (destCardButton != null)
        {
            destCardButton.setEnabled(false);
        }
    }

    @Override
    public void enableClaimRouteButton() {
        if (claimRouteButton != null)
        {
            claimRouteButton.setEnabled(true);
        }
    }

    @Override
    public void disableClaimRouteButton() {
        if (claimRouteButton != null)
        {
            claimRouteButton.setEnabled(false);
        }
    }

    @Override
    public void setDestDeckSize(int n)
    {
        String btnText = "Draw Dest Cards ("+Integer.toString(n)+")";
        destCardButton.setText(btnText);
    }

    @Override
    public void goToEndGameView() {
        Intent intent = new Intent(this, GameEndActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLastRoundWarning()
    {
        if (lastRoundWarningTxt != null)
        {
            lastRoundWarningTxt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLastRoundWarning()
    {
        if (lastRoundWarningTxt != null)
        {
            lastRoundWarningTxt.setVisibility(View.INVISIBLE);
        }
    }


    public void setTrainDeckSize(int n) {

    }
}
