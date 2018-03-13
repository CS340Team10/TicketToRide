package com.example.cs340.tickettoride.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.cs340.tickettoride.R;

import java.util.ArrayList;
import java.util.List;

import Presenters.GamePlayPresenter;
import Presenters.IGamePlayPresenter;
import Services.GameNotificationService;
import common.PlayerPointSummary;

public class GamePlayActivity extends AppCompatActivity implements IGamePlayView {

    IMapView mapView = new MapView();
    IChatHistoryView chatHistoryView = new ChatHistoryView();
    IPickTrainCardView pickTrainCardView = new PickTrainCardView();
    DrawerLayout drawerLayout;
    IPickDestCardView pickDestCardView = new PickDestCardView();
    ArrayList<IPlayerView> playerViews = new ArrayList<>();
    Button trainCardButton;
    Button destCardButton;
    Button claimRouteButton;

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
        for(int i = 0; i < 5; i++)
        {
            playerViews.add(new PlayerView());
            playerViews.get(i).setup(this, i);
        }
        trainCardButton = findViewById(R.id.drawTrainCardsButton);
        destCardButton = findViewById(R.id.drawDestCardsButton);
        claimRouteButton = findViewById(R.id.claimRouteButton);

        presenter = new GamePlayPresenter(this); // Must create after buttons inflated.

        if (getFragmentManager().findFragmentById(R.id.leftDrawer) == null)
        {
            IHandView handView = new HandView();
            getSupportFragmentManager().beginTransaction().replace(R.id.leftDrawer, (Fragment) handView).commit();
        }
        drawerLayout.closeDrawers();

        // Just for testing
        Button phase2TestButton = findViewById(R.id.phase2PassoffButton);
        phase2TestButton.setOnClickListener(new Phase2OnClickListener());
    }

    private class Phase2OnClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            // Fake like the game is over for testing the score screen
            List<PlayerPointSummary> pps = new ArrayList<>();
            pps.add(new PlayerPointSummary("pid1", 1, 2, 3, false));
            pps.add(new PlayerPointSummary("pid2", 55, 66, 77, true));
            pps.add(new PlayerPointSummary("pid3", 5, 4, 3, false));
            GameNotificationService.getInstance().gameOverStatistics(pps);
        }
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


    public void setTrainDeckSize(int n) {

    }
}
