package com.example.cs340.tickettoride.Views;

/**
 * Created by Joseph on 2/26/2018.
 */

public interface IRouteView {
    void drawRouteAsClaimed(String playerID);
    void highlightRoute(boolean highlight);
}
