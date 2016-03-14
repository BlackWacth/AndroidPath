package com.hua.androidpath.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by ZHONG WEI  HUA on 2016/3/14.
 */
public class HeartView extends View {

    private static final int[] start_point= new int[]{400, 400};
    private static final int[] end_point= new int[]{400, 700};
    private static final int[] left_point= new int[]{100, 500, 200, 200};
    private static final int[] right_point= new int[]{600, 200, 700, 500};

    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private float pathLength;
    private float[] mCurrentPoint = new float[2];

    public HeartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.RED);

        mPath = new Path();
        mPath.moveTo(start_point[0], start_point[1]);
        mPath.cubicTo(right_point[0], right_point[1], right_point[2], right_point[3], end_point[0], end_point[1]);
        mPath.cubicTo(left_point[0], left_point[1], left_point[2], left_point[3], start_point[0], start_point[1]);

        mCurrentPoint[0] = start_point[0];
        mCurrentPoint[1] = start_point[1];

        mPathMeasure = new PathMeasure(mPath, true);
        pathLength = mPathMeasure.getLength();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(mCurrentPoint[0], mCurrentPoint[1], 10, mPaint);
    }

    public void startAnimation(int duration) {
        final ValueAnimator anim = ValueAnimator.ofFloat(0, pathLength);
        anim.setDuration(duration);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float distance = (float) animation.getAnimatedValue();
                mPathMeasure.getPosTan(distance, mCurrentPoint, null);
                postInvalidate();
            }
        });
        anim.start();
    }

}
