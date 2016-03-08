package com.hua.paint.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ZHONG WEI  HUA on 2016/3/1.
 */
public class PathEffectView extends View{

    private float mPhase;
    private PathEffect[] mEffects = new PathEffect[7];
    private int[] mColors;
    private Paint mPaint;
    private Path mPath;

    public PathEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mPhase = 10;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);

        mPath = new Path();
        mPath.moveTo(0, 0);
        for (int i = 1; i <=15; i++) {
            mPath.lineTo(i * 30, (float)Math.random() * 100);
        }

        mColors = new int[]{
                Color.BLACK,
                Color.BLUE ,
                Color.CYAN ,
                Color.GREEN ,
                Color.MAGENTA ,
                Color.RED ,
                Color.YELLOW ,
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        mEffects[0] = null;
        mEffects[1] = new CornerPathEffect(10);
        mEffects[2] = new DiscretePathEffect(1.0f, 1.0f);
        mEffects[3] = new DashPathEffect(new float[] {
                20, 10, 5, 10
        }, mPhase);

        Path path = new Path();
        path.addRect(0, 0, 8, 8, Path.Direction.CW);
        mEffects[4] = new PathDashPathEffect(path, 12, mPhase, PathDashPathEffect.Style.TRANSLATE);
        mEffects[5] = new ComposePathEffect(mEffects[2], mEffects[4]);
        mEffects[6] = new SumPathEffect(mEffects[4], mEffects[3]);

        canvas.translate(50, 50);

        for(int i = 0; i < mEffects.length; i++) {
            mPaint.setPathEffect(mEffects[i]);
            mPaint.setColor(mColors[i]);
            canvas.drawPath(mPath, mPaint);
            canvas.translate(0, 150);
        }
        mPhase += 1;

        invalidate();
    }
}
