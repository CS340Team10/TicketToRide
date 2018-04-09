package com.example.cs340.tickettoride.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.cs340.tickettoride.R;

import Presenters.ClientRestorePresenter;

/**
 * Created by matto on 4/6/2018.
 */

public class ClientRestoreActivity extends AppCompatActivity implements IClientRestoreView {
    public ClientRestorePresenter mPresenter;
    public TextView textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_restore);

        textMessage = findViewById(R.id.clientRestoreText);

        mPresenter = new ClientRestorePresenter(this);
    }

    @Override
    public void displayMessage(String message) {
        textMessage.setText(message);
    }

    public void switchToView(Class<?> newViewClass)
    {
        Intent intent = new Intent(this, newViewClass);
        this.startActivity(intent);
    }
}
