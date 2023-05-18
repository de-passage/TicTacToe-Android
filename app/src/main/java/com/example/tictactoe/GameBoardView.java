package com.example.tictactoe;

import static java.lang.Math.min;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameBoardView extends View {
    final int player1 = 1;
    final int player2 = 2;
    Paint gridPaint;
    int gridSize = 5;
    int currentPlayer = player1;
    int[][] grid = new int[gridSize][gridSize];
    private Paint player1Paint;
    private Paint player2Paint;


    public GameBoardView(Context context) {
        super(context);
        init();
    }

    public GameBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Initialize your paint object(s) here
        gridPaint = new Paint();
        gridPaint.setColor(Color.BLACK);
        gridPaint.setStrokeWidth(4);

        player1Paint = new Paint();
        player1Paint.setColor(Color.RED);
        player1Paint.setStrokeWidth(4);
        player1Paint.setStyle(Paint.Style.STROKE);

        player2Paint = new Paint();
        player2Paint.setColor(Color.BLUE);
        player2Paint.setStrokeWidth(4);
        player2Paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int cellWidth = getWidth() / gridSize;
        int cellHeight = getHeight() / gridSize;

        // Draw vertical lines
        for (int i = 1; i < gridSize; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, getHeight(), gridPaint);
        }

        // Draw horizontal lines
        for (int i = 1; i < gridSize; i++) {
            canvas.drawLine(0, i * cellHeight, getWidth(), i * cellHeight, gridPaint);
        }

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {

                if (grid[i][j] == player1) {
                    // Draw an X
                    canvas.drawLine(i * cellWidth, j * cellHeight, (i + 1) * cellWidth, (j + 1) * cellHeight, player1Paint);
                    canvas.drawLine((i + 1) * cellWidth, j * cellHeight, i * cellWidth, (j + 1) * cellHeight, player1Paint);
                } else if (grid[i][j] == player2) {
                    // Draw an O
                    canvas.drawCircle(i * cellWidth + cellWidth / 2.f, j * cellHeight + cellHeight / 2.f, cellWidth / 2.f, player2Paint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) (event.getX() / (getWidth() / gridSize));
            int y = (int) (event.getY() / (getHeight() / gridSize));

            play(x, y);
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            performClick();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        // Call the super class method
        return super.performClick();
    }


    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
        this.grid = new int[gridSize][gridSize];
        currentPlayer = player1;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = 0;
            }
        }
        invalidate();
    }

    private void play(int x, int y) {
        if (grid[x][y] == 0) {
            grid[x][y] = currentPlayer;
            checkForWinner(x, y);
            if (currentPlayer == player1) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }
            invalidate();
        }
    }

    private void checkForWinner(int x, int y) {
        final int countToWin = min(gridSize, 5);
    }
}
