package com.example.cs340.tickettoride.Views;

import java.util.Map;

/**
 * Created by Joseph on 2/26/2018.
 */

public interface IMapView {
    Map<String, IRouteView> getRouteViews();
    void highlightRoute(String routeID);
}
