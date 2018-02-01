package com.example.cs340.tickettoride.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.cs340.tickettoride.R;

public class CreateJoinGameActivity extends AppCompatActivity implements ICreateJoinGameView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_join_game);
    }
}
