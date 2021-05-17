package com.example.gleb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Game extends SurfaceView implements Runnable {
    private final int MILLIS_IN_SECOND = 1000;
    private Thread thread;
    private SurfaceHolder holder;
    private long fps;
    private volatile boolean isPlaying;
    private boolean isPaused = false;
    Hero hero;

    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();

        hero = new Hero(500, 500, "#124959");
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();

            canvas.drawColor(Color.BLUE);

            hero.render(canvas);

            printDebuggingText(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void printDebuggingText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        canvas.drawText("Hero: " + hero.x + ", "+ hero.y, 30, 90, paint);
    }

    private void update() {
        hero.updatePosition(fps);
    }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch(Exception e) {
            Log.e("Error", "joining thread");
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while(isPlaying) {
            long frameStartTime = System.currentTimeMillis();

            if (!isPaused) {
                update();
            }
            draw();

            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            if (timeThisFrame > 0) {
                fps = MILLIS_IN_SECOND / timeThisFrame;
            }
        }

        draw();
    }
}
