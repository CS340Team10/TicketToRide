package com.example.cs340.tickettoride.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cs340.tickettoride.R;

public class GamePlayActivity extends AppCompatActivity implements IGamePlayView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
    }
}
