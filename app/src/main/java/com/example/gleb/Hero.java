package com.example.gleb;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

public class Hero {
    float x;
    float y;
//    private String color = "#eeffee";
    Bitmap imagehero;
    private RectF gameBoard;
//    Bitmap rotate_imagehero;



    float vx = 0;
    float vy = 0;
    float direction = 0;
    private int width = 20;
    private int height = 50;
    Paint paint;

    Hero(float x, float y, RectF gameBoard, Bitmap imagehero) {
        this.x = x;
        this.y = y;
        this.gameBoard = gameBoard;
        this.imagehero = Bitmap.createScaledBitmap(imagehero,100, 100,true);
//        this.rotate_imagehero = this.imagehero;

        this.paint = new Paint();

    }


    void render(Canvas canvas) {
//        rotate_imagehero = Bitmap.createBitmap(imagehero, 0, 0, imagehero.getWidth(),imagehero.getHeight(),matrix,true);
        Matrix matrix = new Matrix();
//        float centerX = x + width / 2;
//        float centerY = y + height / 2;
//        float cx = centerX + (float)(100 * Math.cos(Math.toRadians(direction)));
//        float cy = centerY + (float)(-1 * 100 * Math.sin(Math.toRadians(direction)));
        float degrees = -direction + 90;
        matrix.preRotate(degrees);
//        paint.setColor(Color.parseColor(this.color));
//        canvas.drawRect(x, y, x + width, y + height, paint );
        canvas.drawBitmap(Bitmap.createBitmap(imagehero, 0, 0, imagehero.getWidth(), imagehero.getHeight(), matrix,true), x-44, y-20,paint);
//        paint.setTextSize(30);
//        canvas.drawText(degrees + "", x, y, paint);
//        canvas.drawBitmap(imagehero, x-44, y-20,paint);
//        canvas.drawCircle(cx, cy, 10, paint);


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
