package com.example.gleb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game extends SurfaceView implements Runnable {
    private int width;
    private int height;
    private final int MILLIS_IN_SECOND = 1000;
    private Thread thread;
    private SurfaceHolder holder;
    private long fps;
    private volatile boolean isPlaying;
    private boolean isPaused = false;
    private Random random = new Random();
    Hero hero;
    RectF gameBoard;
    Bullet[] bullets = new Bullet[20];
    int nextIndex = 0;


    Enemy[] enemies = new Enemy[100];
    int nextEnemyIndex = 0;



    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        gameBoard = new RectF(left, top, right, bottom);
        hero = new Hero(getWidth() / 2, getHeight() / 2, gameBoard);
        createEnemy();
        resume();

    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();

            canvas.drawColor(Color.BLUE);

            hero.render(canvas);

            for (Bullet b : bullets) {
                if (b != null) {
                    b.render(canvas);
                }
            }

            for (Enemy enemy: enemies) {
                if (enemy != null && !enemy.isDead()) {
                    enemy.render(canvas);
                }
            }

            printDebuggingText(canvas);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void printDebuggingText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        canvas.drawText(String.format("FPS: %d", fps), 30, 90, paint);
        canvas.drawText("Hero: " + hero.x + ", "+ hero.y, 30, 150, paint);
        canvas.drawText(String.format("Hero speed: %.2f, %.2f, %.2f", hero.vx, hero.vy, hero.direction), 30, 210, paint);
    }

    private void update() {
        hero.updatePosition(fps);

        for (Bullet b : bullets) {
            if (b != null) {
                b.updatePosition(fps);
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy != null && !enemy.isDead()) {
                enemy.updatePosition(fps);
                enemy.updateTargetPosition(hero.getCenterX(), hero.getCenterY());
                for (Bullet bullet : bullets) {
                    if (bullet == null) {
                        continue;
                    }

                    if (bullet.getBoundingRect().intersect(enemy.getBoundingRect())) {
                        killEnemy(enemy);
                    }
                }
            }
        }


    }

    private void killEnemy(Enemy enemy) {
        enemy.kill();
        if (random.nextBoolean()) {
            createEnemy();
        } else {
            createEnemy();
            createEnemy();
        }
    }

    private void createEnemy() {
        enemies[nextEnemyIndex] = Enemy.create(gameBoard);
        nextEnemyIndex++;
        if (nextEnemyIndex >= enemies.length) {
            nextEnemyIndex = 0;
        }
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

    public void setHeroSpeed(float vx, float vy, float direction) {
        hero.vx = vx * 3;
        hero.vy = vy * 3;
        hero.direction = direction;
    }

    public void shootHero() {
        bullets[nextIndex] = new Bullet(hero.getCenterX(), hero.getCenterY(), hero.direction);
        nextIndex += 1;
        if (nextIndex >= bullets.length) {
            nextIndex = 0;
        }
    }
}
