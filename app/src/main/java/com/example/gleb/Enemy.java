package com.example.gleb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

public class Enemy {
    private float x;
    private float y;
    private float speed = 100;
    private float vx = 0;
    private float vy = 0;
    private static float radius = 30;
    private static float halfRadius = 15;
    private boolean dead = false;
    private static Random random = new Random();
    Bitmap imageenemy;


    Paint paint = new Paint();

    public Enemy(float x, float y, Bitmap imageenemy) {
        this.x = x;
        this.y = y;
        this.imageenemy = Bitmap.createScaledBitmap(imageenemy,100, 100,true);
        paint.setColor(Color.WHITE);
    }

    public static Enemy create(RectF gameBoard, Bitmap imageenemy_create) {
        int position = random.nextInt(4);
        float x = 0;
        float y = 0;


        switch (position) {
            case 0: {
                x = 0 - radius;
                y = random.nextInt((int)gameBoard.bottom);
                break;
            }
            case 1: {
                x = random.nextInt((int)gameBoard.right);
                y = 0 - radius;
                break;
            }
            case 2: {
                x = gameBoard.right + radius;
                y = random.nextInt((int)gameBoard.bottom);
                break;
            }
            case 3: {
                x = x = random.nextInt((int)gameBoard.right);
                y = gameBoard.bottom + radius;
            }
        }
        return new Enemy(x, y, imageenemy_create);
    }

    public void render(Canvas canvas) {
        canvas.drawBitmap(imageenemy,x-50, y-45,paint);
    }

    public void updatePosition(long fps) {
        float time = (fps == 0) ? 0 : 1f/fps;
        this.x = x + time * vx;
        this.y = y + time * vy;
    }

    public void updateTargetPosition(float x, float y) {
        float shiftX = x - this.x;
        float shiftY = y - this.y;
        double radian = Math.atan(shiftX/shiftY);
        float degree = (float) Math.toDegrees(radian);

        if (shiftY < 0) {
            // 1, 2 сектор
            degree += 90;
        } else {
            // 3, 4 сектор
            degree += 270;
        }

        int sign = (degree > 0 && degree < 180) ? -1 : 1;
        vx = (float)(sign * speed * Math.sin(radian));
        vy = (float)(sign * speed * Math.cos(radian));
    }

    public RectF getBoundingRect() {
        return new RectF(this.x - halfRadius, this.y - halfRadius, this.x + halfRadius, this.y + halfRadius);
    }

    public void reset() {
        this.x = 0;
        this.y = 0;
    }

    public boolean isDead() {
        return dead;
    }

    public void kill() {
        dead = true;
    }
}
