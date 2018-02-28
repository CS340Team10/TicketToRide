package com.example.cs340.tickettoride.Views;

import java.util.List;

import common.DestCard;
import common.TrainCard;

/**
 * Created by Joseph on 2/26/2018.
 */

public interface IHandView {
    boolean isShowingTrainCards();
    boolean isShowingDestCards();
    void updateTrainHand(List<TrainCard> cards);
    void updateDestHand(List<DestCard> cards);
    void showTrainCards();
    void showDestCards();
}
