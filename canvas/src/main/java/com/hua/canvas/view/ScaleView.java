package com.hua.canvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hua.canvas.L;
import com.hua.canvas.R;

/**
 * Canvas 缩放
 * Created by ZHONG WEI  HUA on 2016/2/27.
 */
public class ScaleView extends View{

    public static final int TARGET_WIDTH = 300;

    private int mTotalWidth, mTotalHeight;
    private int mHalfWidth, mHalfHeight;
    private int mDrawLeft, mDrawTop;
    private int mScaleX, mScaleY;
    private Paint mPaint;
    private int mTargetWidth;



    public ScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);

        mTargetWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TARGET_WIDTH, getResources().getDisplayMetrics());
        L.i("mTargetWidth = " + mTargetWidth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mHalfWidth = mTotalWidth / 2;
        mHalfHeight = mTotalHeight / 2;

        mScaleX = mHalfWidth;
        mScaleY = mHalfHeight;

        mDrawLeft = mScaleX - mTargetWidth / 2;
        mDrawTop = mScaleY - mTargetWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawSquare(canvas);
    }

    private void drawSquare(Canvas canvas) {
        canvas.save();
        for(int i = 0; i <= 100; i++) {
            canvas.drawRect(new Rect(mDrawLeft, mDrawTop, mDrawLeft + mTargetWidth, mDrawTop + mTargetWidth), mPaint);
            canvas.scale(0.95f, 0.95f, mScaleX, mScaleY);
        }
        canvas.restore();
    }

    private void testScale(Canvas canvas) {
        canvas.drawColor(getResources().getColor(R.color.colorPrimary));

        mPaint.setColor(Color.RED);
        canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);

        canvas.save();
        canvas.scale(0.5f, 0.5f);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);
        canvas.restore();

        canvas.scale(0.5f, 0.5f, 200, 200);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);
    }
}
