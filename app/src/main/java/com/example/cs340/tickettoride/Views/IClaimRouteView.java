package com.example.cs340.tickettoride.Views;

import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import common.ICard;
import common.Route;

/**
 * Created by Joseph on 3/5/2018.
 */

public interface IClaimRouteView
{
    void offerRoutes(List<Route> routes);
    void dialogCreateAndShow();
    void dismissDialog();
    void setup(AppCompatActivity activity);
    void showToast(String msg);
    void setAvailableCards(List<Pair<ICard, Integer>> availableCards);
}
