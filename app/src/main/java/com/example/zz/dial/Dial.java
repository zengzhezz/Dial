package com.example.zz.dial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by zz on 2016/1/20.
 */
public class Dial extends View {

    //渐变色环画笔，抗锯齿
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //渐变色环颜色
    private final int[] mColors = new int[]{0xffff0000, 0xffffff00, 0xff00ff00, 0xff00ffff,
            0xff0000ff, 0xffff00ff};

    public Dial(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int CENTER_X = wm.getDefaultDisplay().getWidth() / 2;
        int CENTER_Y = wm.getDefaultDisplay().getHeight() / 2;
        Shader s = new SweepGradient(0, 0, mColors, null);
        mPaint.setShader(s);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40);
        float r = CENTER_X - mPaint.getStrokeWidth() * 0.5f;
        canvas.save();
        canvas.translate(CENTER_X, CENTER_Y);
        canvas.rotate(150);
        canvas.drawOval(new RectF(-r, -r, r, r), mPaint);
        canvas.restore();
    }
}
