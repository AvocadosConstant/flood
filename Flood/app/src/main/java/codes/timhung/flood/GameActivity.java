package codes.timhung.flood;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class GameActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final GameView gameView = (GameView)findViewById(R.id.gameView);

        final ToggleButton buttonGreen = (ToggleButton)findViewById(R.id.buttonGreen);
        final ToggleButton buttonBlue = (ToggleButton)findViewById(R.id.buttonBlue);
        final ToggleButton buttonPurple = (ToggleButton)findViewById(R.id.buttonPurple);

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
