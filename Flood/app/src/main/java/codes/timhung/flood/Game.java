package codes.timhung.flood;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.LinkedList;

public class Game {

    private enum GameState {
        START, RUNNING, END
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
            Log.d("ONTOUCHEVENT", "grid[" + x + "][" + y + "]");
            floodGrid(brushColor, x, y);
            checkWin();
        } else if(state == GameState.START) {
            state = GameState.RUNNING;
        } else if(state == GameState.END) {
            restartGame();
            state = GameState.RUNNING;
        }
    }

    public void setColor(CellColor color) {
        brushColor = color;
    }

    public static int calcGridIndex(int screenDim, int gridDim, float touchLoc) {
        float f = touchLoc / screenDim;
        return (int)(gridDim * f);
    }

    public void floodGrid(CellColor color, int x, int y) {
        if(x < 0 || x >= gridWidth || y < 0 || y >= gridHeight) return;
        if(grid[x][y] != color) {
            moves++;
            CellColor originalColor = grid[x][y];

            // Floodfill algorithm
            LinkedList<Point> queue = new LinkedList<>();
            queue.push(new Point(x, y));

            while(!queue.isEmpty()) {
                Point cur = queue.pop();
                if(cur.x >= 0 && cur.x < gridWidth
                    && cur.y >= 0 && cur.y < gridHeight
                    && grid[cur.x][cur.y] == originalColor) {
                    // Need to color this cell and push its neighbors
                    grid[cur.x][cur.y] = color;
                    queue.push(new Point(cur.x - 1, cur.y));
                    queue.push(new Point(cur.x, cur.y - 1));
                    queue.push(new Point(cur.x + 1, cur.y));
                    queue.push(new Point(cur.x, cur.y + 1));
                }
            }
        }
        Log.d("floodGrid", "Moves: " + moves);
    }

    public void checkWin() {
        CellColor color = grid[0][0];
        for(int j = 0; j < grid[0].length; j++) {
            for (int i = 0; i < grid.length; i++) {
                if(grid[i][j] != color) return;
            }
        }

        // Won game!
        state = GameState.END;
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