package com.example.cs340.tickettoride.Views;

import android.app.Activity;

import common.PlayerAttributes;

/**
 * Created by ephraimkunz on 2/28/18.
 */

public interface IMapView {
    void setup(Activity activity);
    void drawRouteAsClaimed(String routeId, PlayerAttributes.Color color);
}
