package com.example.cs340.tickettoride.Views;

import android.content.Context;
import android.widget.ImageView;

import common.PlayerAttributes;

/**
 * Created by ephraimkunz on 2/28/18.
 */

public interface IMapView {
    void setParams(Context context, ImageView view);
    void drawRouteAsClaimed(String routeId, PlayerAttributes.Color color);
}
