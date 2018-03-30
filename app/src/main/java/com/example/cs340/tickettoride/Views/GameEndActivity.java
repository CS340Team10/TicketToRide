package com.example.cs340.tickettoride.Views;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.cs340.tickettoride.R;

import java.util.ArrayList;
import java.util.List;

import ClientModel.Player;
import Presenters.GameEndPresenter;
import Presenters.IGameEndPresenter;
import common.PlayerPointSummary;

public class GameEndActivity extends AppCompatActivity implements IGameEndView
{
    private final int MAX_SUPPORTED_PLAYERS = 5;//The view is designed to only display up to 5 people
    IGameEndPresenter presenter = new GameEndPresenter(this);
    String[] playerNames = new String[MAX_SUPPORTED_PLAYERS];
    String[] claimedRoutePnts = new String[MAX_SUPPORTED_PLAYERS];
    String[] destCardPnts = new String[MAX_SUPPORTED_PLAYERS];
    String[] longestRoutePnts = new String[MAX_SUPPORTED_PLAYERS];
    String[] totalPnts = new String[MAX_SUPPORTED_PLAYERS];
    String[] wonGame = new String[MAX_SUPPORTED_PLAYERS];
    TextView[] txtPlayerNames = new TextView[MAX_SUPPORTED_PLAYERS];
    TextView[] txtClaimedRoutePnts = new TextView[MAX_SUPPORTED_PLAYERS];
    TextView[] txtDestCardPnts = new TextView[MAX_SUPPORTED_PLAYERS];
    TextView[] txtLongestRoutePnts = new TextView[MAX_SUPPORTED_PLAYERS];
    TextView[] txtTotalPnts = new TextView[MAX_SUPPORTED_PLAYERS];
    TextView[] txtWonGame = new TextView[MAX_SUPPORTED_PLAYERS];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);
        presenter.setup(this); //Call setup on the presenter to notify it that the view has been initialized
        ConstraintLayout container = findViewById(R.id.gameEndLayout);
        for (int i = 0; i < MAX_SUPPORTED_PLAYERS; i++)
        {
            String pN = Integer.toString(i+1);
            txtPlayerNames[i] = container.findViewWithTag("txtP"+pN);
            txtClaimedRoutePnts[i] = container.findViewWithTag("txtClaimedRouteP"+pN);
            txtDestCardPnts[i] = container.findViewWithTag("txtDestCardP"+pN);
            txtLongestRoutePnts[i] = container.findViewWithTag("txtLongestRouteP"+pN);
            txtTotalPnts[i] = container.findViewWithTag("txtTotalP"+pN);
            txtWonGame[i] = container.findViewWithTag("txtWonP"+pN);
        }
        updateSummaryViews();
    }

    /**
     * @post This will take the player point summaries and store them (to be shown when the views are ready)
     * @post Can support displaying up to 5 players
     * @post If the list is empty, then no players will be shown
     * @pre the list of playerPointSummaries should not be null.
     * @param playerPointSummaries the list of objects containing the summary of players' points and achievements to show at the end of the game
     */
    @Override
    public void loadPointSummary(List<PlayerPointSummary> playerPointSummaries, ArrayList<Player> players)
    {
        //load the point summaries so they are displayed in the grid view
        int numPlayers = playerPointSummaries.size();//get the number of players to display
        if (numPlayers > MAX_SUPPORTED_PLAYERS)//if we can't support that many
        {
            Log.d("GameEndActivity", "Cannot support displaying the info of"
                    + Integer.toString(numPlayers) + " players. Only displaying first "
                    + Integer.toString(MAX_SUPPORTED_PLAYERS));
            numPlayers = MAX_SUPPORTED_PLAYERS;//display only the first 5
        }
        for (int i = 0; i < MAX_SUPPORTED_PLAYERS; i++)//Loop through all views, filling relevant ones with data and blanking out others
        {
            playerNames[i] = "";
            claimedRoutePnts[i] = "";
            destCardPnts[i] = "";
            longestRoutePnts[i] = "";
            totalPnts[i] = "";
            wonGame[i] = "";
            if (i < numPlayers)
            {
                String id = playerPointSummaries.get(i).getPlayerId();
                for(Player player : players) {
                    if (player.getId().equals(id)) {
                        playerNames[i] = player.getUsername();
                    }
                }
                claimedRoutePnts[i] = Integer.toString(playerPointSummaries.get(i).getClaimedRoutePoints());
                destCardPnts[i] = Integer.toString(playerPointSummaries.get(i).getDestCardPoints());
                longestRoutePnts[i] = Integer.toString(playerPointSummaries.get(i).getLongestRoutePoints());
                totalPnts[i] = Integer.toString(playerPointSummaries.get(i).getTotalPoints());
                if (playerPointSummaries.get(i).isWinner())
                {
                    wonGame[i] = "YES";
                }
                else
                {
                    wonGame[i] = "NO";
                }
            }
        }
    }

    /**
     * @pre This method should not be called until AFTER onCreate() has happened
     * @pre This method should not be called until AFTER loadPointSummary() has been called
     * @post updates the summary views to contain the data loaded after loadPointSummary was called
     */
    public void updateSummaryViews()
    {
        for (int i = 0; i < MAX_SUPPORTED_PLAYERS; i++)//Loop through all views, filling relevant ones with data and blanking out others
        {
            if (txtPlayerNames[i] != null) txtPlayerNames[i].setText(playerNames[i]);
            if (txtClaimedRoutePnts[i] != null) txtClaimedRoutePnts[i].setText(claimedRoutePnts[i]);
            if (txtClaimedRoutePnts[i] != null) txtDestCardPnts[i].setText(destCardPnts[i]);
            if (txtLongestRoutePnts[i] != null) txtLongestRoutePnts[i].setText(longestRoutePnts[i]);
            if (txtTotalPnts[i] != null) txtTotalPnts[i].setText(totalPnts[i]);
            if (txtWonGame[i]!= null) txtWonGame[i].setText(wonGame[i]);
        }
    }
}
