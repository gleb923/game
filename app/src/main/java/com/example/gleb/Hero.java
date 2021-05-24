package com.example.gleb;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

public class Hero {
    float x;
    float y;
    private String color = "#eeffee";
    private RectF gameBoard;

    float vx = 0;
    float vy = 0;
    float direction = 0;
    private float width = 20;
    private float height = 50;
    Paint paint;

    Hero(float x, float y, RectF gameBoard) {
        this.x = x;
        this.y = y;
        this.gameBoard = gameBoard;
        this.paint = new Paint();
    }

    void render(Canvas canvas) {
        float centerX = x + width / 2;
        float centerY = y + height / 2;
        float cx = centerX + (float)(100 * Math.cos(Math.toRadians(direction)));
        float cy = centerY + (float)(-1 * 100 * Math.sin(Math.toRadians(direction)));
        paint.setColor(Color.parseColor(this.color));
        canvas.drawRect(x, y, x + width, y + height, paint );
        canvas.drawCircle(cx, cy, 10, paint);


    }

    public float getCenterX() {
        return x + width / 2;
    }

    public float getCenterY() {
        return y + height / 2;
    }


    public void updatePosition(long fps) {
        float time = (fps == 0) ? 0 : 1f/fps;

        this.x = Math.min(x + time * vx, gameBoard.right - width);
        this.x = Math.max(this.x, gameBoard.left);

        this.y = Math.min(y + time * vy, gameBoard.bottom - height);
        this.y = Math.max(this.y, gameBoard.top);
    }

}
