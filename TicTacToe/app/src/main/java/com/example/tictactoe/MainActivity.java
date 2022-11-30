package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TicTacToeBoard ticTacToeBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ticTacToeBoard = findViewById(R.id.main_ticTacToeBoard);
        Button playAgainButton = findViewById(R.id.main_playagain_btn);
        TextView playerdisplaytv = findViewById(R.id.main_playerdisplay);

        ticTacToeBoard.setUpGame(playAgainButton,playerdisplaytv);
        playAgainButton.setOnClickListener(view -> playAgainButtonClick(view));
    }

    public void playAgainButtonClick(View view) {
        ticTacToeBoard.resetGame();
        ticTacToeBoard.invalidate();
    }
}