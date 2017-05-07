package codes.timhung.flood;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GameActivity extends Activity {

    GameView gameView;
    TextView movesText;
    TextView scoreText;

    ColorButton colorButtons[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        findViews();
        handleButtons();
    }

    public void findViews() {
        gameView = (GameView)findViewById(R.id.gameView);
        movesText = (TextView)findViewById(R.id.scoreText);
        scoreText = (TextView)findViewById(R.id.highScoreText);

        colorButtons = new ColorButton[] {
                new ColorButton((ToggleButton)findViewById(R.id.buttonOrange), CellColor.ORANGE),
                new ColorButton((ToggleButton)findViewById(R.id.buttonYellow), CellColor.YELLOW),
                new ColorButton((ToggleButton)findViewById(R.id.buttonGreen), CellColor.GREEN),
                new ColorButton((ToggleButton)findViewById(R.id.buttonBlue), CellColor.BLUE),
                new ColorButton((ToggleButton)findViewById(R.id.buttonPurple), CellColor.PURPLE)
        };
    }

    public void restartGame() {
        updateMoves(0);
        for(final ColorButton btn : colorButtons) {
            btn.setChecked(false);
        }
    }

    public void updateMoves(int moves) {
        movesText.setText("MOVES: " + moves);
    }

    public void updateHighScore(int moves) {
        scoreText.setText("HIGH SCORE: " + moves);
    }

    public void handleButtons() {
        for(final ColorButton btn : colorButtons) {
            btn.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(btn.isChecked()) {
                        gameView.game.setColor(btn.cellColor);

                        for(final ColorButton otherBtn : colorButtons) {
                            if(otherBtn.cellColor != btn.cellColor) {
                                otherBtn.setChecked(false);
                            }
                        }
                    } else btn.setChecked(true);
                }
            });
        }
    }
}
