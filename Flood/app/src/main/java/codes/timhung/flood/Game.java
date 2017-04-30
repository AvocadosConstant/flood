package codes.timhung.flood;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

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

    private Paint greenPaint;
    private Paint bluePaint;
    private Paint purplePaint;

    private int gridWidth = 12;
    private int gridHeight = 12;
    private int cellWidth;
    private int cellHeight;
    private CellColor grid[][];
    private CellColor brushColor;

    private int moves;

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

        brushColor = CellColor.GREEN;

        moves = 0;
    }

    public void onTouchEvent(MotionEvent event) {
        if (state == GameState.RUNNING) {
            int x = calcGridIndex(screen.width(), gridWidth, event.getX());
            int y = calcGridIndex(screen.height(), gridHeight, event.getY());
            Log.d("ONTOUCHEVENT", "Touched screen at " + event.getX() + ", " + event.getY());
            Log.d("ONTOUCHEVENT", "= grid[" + x + "][" + y + "]");
            floodGrid(brushColor, x, y);
        } else if(state == GameState.START) {
            state = GameState.RUNNING;
        } else if(state == GameState.END) {
            //restartGame();
            state = GameState.RUNNING;
        }
    }

    public static int calcGridIndex(int screenDim, int gridDim, float touchLoc) {
        float f = touchLoc / screenDim;
        Log.d("CALCGRIDINDEX", "Ratio is " + f);
        return (int)(gridDim * f);
    }

    public void floodGrid(CellColor color, int x, int y) {
        if(x < 0 || x >= gridWidth || y < 0 || y >= gridHeight) return;
        if(grid[x][y] != color) {
            moves++;
            grid[x][y] = color;
        }

        Log.d("floodGrid", "Moves: " + moves);
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