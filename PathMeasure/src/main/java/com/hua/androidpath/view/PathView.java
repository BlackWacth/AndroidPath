package com.hua.androidpath.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by ZHONG WEI  HUA on 2016/2/26.
 */
public class PathView extends View{

    private static final String TAG = "PathView";

    private static final int PATH_WIDTH = 2;

    //起始点
    private static final int[] START_POINT = new int[]{300, 270};

    //底部端点
    private static final int[] BOTTOM_POINT = new int[]{300, 400};

    //左侧控制点
    private static final int[] LEFT_CONTROL_POINT = new int[]{450, 200};

    //右侧控制点
    private static final int[] RIGHT_CONTROL_POINT = new int[]{150, 200};

    private PathMeasure mPathMeasure;

    private Paint mPaint;

    private Path mPath;

    private float[] mCurrentPoint = new float[2];

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(PATH_WIDTH);
        mPaint.setColor(Color.RED);

        mPath = new Path();
        mPath.moveTo(START_POINT[0], START_POINT[1]);
        mPath.quadTo(RIGHT_CONTROL_POINT[0], RIGHT_CONTROL_POINT[1], BOTTOM_POINT[0], BOTTOM_POINT[1]);
        mPath.quadTo(LEFT_CONTROL_POINT[0], LEFT_CONTROL_POINT[1], START_POINT[0], START_POINT[1]);

        mPathMeasure = new PathMeasure(mPath, true);
        mCurrentPoint = new float[]{300, 270};
    }

    public void startPathAnim(long duration) {
        float length = mPathMeasure.getLength();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, length);
        Log.i(TAG, "length = " + length);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                Log.i(TAG, "value = " + value);
                mPathMeasure.getPosTan(value, mCurrentPoint, null);
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawPath(mPath, mPaint);

        canvas.drawCircle(RIGHT_CONTROL_POINT[0], RIGHT_CONTROL_POINT[1], 5, mPaint);
        canvas.drawCircle(LEFT_CONTROL_POINT[0], LEFT_CONTROL_POINT[1], 5, mPaint);

        canvas.drawCircle(mCurrentPoint[0], mCurrentPoint[1], 10, mPaint);
    }
}
