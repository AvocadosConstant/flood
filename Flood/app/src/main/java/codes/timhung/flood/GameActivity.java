package codes.timhung.flood;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GameActivity extends ActionBarActivity {

    GameView gameView;
    ToggleButton buttonGreen;
    ToggleButton buttonBlue;
    ToggleButton buttonPurple;
    TextView movesText;
    TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = (GameView)findViewById(R.id.gameView);

        buttonGreen = (ToggleButton)findViewById(R.id.buttonGreen);
        buttonBlue = (ToggleButton)findViewById(R.id.buttonBlue);
        buttonPurple = (ToggleButton)findViewById(R.id.buttonPurple);

        movesText = (TextView)findViewById(R.id.scoreText);
        scoreText = (TextView)findViewById(R.id.highScoreText);

        handleButtons();
    }

    public void restartGame() {
        updateMoves(0);
        buttonGreen.setChecked(false);
        buttonBlue.setChecked(false);
        buttonPurple.setChecked(false);
    }

    public void updateMoves(int moves) {
        movesText.setText("MOVES: " + moves);
    }

    public void updateHighScore(int moves) {
        scoreText.setText("HIGH SCORE: " + moves);
    }

    public void handleButtons() {

        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonGreen.isChecked()) {
                    gameView.game.setColor(CellColor.GREEN);

                    buttonBlue.setChecked(false);
                    buttonPurple.setChecked(false);
                } else buttonGreen.setChecked(true);
            }
        });

        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonBlue.isChecked()) {
                    gameView.game.setColor(CellColor.BLUE);

                    buttonGreen.setChecked(false);
                    buttonPurple.setChecked(false);
                } else buttonBlue.setChecked(true);
            }
        });

        buttonPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonPurple.isChecked()) {
                    gameView.game.setColor(CellColor.PURPLE);

                    buttonGreen.setChecked(false);
                    buttonBlue.setChecked(false);
                } else buttonPurple.setChecked(true);
            }
        });

    }
}
