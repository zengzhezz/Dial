package com.example.zz.dial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
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
    private Paint mPaintMiddleCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintInnerCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintGap1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintGap2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final String[] text = {"950", "极好", "700", "优秀", "650", "良好", "600", "中等", "550", "较差", "350",
            "很差", "150"};

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
        //设置画笔的空心
        mPaint.setStyle(Paint.Style.STROKE);
        //设置画笔的空心线宽
        mPaint.setStrokeWidth(40);
        mPaintMiddleCircle.setColor(Color.GRAY);
        mPaintInnerCircle.setColor(Color.GRAY);
        mPaintGap1.setColor(Color.WHITE);
        mPaintGap2.setColor(Color.WHITE);
        mPaintBg.setColor(Color.WHITE);
        mPaintText.setColor(Color.GRAY);
        mPaintMiddleCircle.setStrokeWidth(4);
        mPaintInnerCircle.setStrokeWidth(4);
        mPaintGap1.setStrokeWidth(2);
        mPaintGap2.setStrokeWidth(4);
        mPaintText.setTextSize(32);
        mPaintMiddleCircle.setStyle(Paint.Style.STROKE);
        mPaintInnerCircle.setStyle(Paint.Style.STROKE);
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
        mPaintInnerCircle.setPathEffect(effects);
        float r = CENTER_X - mPaint.getStrokeWidth() * 1.0f;
        //保存原来的坐标系
        canvas.save();
        canvas.translate(CENTER_X, CENTER_X);
        canvas.drawCircle(0, 0, CENTER_X * 5 / 8, mPaintInnerCircle);
        canvas.drawCircle(0, 0, CENTER_X * 3 / 4, mPaintMiddleCircle);
        //rotate()参数为正则顺时针，否则逆时针
        canvas.rotate(150);
        //画椭圆
        canvas.drawOval(new RectF(-r, -r, r, r), mPaint);
        //返回原来的坐标系
        canvas.restore();
        int a = (int) (2 * CENTER_X - mPaint.getStrokeWidth() * 1.5);
        for (int i = 0; i < 60; i++) {
            canvas.save();
            canvas.rotate(30 - 4 * i, CENTER_X, CENTER_X);
            if (i % 10 == 0) {
                canvas.drawLine(a, CENTER_X, a + mPaint.getStrokeWidth(), CENTER_X, mPaintGap2);
            } else {
                canvas.drawLine(a, CENTER_X, a + mPaint.getStrokeWidth(), CENTER_X, mPaintGap1);
            }
            canvas.restore();
        }
        int width = MeasureSpec.getSize((int) (CENTER_X * 2 - mPaint.getStrokeWidth()));
        int height = (int) ((Math.tan(Math.PI / 6) + 1) * width / 2);
        Path path = new Path();
        path.moveTo(CENTER_X, CENTER_X);
        path.lineTo(mPaint.getStrokeWidth(), height);
        path.lineTo(mPaint.getStrokeWidth(), CENTER_X * 2);
        path.lineTo(CENTER_X * 2 - mPaint.getStrokeWidth() / 2, CENTER_X * 2);
        path.lineTo(CENTER_X * 2 - mPaint.getStrokeWidth() / 2, height);
        path.lineTo(CENTER_X, CENTER_X);
        path.close();
        canvas.drawPath(path, mPaintBg);
        for (int i = 0; i <= 12; i++) {
            canvas.save();
            canvas.rotate(120 - 20 * i, CENTER_X, CENTER_X);
            canvas.drawText(text[i], CENTER_X - 20, CENTER_X * 3 / 16, mPaintText);
            canvas.restore();
        }
    }
}
