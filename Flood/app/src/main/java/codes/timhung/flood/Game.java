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

import java.util.HashMap;
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

    private HashMap<CellColor, Paint> paintMap;

    private int gridWidth = 24;
    private int gridHeight = 24;
    private int cellWidth;
    private int cellHeight;
    private CellColor grid[][];
    private CellColor brushColor;

    private int moves;
    private int highScore;

    public Game(Context context, Rect screen, SurfaceHolder holder, Resources resources) {
        this.context = context;
        this.screen = screen;
        this.holder = holder;
        this.resources = resources;

        highScore = Integer.MAX_VALUE;
        restartGame();
    }

    public void restartGame() {
        Log.d("RESTARTGAME", "Restarting!");
        cellWidth = screen.width() / gridWidth;
        cellHeight = screen.height() / gridHeight;

        grid = new CellColor[gridWidth][gridHeight];
        for(int j = 0; j < grid[0].length; j++) {
            for(int i = 0; i < grid.length; i++) {
                grid[i][j] = CellColor.values()[(int)(Math.random() * (CellColor.values().length) - 1)];
            }
        }

        paintMap = new HashMap<>();

        Paint greenPaint = new Paint();
        greenPaint.setColor(resources.getColor(R.color.cellGreen));
        paintMap.put(CellColor.GREEN, greenPaint);

        Paint bluePaint = new Paint();
        bluePaint.setColor(resources.getColor(R.color.cellBlue));
        paintMap.put(CellColor.BLUE, bluePaint);

        Paint purplePaint = new Paint();
        purplePaint.setColor(resources.getColor(R.color.cellPurple));
        paintMap.put(CellColor.PURPLE, purplePaint);

        Paint blankPaint = new Paint();
        blankPaint.setColor(resources.getColor(R.color.colorButtonBorder));
        paintMap.put(CellColor.BLANK, blankPaint);

        brushColor = CellColor.BLANK;

        moves = 0;

        ((GameActivity)context).restartGame();
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
        if(color == CellColor.BLANK || x < 0 || x >= gridWidth || y < 0 || y >= gridHeight) return;
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
        ((GameActivity)context).updateMoves(moves);

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
        if(moves < highScore) {
            highScore = moves;
            ((GameActivity)context).updateHighScore(highScore);
        }
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
        for(int j = 0; j < grid[0].length; j++) {
            for(int i = 0; i < grid.length; i++) {
                canvas.drawRect(
                    i * cellWidth,
                    j * cellHeight,
                    i * cellWidth + cellWidth,
                    j * cellHeight + cellHeight,
                    paintMap.get(grid[i][j])
                );
            }
        }
    }
}