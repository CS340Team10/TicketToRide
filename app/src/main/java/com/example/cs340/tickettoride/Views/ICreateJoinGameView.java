package com.example.cs340.tickettoride.Views;

/**
 * Created by ephraimkunz on 1/31/18.
 */

public interface ICreateJoinGameView {
    void displayErrorMessage(String message);
    void setAvailableGames(String[] gameNames);
    String getNewGameName();
    int getNewGameNumPlayers();
    void setCreateGameButtonEnabled(boolean enabled);
    void switchToView(Class<?> newViewClass);
}
