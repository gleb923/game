package com.example.gleb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ControlGun extends View {
    ShootListener shootListener;

    public ControlGun(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = canvas.getWidth() / 2;
        float centerY = canvas.getHeight() / 2;

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawCircle(centerX, centerY, 100, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (shootListener != null) {
                shootListener.onShoot();
            }
        }
        return true;
    }

    public void setOnShootListener(ShootListener l) {
        shootListener = l;
    }

    public static interface ShootListener {
        void onShoot();
    }
}
