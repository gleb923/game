package com.example.gleb;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.Button;

public class Bullet {
    private float vx;
    private float vy;
    private float x;
    private float y;
    private int radius = 10;
    private int halfRadius = 5;

    public Bullet(float x, float y, float direction) {
        this.x = x;
        this.y = y;
        this.vx = (float)(2000 * Math.cos(Math.toRadians(direction)));
        this.vy = (float)(-1 * 2000 * Math.sin(Math.toRadians(direction)));


    }

    public void render(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, radius, paint);
    }

    public void updatePosition(long fps) {
        float time = (fps == 0) ? 0 : 1f/fps;
        this.x = x + time * vx;
        this.y = y + time * vy;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public RectF getBoundingRect() {
        return new RectF(this.x - halfRadius, this.y - halfRadius, this.x + halfRadius, this.y + halfRadius);
    }
}
