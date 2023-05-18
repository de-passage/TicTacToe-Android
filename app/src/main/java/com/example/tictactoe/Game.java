package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        int gridSize = intent.getIntExtra("size", 5);
        GameBoardView gameBoardView = findViewById(R.id.game_board_view);
        gameBoardView.setGridSize(gridSize);
    }
}