package com.example.gleb;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Hero {
    float x;
    float y;
    String color;

    float vx = 10;
    float vy = 10;
    private float width = 20;
    private float height = 50;
    Paint paint;

    Hero(float x, float y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.paint = new Paint();
    }

    void render(Canvas canvas) {
        paint.setColor(Color.parseColor(this.color));
        canvas.drawRect(x, y, x + width, y + height, paint );
    }


    public void updatePosition(long fps) {
        this.x = (float) (x + .1);
        this.y = (float) (y + .1);
        Log.i("new position", x + " : " + y);
    }
}
