package com.example.cs340.tickettoride.Views;

import android.app.Activity;

import java.util.List;

import common.TrainCard;

/**
 * Created by ephraimkunz on 3/3/18.
 */

public interface IPickTrainCardView {
    void setup(Activity activity);
    void setVisibleCards(List<TrainCard> cards);
    void setInvisibleCards(int invisible);
    void dismissView();
    void showToast(String message);
}
