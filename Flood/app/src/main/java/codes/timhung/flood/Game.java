package codes.timhung.flood;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Game {

    private enum GameState {
        START, RUNNING, END
    }

    private Context context;
    private SurfaceHolder holder;
    private Rect screen;
    private Resources resources;
    private GameState state = GameState.START;
    private BitmapFactory.Options options;

    public Game(Context context, Rect screen, SurfaceHolder holder, Resources resources) {
        this.context = context;
        this.screen = screen;
        this.holder = holder;
        this.resources = resources;

        restartGame();
    }

    public void restartGame() {
        // TODO
    }

    public void onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();

        if (state == GameState.RUNNING) {
        } else if(state == GameState.START) {
            state = GameState.RUNNING;
        } else if(state == GameState.END) {
            restartGame();
            state = GameState.RUNNING;
        }
    }

    /**
     * Game logic is checked here! Hitboxes, movement, etc.
     */
    public void update() {
        if(state == GameState.RUNNING){
        }
    }

    /**
     * Decides what to draw
     */
    public void draw() {
        //Log.d("GAME_DRAW", "Locking canvas");
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            switch (state) {
                case RUNNING:
                    canvas.drawColor(Color.LTGRAY);
                    break;
                case END:
                    canvas.drawColor(Color.DKGRAY);
                case START:
                    canvas.drawColor(Color.BLACK);
                    break;
            }
            drawGame(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Draws game resources
     * @param canvas Canvas to be drawn on
     */
    private void drawGame(Canvas canvas) {
        // TODO
    }
}