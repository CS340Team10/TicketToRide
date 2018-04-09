package com.example.cs340.tickettoride.Views;

import android.widget.TextView;

import Presenters.ClientRestorePresenter;

/**
 * Created by matto on 4/6/2018.
 */

public class ClientRestoreActivity implements IClientRestoreView {
    public ClientRestorePresenter mPresenter;
    public TextView textMessage;

    @Override
    public void displayMessage(String message) {

    }
}
