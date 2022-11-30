package com.example.tictactoe;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameLogic {
    private int[][] gameBoard;

    private Button playAgainBtn;
    private TextView playerTurn;
    private int player = 1;

    private int[] winType = {-1, -1, -1};

    GameLogic() {
        gameBoard = new int[3][3];
        resetGameBoard();
    }

    public void resetGameBoard() {
        for (int r=0;r<3;r++) {
            for (int c=0;c<3;c++) {
                gameBoard[r][c] = 0;
            }
        }
        setPlayer(1);
        int[] arr = {-1, -1, -1};
        setWinType(arr);
        if (playAgainBtn != null) {
            playAgainBtn.setVisibility(View.GONE);
        }
    }

    public void setWinType(int[] winType) {
        this.winType = winType;
    }

    public boolean updateGameBoard(int row, int col) {
        if (gameBoard[row-1][col-1] == 0 ){
            gameBoard[row-1][col-1] = player;

            return true;
        }
        return false;
    }

    public boolean winnerCheck() {
        boolean isWinner = false;

        // Horizontal check winType = 1
        for (int r=0; r<3;r++) {
            if(gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][1] == gameBoard[r][2] && gameBoard[r][0] != 0) {
                winType = new int[] {r, 0, 1};
                isWinner = true;
            }
        }

        // Vertical check winType = 2
        for (int c=0; c<3;c++) {
            if(gameBoard[0][c] == gameBoard[1][c] && gameBoard[1][c] == gameBoard[2][c] && gameBoard[0][c] != 0) {
                winType = new int[] {0, c, 2};
                isWinner = true;
            }
        }

        // Negative Diagonal check winType = 3
        if(gameBoard[0][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[2][2] && gameBoard[0][0] != 0) {
            winType = new int[] {0, 2, 3};
            isWinner = true;
        }

        // Positive Diagonal check winType = 4
        if(gameBoard[2][0] == gameBoard[1][1] && gameBoard[1][1] == gameBoard[0][2] && gameBoard[0][2] != 0) {
            winType = new int[] {2, 2, 4};
            isWinner = true;
        }

        int boardFilled = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (gameBoard[r][c] != 0) boardFilled++;
            }
        }

        if(isWinner) {
            playerTurn.setText("Player " +getCurrPlayer() + " has WON the game!");
            playAgainBtn.setVisibility(View.VISIBLE);
            return true;

        } else if (boardFilled == 9) {
            playerTurn.setText("Tie Game!");
            playAgainBtn.setVisibility(View.VISIBLE);
            return true;
        } else {
            return false;
        }
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
        setPlayerText();
    }

    public Button getPlayAgainBtn() {
        return playAgainBtn;
    }

    public void setPlayAgainBtn(Button playAgainBtn) {
        this.playAgainBtn = playAgainBtn;
    }

    public TextView getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
        setPlayerText();
    }

    private void setPlayerText() {
        if (playerTurn != null) {
            String playerText = getCurrPlayer();
            playerTurn.setText("Player " + playerText + " Turn");
        }
    }

    private String getCurrPlayer() {
        return player % 2 == 0 ? "O" : "X";
    }

    public int[] getWinType() {
        return winType;
    }
}
