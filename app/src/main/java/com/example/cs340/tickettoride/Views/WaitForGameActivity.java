package com.example.cs340.tickettoride.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.cs340.tickettoride.R;

import Presenters.IWaitForGamePresenter;
import Presenters.WaitForGamePresenter;

public class WaitForGameActivity extends AppCompatActivity implements IWaitForGameView {
    TextView message;

    private IWaitForGamePresenter presenter = new WaitForGamePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_game);

        message = findViewById(R.id.wait_message);
    }

    @Override
    public void onBackPressed() {
        // Do nothing
    }

    @Override
    public void displayMessage(String message) {
        this.message.setText(message);
    }

    @Override
    public void switchToView(Class<?> newViewClass) {
        Intent intent = new Intent(this, newViewClass);
        this.startActivity(intent);
    }
}
