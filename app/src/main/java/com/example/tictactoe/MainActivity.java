package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar=findViewById(R.id.grid_size_seek_bar);
        textView=findViewById(R.id.grid_size_text_view);
        setText();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setText();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button startButton = findViewById(R.id.go_button);
        startButton.setOnClickListener(view -> {
            final int size = seekBar.getProgress() + 3;
            Intent intent = new Intent(this, Game.class);
            intent.putExtra("size", size);
            startActivity(intent);
        });
    }

    private void setText() {
        final String size = String.valueOf(seekBar.getProgress() + 3);
        final String text = "Grid Size: " + size + "x" + size;
        textView.setText(text);
    }
}