package com.example.cs340.tickettoride.Views;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.cs340.tickettoride.R;

import java.util.List;

import Presenters.GameEndPresenter;
import Presenters.IGameEndPresenter;
import common.PlayerPointSummary;

public class GameEndActivity extends AppCompatActivity implements IGameEndView
{
    private final int MAX_SUPPORTED_PLAYERS = 5;//The view is designed to only display up to 5 people
    IGameEndPresenter presenter = new GameEndPresenter(this);
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
    }

    /**
     * @post This will take the player point summaries and load them into the view.
     * @post Can support displaying up to 5 players
     * @post If the list is empty, then no players will be shown
     * @post None of the categorical labels are affected
     * @pre This method should not be called until AFTER onCreate() has happened
     * @pre the list of playerPointSummaries should not be null.
     * @param playerPointSummaries the list of objects containing the summary of players' points and achievements to show at the end of the game
     */
    @Override
    public void loadPointSummary(List<PlayerPointSummary> playerPointSummaries) {
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
            String playerNames = "";
            String claimedRoutePnts = "";
            String destCardPnts = "";
            String longestRoutePnts = "";
            String totalPnts = "";
            String wonGame = "";
            if (i < numPlayers)
            {
                playerNames = playerPointSummaries.get(i).getPlayerId();
                claimedRoutePnts = Integer.toString(playerPointSummaries.get(i).getClaimedRoutePoints());
                destCardPnts = Integer.toString(playerPointSummaries.get(i).getDestCardPoints());
                longestRoutePnts = Integer.toString(playerPointSummaries.get(i).getLongestRoutePoints());
                totalPnts = Integer.toString(playerPointSummaries.get(i).getTotalPoints());
                if (playerPointSummaries.get(i).isWinner())
                {
                    wonGame = "YES";
                }
                else
                {
                    wonGame = "NO";
                }
            }
            txtPlayerNames[i].setText(playerNames);
            txtClaimedRoutePnts[i].setText(claimedRoutePnts);
            txtDestCardPnts[i].setText(destCardPnts);
            txtLongestRoutePnts[i].setText(longestRoutePnts);
            txtTotalPnts[i].setText(totalPnts);
            txtWonGame[i].setText(wonGame);
        }
    }
}
