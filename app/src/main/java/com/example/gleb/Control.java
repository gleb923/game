package com.example.gleb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import static java.lang.Math.sqrt;

public class Control extends View {
    private int width;
    private int height;
    private boolean isPress = false;
    private float startX;
    private float startY;
    private float shiftX = 0;
    private float shiftY = 0;

    OnChangeSpeed changeSpeedListener;

    public Control(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = canvas.getWidth();
        height = canvas.getHeight();
        float centerX = width / 2;
        float centerY = width / 2;
        float radiusOut = width / 2;
        float radiusIn = width / 4;

        Paint paint = new Paint();
        paint.setColor(Color.argb(120, 255, 255, 255));
        canvas.drawCircle(centerX, centerY, radiusOut, paint);

        paint.setColor(Color.WHITE);
        if (isPress) {
            float cx = centerX + shiftX;
            float cy = centerY + shiftY;
            float radius_center = 80 - radiusIn/3;
            float x = cx - centerX;
            float y = cy - centerY;
            float ratio = (float) sqrt(cx*cx + cy*cy) / radius_center;
            if (sqrt(x*x + y*y) > radius_center) {
                cx = cx / ratio;
                cy = cy / ratio;
            }

            canvas.drawCircle(cx, (float) cy, radiusIn, paint);
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

            if (changeSpeedListener != null) {
                changeSpeedListener.onChange(shiftX, shiftY);
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
            changeSpeedListener.onChange(shiftX, shiftY);
        }
    }

    void setOnChangeListener(OnChangeSpeed listener) {
        changeSpeedListener = listener;
    }

    public static interface OnChangeSpeed {
        void onChange(float vx, float vy);
    }
}
