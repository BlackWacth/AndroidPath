package com.hua.path;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by ZHONG WEI  HUA on 2016/2/29.
 */
public class PathView02 extends View{

    private Paint mPaint;
    private Path mPath;
    private RectF mRectF;
    private PathMeasure mPathMeasure;
    private float[] mCurrentPoint = new float[2];

    public PathView02(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.RED);

//        mPath = new Path();
//        mPath.moveTo(100, 100);
//        mPath.lineTo(200, 200);

//        mPath.moveTo(100, 500);
//        mPath.rMoveTo(50, 50);
//        mPath.addCircle(100, 100, 80, Path.Direction.CW);

//        mPath.lineTo(100, 300); //以左上角为坐标原点，建立坐标系
//        mPath.rLineTo(300, 300); //以上一个点为坐标原点，建立坐标系。
//        mPath.rLineTo(300, 100);
//        mPath.rLineTo(100, 100);

//        mPath.lineTo(200, 200);
//        mPath.rMoveTo(100, 100);
//        mPath.lineTo(400, 400);
//
//        mRectF = new RectF(0, 400, 800, 800);
//        mPath.arcTo(mRectF, 0, 200, true);//三点钟方向为0度，顺时针旋转为正，即6点钟方向为90度，当其实角度为0，划过角度为360，怎么绘画结果为空白
//        mPath.close();//闭合新增线的起点到终点

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

//        canvas.drawPath(mPath, mPaint);
        drawHeart(canvas);

    }

    private void drawHeart(Canvas canvas) {
        mPath = new Path();
        mPath.moveTo(400, 400);
        mPath.cubicTo(600, 200, 700, 500, 400, 700);
        mPath.cubicTo(100, 500, 200, 200, 400, 400);
        canvas.drawPath(mPath, mPaint);
    }
}
