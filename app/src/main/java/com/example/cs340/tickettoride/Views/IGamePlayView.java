package com.example.cs340.tickettoride.Views;

/**
 * Created by Joseph on 2/26/2018.
 */

public interface IGamePlayView
{
    void enableTrainCardButton();
    void disableTrainCardButton();
    void enableDrawRouteButton();
    void disableDrawRouteButton();
    void enableClaimRouteButton();
    void disableClaimRouteButton();
    void setDestDeckSize(int n);
    void goToEndGameView();
}
