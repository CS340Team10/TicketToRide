package com.example.cs340.tickettoride.Views;

import android.app.Activity;

import common.Deck;

/**
 * Created by Joseph on 3/4/2018.
 */

public interface IPickDestCardView
{
    void offerDestCards(Deck cards, int minSelected);
    void dialogCreateAndShow();
    void showToast(String msg);
    void setup(Activity activity);
}
