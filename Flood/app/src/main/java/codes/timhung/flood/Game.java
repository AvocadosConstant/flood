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

    private enum CellColor {
        GREEN, BLUE, PURPLE
    }
    private Context context;
    private SurfaceHolder holder;
    private Rect screen;
    private Resources resources;
    private GameState state = GameState.START;
    private BitmapFactory.Options options;

    private Paint greenPaint;
    private Paint bluePaint;
    private Paint purplePaint;

    private int gridWidth = 12;
    private int gridHeight = 12;
    private int cellWidth;
    private int cellHeight;
    private CellColor grid[][];

    public Game(Context context, Rect screen, SurfaceHolder holder, Resources resources) {
        this.context = context;
        this.screen = screen;
        this.holder = holder;
        this.resources = resources;

        restartGame();
    }

    public void restartGame() {
        Log.d("RESTARTGAME", "Restarting!");
        cellWidth = screen.width() / gridWidth;
        cellHeight = screen.height() / gridHeight;

        grid = new CellColor[gridWidth][gridHeight];
        for(int j = 0; j < grid[0].length; j++) {
            for(int i = 0; i < grid.length; i++) {
                grid[i][j] = CellColor.values()[(int)(Math.random()*CellColor.values().length)];
            }
        }

        greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);
        bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        purplePaint = new Paint();
        purplePaint.setColor(Color.MAGENTA);
    }

    public void onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();

        if (state == GameState.RUNNING) {
        } else if(state == GameState.START) {
            state = GameState.RUNNING;
        } else if(state == GameState.END) {
            //restartGame();
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
                    drawGame(canvas);
                    break;
                case END:
                    canvas.drawColor(Color.DKGRAY);
                case START:
                    canvas.drawColor(Color.BLACK);
                    break;
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }

    /**
     * Draws game resources
     * @param canvas Canvas to be drawn on
     */
    private void drawGame(Canvas canvas) {

        screen.width();

        for(int j = 0; j < grid[0].length; j++) {
            for(int i = 0; i < grid.length; i++) {
                canvas.drawRect(
                    i * cellWidth,
                    j * cellHeight,
                    i * cellWidth + cellWidth,
                    j * cellHeight + cellHeight,
                    (grid[i][j] == CellColor.GREEN) ? greenPaint
                        : (grid[i][j] == CellColor.BLUE) ? bluePaint : purplePaint
                );
                //grid[i][j] = CellColor.values()[(int)(Math.random()*CellColor.values().length)];
            }
        }
    }
}