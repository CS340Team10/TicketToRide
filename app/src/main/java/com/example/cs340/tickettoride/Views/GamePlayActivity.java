package com.example.cs340.tickettoride.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cs340.tickettoride.R;

import java.util.ArrayList;
import java.util.List;

import ClientModel.ClientModel;
import Services.GameNotificationService;
import common.DestCard;
import common.PlayerAttributes;
import common.TrainCard;

public class GamePlayActivity extends AppCompatActivity implements IGamePlayView {

    IMapView mapView = new MapView();
    IChatHistoryView chatHistoryView = new ChatHistoryView();
    IPickTrainCardView pickTrainCardView = new PickTrainCardView();
    DrawerLayout drawerLayout;

    // For testing
    int commandId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        mapView.setup(this);
        chatHistoryView.setup(this);
        pickTrainCardView.setup(this);
        drawerLayout = findViewById(R.id.gamePlayDrawers);

        if (getFragmentManager().findFragmentById(R.id.leftDrawer) == null)
        {
            IHandView handView = new HandView();
            getSupportFragmentManager().beginTransaction().replace(R.id.leftDrawer, (Fragment) handView).commit();
        }
        drawerLayout.closeDrawers();

        // Just for testing
        Button phase2Passoff = findViewById(R.id.phase2PassoffButton);
        phase2Passoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Run this little test script for the TA's.
                switch (commandId) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "Updating player points, num train cards, num dest cards, num train cars", Toast.LENGTH_LONG).show();
                        PlayerAttributes attr = new PlayerAttributes();
                        attr.color = PlayerAttributes.Color.green;
                        attr.points = 515;
                        attr.playerId = ClientModel.getInstance().getUser().getId();
                        attr.username = ClientModel.getInstance().getUser().getUsername();
                        attr.trainCarNum = 1000;
                        attr.destCardNum = 1001;
                        attr.trainCardNum = 1002;

                        GameNotificationService.getInstance().playerUpdated(attr);
                        commandId ++;
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "Add 3 black train cards for this player", Toast.LENGTH_LONG).show();
                        GameNotificationService.getInstance().trainCardChosen(ClientModel.getInstance().getUser().getId(), new TrainCard(TrainCard.Colors.black));
                        GameNotificationService.getInstance().trainCardChosen(ClientModel.getInstance().getUser().getId(), new TrainCard(TrainCard.Colors.black));
                        GameNotificationService.getInstance().trainCardChosen(ClientModel.getInstance().getUser().getId(), new TrainCard(TrainCard.Colors.black));
                        commandId++;
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "Add dest cards for this player", Toast.LENGTH_LONG).show();
                        List<DestCard> newCards = new ArrayList<>();
                        newCards.add(new DestCard("The Shire", "Mt. Doom", 100000));
                        GameNotificationService.getInstance().destCardsChosen(ClientModel.getInstance().getUser().getId(), newCards);
                        commandId++;
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "Update visible and invisible train card deck", Toast.LENGTH_LONG).show();
                        List<TrainCard> newVisible = new ArrayList<>();
                        newVisible.add(new TrainCard(TrainCard.Colors.wildcard));
                        newVisible.add(new TrainCard(TrainCard.Colors.wildcard));
                        newVisible.add(new TrainCard(TrainCard.Colors.wildcard));
                        newVisible.add(new TrainCard(TrainCard.Colors.wildcard));
                        newVisible.add(new TrainCard(TrainCard.Colors.wildcard));
                        GameNotificationService.getInstance().trainCardDeckUpdated(newVisible, 505);
                        commandId++;
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "Update invisible dest card deck", Toast.LENGTH_LONG).show();
                        GameNotificationService.getInstance().destCardDeckUpdated(565);
                        commandId++;
                        break;
                    case 5:
                        Toast.makeText(getApplicationContext(), "Claim route", Toast.LENGTH_LONG).show();
                        List<TrainCard> used = new ArrayList<>();
                        used.add(new TrainCard(TrainCard.Colors.black));
                        used.add(new TrainCard(TrainCard.Colors.black));
                        used.add(new TrainCard(TrainCard.Colors.black));
                        GameNotificationService.getInstance().routeClaimed(ClientModel.getInstance().getUser().getId(), "vanc_calg", used);
                        commandId++;
                        break;
                    case 6:
                        Toast.makeText(getApplicationContext(), "Chat message", Toast.LENGTH_LONG).show();
                        GameNotificationService.getInstance().chat(ClientModel.getInstance().getUser().getId(), "Test chat from test script");
                        commandId = 0; // Back to beginning
                        break;
                }
            }
        });
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
