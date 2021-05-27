package com.example.gleb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class Control extends View {
    private int width;
    private int height;
    private boolean isPress = false;
    private float radiusOut;
    private float radiusIn;
    protected float maxRadius;
    private float centerX;
    private float centerY;
    private float startX;
    private float startY;
    private float maxShiftX = 0;
    private float maxShiftY = 0;
    private float shiftX = 0;
    private float shiftY = 0;
    private float degree = 0;
    private float cx = 0;
    private float cy = 0;
    private float maxCX = 0;
    private float maxCY = 0;

    OnChangeSpeed changeSpeedListener;

    public Control(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        radiusOut = width / 2;
        radiusIn = width / 4;
        maxRadius = radiusOut - radiusIn;
        centerX = width / 2;
        centerY = height / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        width = canvas.getWidth();
//        height = canvas.getHeight();



        Paint paint = new Paint();
        paint.setColor(Color.argb(120, 255, 255, 255));
        canvas.drawCircle(centerX, centerY, radiusOut, paint);

        paint.setColor(Color.WHITE);
        if (isPress) {
            canvas.drawCircle(centerX + shiftX, centerY + shiftY, radiusIn, paint);
        }
        else {
            canvas.drawCircle(centerX, centerY, radiusIn, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() == 1) {
            isPress = true;
            startX = event.getX();
            startY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            shiftX = event.getX() - startX;
            shiftY = event.getY() - startY;
            double radian = Math.atan(shiftX/shiftY);
            degree = (float) Math.toDegrees(radian);

            if (shiftY < 0) {
                // 1, 2 сектор
                degree += 90;
            } else {
                // 3, 4 сектор
                degree += 270;
            }

            int sign = (degree > 0 && degree < 180) ? -1 : 1;
            maxShiftX = (float)(sign * maxRadius * Math.sin(radian));
            maxShiftY = (float)(sign * maxRadius * Math.cos(radian));

            if (maxRadius*maxRadius < shiftX*shiftX + shiftY*shiftY) {
                shiftX = maxShiftX;
                shiftY = maxShiftY;
            }

            if (changeSpeedListener != null) {
                changeSpeedListener.onChange(shiftX, shiftY, degree);
            }
            invalidate();
        } else if (event.getAction() == MotionEvent.ACTION_UP && event.getPointerCount() == 1) {
            isPress = false;
            resetShift();
            invalidate();
        }
        return true;
    }

    private void resetShift() {
        shiftX = 0;
        shiftY = 0;
        if (changeSpeedListener != null) {
            changeSpeedListener.onChange(shiftX, shiftY, degree);
        }
    }

    void setOnChangeListener(OnChangeSpeed listener) {
        changeSpeedListener = listener;
    }

    public static interface OnChangeSpeed {
        void onChange(float vx, float vy, float degree);
    }
}
