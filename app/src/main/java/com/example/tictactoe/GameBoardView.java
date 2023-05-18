package com.example.tictactoe;

import static java.lang.Math.min;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Pair;
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
    private Paint victoryPaint;
    private Paint victoryTextStrokePaint;
    private Pair<Coordinates, Coordinates> victoryCoordinates = null;

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
        player1Paint.setStrokeWidth(8);
        player1Paint.setStyle(Paint.Style.STROKE);

        player2Paint = new Paint();
        player2Paint.setColor(Color.BLUE);
        player2Paint.setStrokeWidth(8);
        player2Paint.setStyle(Paint.Style.STROKE);

        victoryPaint = new Paint();
        victoryPaint.setColor(Color.GREEN);
        victoryPaint.setStrokeWidth(16);

        victoryTextStrokePaint = new Paint();
        victoryTextStrokePaint.setColor(Color.DKGRAY);
        victoryTextStrokePaint.setTextSize(128);
        victoryTextStrokePaint.setStyle(Paint.Style.FILL);
        victoryTextStrokePaint.setTypeface(Typeface.DEFAULT_BOLD);
        victoryTextStrokePaint.setAntiAlias(true);
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
                    canvas.drawLine(i * cellWidth, j * cellHeight, (i + 1) * cellWidth,
                                    (j + 1) * cellHeight, player1Paint);
                    canvas.drawLine((i + 1) * cellWidth, j * cellHeight, i * cellWidth,
                                    (j + 1) * cellHeight, player1Paint);
                } else if (grid[i][j] == player2) {
                    // Draw an O
                    canvas.drawCircle(i * cellWidth + cellWidth / 2.f,
                                      j * cellHeight + cellHeight / 2.f, cellWidth / 2.f,
                                      player2Paint);
                }
            }
        }

        if (victoryCoordinates != null) {
            Coordinates start = victoryCoordinates.first;
            Coordinates end = victoryCoordinates.second;
            canvas.drawLine(start.x * cellWidth + cellWidth / 2.f,
                            start.y * cellHeight + cellHeight / 2.f,
                            end.x * cellWidth + cellWidth / 2.f,
                            end.y * cellHeight + cellHeight / 2.f, victoryPaint);

            canvas.drawText("Player " + grid[start.x][start.y] + " wins!", 0, getHeight() * 0.25f,
                            victoryTextStrokePaint);
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
        victoryCoordinates = null;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = 0;
            }
        }
        invalidate();
    }

    private void play(int x, int y) {
        if (this.victoryCoordinates == null && grid[x][y] == 0) {
            grid[x][y] = currentPlayer;
            this.victoryCoordinates = checkForWinner(x, y);
            invalidate();
            if (currentPlayer == player1) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }
        }
    }

    private Pair<Coordinates, Coordinates> checkForWinner(int x, int y) {
        final int countToWin = min(gridSize, 5);

        int currentX = x;
        int currentY = y;
        int currentCount = 1;
        while (currentX > 0 && grid[currentX - 1][currentY] == currentPlayer) {
            currentX--;
            currentCount++;
        }
        int startX = currentX;
        int startY = currentY;
        currentX = x;
        while (currentX < gridSize - 1 && grid[currentX + 1][currentY] == currentPlayer) {
            currentX++;
            currentCount++;
        }
        if (currentCount >= countToWin) {
            return new Pair<>(new Coordinates(startX, startY), new Coordinates(currentX, currentY));
        }

        currentX = x;
        currentCount = 1;
        while (currentY > 0 && grid[currentX][currentY - 1] == currentPlayer) {
            currentY--;
            currentCount++;
        }
        startX = currentX;
        startY = currentY;
        currentY = y;
        while (currentY < gridSize - 1 && grid[currentX][currentY + 1] == currentPlayer) {
            currentY++;
            currentCount++;
        }
        if (currentCount >= countToWin) {
            return new Pair<>(new Coordinates(startX, startY), new Coordinates(currentX, currentY));
        }

        currentY = y;
        currentCount = 1;
        while (currentX > 0 && currentY > 0 && grid[currentX - 1][currentY - 1] == currentPlayer) {
            currentX--;
            currentY--;
            currentCount++;
        }
        startX = currentX;
        startY = currentY;
        currentX = x;
        currentY = y;
        while (currentX < gridSize - 1 && currentY < gridSize - 1 && grid[currentX + 1][currentY + 1] == currentPlayer) {
            currentX++;
            currentY++;
            currentCount++;
        }
        if (currentCount >= countToWin) {
            return new Pair<>(new Coordinates(startX, startY), new Coordinates(currentX, currentY));
        }

        currentX = x;
        currentY = y;
        currentCount = 1;
        while (currentX > 0 && currentY < gridSize - 1 && grid[currentX - 1][currentY + 1] == currentPlayer) {
            currentX--;
            currentY++;
            currentCount++;
        }
        startX = currentX;
        startY = currentY;
        currentX = x;
        currentY = y;
        while (currentX < gridSize - 1 && currentY > 0 && grid[currentX + 1][currentY - 1] == currentPlayer) {
            currentX++;
            currentY--;
            currentCount++;
        }
        if (currentCount >= countToWin) {
            return new Pair<>(new Coordinates(startX, startY), new Coordinates(currentX, currentY));
        }

        return null;
    }

    private static class Coordinates {
        int x;
        int y;

        Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}