package com.example.gleb;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Hero {
    float x;
    float y;
    private String color = "#eeffee";

    float vx = 0;
    float vy = 0;
    private float width = 20;
    private float height = 50;
    Paint paint;

    Hero(float x, float y) {
        this.x = x;
        this.y = y;
        this.paint = new Paint();
    }

    void render(Canvas canvas) {
        paint.setColor(Color.parseColor(this.color));
        canvas.drawRect(x, y, x + width, y + height, paint );
    }


    public void updatePosition(long fps) {
        float time = (fps == 0) ? 0 : 1f/fps;
        this.x = x + time * vx;
        this.y = y + time * vy;
    }
}
