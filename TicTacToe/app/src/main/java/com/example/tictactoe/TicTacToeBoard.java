package com.example.tictactoe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {

    private final int boardColor;
    private final int winningLineColor;

    private boolean winningLine;

    private final Paint paint = new Paint();

    private int cellSize = getWidth()/3;
    private final GameLogic game;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game = new GameLogic();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TicTacToeBoard, 0,0);

        try {
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0);
        }finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width,height);

        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimension/3;

        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if (winningLine) {
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            int row = (int) Math.ceil(y/cellSize);
            int col = (int) Math.ceil(x/cellSize);

            if(!winningLine) {
                if(game.updateGameBoard(row,col)) {
                    invalidate();

                    if(game.winnerCheck()) {
                        winningLine = true;
                        invalidate();
                    } else {
                        changePlayer();
                    }
                }
            }

            return true;
        }
        return false;
    }

    private void drawGameBoard(Canvas canvas) {
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

        for (int c=1;c<3;c++) {
          canvas.drawLine(cellSize*c, 0,cellSize*c, canvas.getWidth(), paint);
        }
        for (int r=1;r<3;r++) {
            canvas.drawLine(0, cellSize*r,canvas.getWidth() , cellSize*r, paint);
        }
    }

    private void drawMarkers(Canvas canvas) {
        for (int r=0;r<3;r++) {
            for (int c=0;c<3;c++) {
                if(game.getGameBoard()[r][c] != 0) {
                    if (game.getGameBoard()[r][c] == 1) {
                        addX(canvas, r,c);
                    } else {
                        addO(canvas, r, c);
                    }
                }
            }
        }
    }

    private void addX(Canvas canvas, int row, int col) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.x);
        canvas.drawBitmap(bmp,(col)*cellSize, (row)*cellSize ,paint);
    }

    private void addO(Canvas canvas,  int row, int col) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.o);
        canvas.drawBitmap(bmp,(col)*cellSize,(row)*cellSize ,paint);
    }

    public void setUpGame(Button playAgainBtn, TextView playerDisplay) {
        game.setPlayAgainBtn(playAgainBtn);
        game.setPlayerTurn(playerDisplay);
        playAgainBtn.setVisibility(View.GONE);
    }

    public void resetGame() {
        game.resetGameBoard();
        setWinningLine(false);
    }

    public void setWinningLine(boolean winningLine) {
        this.winningLine = winningLine;
    }

    public void changePlayer() {
        if(game.getPlayer() % 2 == 0) {
            game.setPlayer(game.getPlayer()-1);
        } else {
            game.setPlayer(game.getPlayer()+1);
        }
    }

    private void drawHorizontalLine(Canvas canvas, int row, int col) {
        canvas.drawLine(col,
                row*cellSize + cellSize/2,
                cellSize*3 ,
                row*cellSize + cellSize/2,
                paint);
    }

    private void drawVerticalLine(Canvas canvas, int row, int col) {
        canvas.drawLine(col*cellSize + cellSize/2,
                row,
                col*cellSize + cellSize/2  ,
                cellSize*3,
                paint);
    }

    private void drawDiagonalLinePositive(Canvas canvas) {
        canvas.drawLine(0,
                cellSize*3,
                cellSize*3,
                0,
                paint);
    }

    private void drawDiagonalLineNegative(Canvas canvas) {
        canvas.drawLine(0,
                0,
                cellSize*3,
                cellSize*3,
                paint);
    }

    private void drawWinningLine(Canvas canvas) {
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];

        switch (game.getWinType()[2]) {
            case 1:
                drawHorizontalLine(canvas, row, col);
                break;
            case 2:
                drawVerticalLine(canvas, row, col);
                break;
            case 3:
                drawDiagonalLineNegative(canvas);
                break;
            case 4:
                drawDiagonalLinePositive(canvas);
                break;
        }
    }
}
