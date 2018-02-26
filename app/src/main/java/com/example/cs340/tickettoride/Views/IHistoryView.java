package com.example.cs340.tickettoride.Views;

import java.util.List;

/**
 * Created by Joseph on 2/26/2018.
 */

public interface IHistoryView {
    boolean isShowingGameHistory();
    boolean isShowingChatHistory();
    void updateGameHistory(List<String> history);
    void updateChatHistory(List<String> history);
}
