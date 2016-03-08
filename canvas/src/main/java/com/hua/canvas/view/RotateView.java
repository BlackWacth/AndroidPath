package com.hua.canvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hua.canvas.L;

/**
 * Canvas 旋转
 * Created by ZHONG WEI  HUA on 2016/2/27.
 */
public class RotateView extends View{

    public static final int RADIUS = 150;
    public static final int LONG_LINE = 35;
    public static final int SHORT_LINE = 25;
    public static final int CIRCLE_LINE_WIDTH = 4;
    public static final int LINE_WIDTH = 4;

    private int mCircleLineWidth, mHalfCircleLineWidth;
    private int mLineWidth, mHalfLineWidth;
    private int circleX;
    private int circleY;
    private int mRadius;
    private int mLongLineHeight;
    private int mShortLineHeight;
    private int mLineLeft, mLineTop;
    private int mLineBottom;
    private int mFixLineHeight;

    private Paint mCirclePaint;
    private Paint mLinePaint;
    //给Canvas加上抗锯齿
    private DrawFilter mDrawFilter;

    public RotateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, RADIUS, getResources().getDisplayMetrics());
        mLongLineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LONG_LINE, getResources().getDisplayMetrics());
        mShortLineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SHORT_LINE, getResources().getDisplayMetrics());
        mCircleLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, CIRCLE_LINE_WIDTH, getResources().getDisplayMetrics());
        mHalfCircleLineWidth = mCircleLineWidth / 2;
        mLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LINE_WIDTH, getResources().getDisplayMetrics());
        mHalfLineWidth = mLineWidth / 2;
        mFixLineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStrokeWidth(mCircleLineWidth);
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setColor(Color.CYAN);
        mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        circleX = w / 2;
        circleY = h / 2;

        mRadius = w / 2 - mHalfCircleLineWidth;

        mLineLeft = w / 2 - mHalfLineWidth;
        mLineTop = h / 2 - w / 2 + mFixLineHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(mDrawFilter);
        super.onDraw(canvas);
        drawCircle(canvas);
        drawLines(canvas);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(circleX, circleY, mRadius, mCirclePaint);
    }

    private void drawLines(Canvas canvas) {
        for(int i = 0; i <= 360; i++) {
            if(i % 30 == 0){
                mLineBottom = mLineTop + mLongLineHeight;
                mLinePaint.setStrokeWidth(mLineWidth);
            }else {
                mLineBottom = mLineTop + mShortLineHeight;
                mLinePaint.setStrokeWidth(mHalfLineWidth);
            }

            if(i % 6 == 0) {
                canvas.save();
                canvas.rotate(i, circleX, circleY);
                canvas.drawLine(mLineLeft, mLineTop, mLineLeft, mLineBottom, mLinePaint);
                canvas.restore();
            }
        }
    }
}
