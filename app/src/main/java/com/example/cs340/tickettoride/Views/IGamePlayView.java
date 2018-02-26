package com.example.cs340.tickettoride.Views;

import java.util.List;

import common.DestCard;
import common.TrainCard;

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
    void dismissPickDestCardView();
    void offerDestCards(List<DestCard> cards);
    void dismissPickTrainCardView();
    void offerTrainCards(List<TrainCard> cards);
    void dismissClaimRouteView();
    void offerRoutes(List<String> routeIDs);
    void setDestDeckSize(int n);
    void setTrainDeckSize(int n);
}
