package com.example.cs340.tickettoride.Views;

/**
 * Created by ephraimkunz on 2/1/18.
 */

public interface IWaitForGameView {
    void displayMessage(String message);
    void switchToView(Class<?> newViewClass);
}
