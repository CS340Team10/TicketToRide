package com.example.cs340.tickettoride.Views;

import android.app.Activity;

import java.util.List;


/**
 * Created by Joseph on 2/26/2018.
 */

public interface IChatHistoryView {
    void setup(Activity activity);
    void updateGameHistory(List<String> history);
    void updateChatHistory(List<String> chats);
}
