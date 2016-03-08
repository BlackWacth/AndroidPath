package com.hua.canvas.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hua.canvas.L;

/**
 * Canvas 平移
 * Created by ZHONG WEI  HUA on 2016/2/26.
 */
public class TranslateView extends View{

    //刻度尺高度
    private static final int DIVIDING_RULE_HEIGHT = 70;

    //距离左右
    private static final int DIVIDING_RULE_MARGIN_LEFT_RIGHT = 10;

    //第一条线距离边框距离
    private static final int FRIST_LING_MARGIN = 5;

    //打算绘制的厘米数
    private static final int DEFAULT_COUNT = 9;

    private static final int NUM_TOP = 20;

    private int mDividRuleHeight;

    private int mHalfRuleHeight;

    private int mDividRuleLeftMargin;

    private int mFristLineMargin;

    private Paint mOuterPaint, mLinePaint, mNumPaint;

    private Resources mResources;

    private Rect mOutRect;

    private int mLineInterval;

    private int mTotalWidth, mTotalHeight;
    private int mHalfWidth, mHalfHeight;

    private int mRuleBottom;

    private int mLineStartX;

    private int mMaxLineTop;

    private int mMidLineTop;

    private int mMinLineTop;

    private int mNumTop;

    private int mRuleNumTop;


    public TranslateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mResources = context.getResources();
        mDividRuleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DIVIDING_RULE_HEIGHT, mResources.getDisplayMetrics());
        mHalfRuleHeight = mDividRuleHeight / 2;
        L.i("mDividRuleHeight = " + mDividRuleHeight);
        L.i("mHalfRuleHeight = " + mHalfRuleHeight);

        mDividRuleLeftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DIVIDING_RULE_MARGIN_LEFT_RIGHT, mResources.getDisplayMetrics());
        mFristLineMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, FRIST_LING_MARGIN, mResources.getDisplayMetrics());
        mNumTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, NUM_TOP, mResources.getDisplayMetrics());
        L.i("mDividRuleLeftMargin = " + mDividRuleLeftMargin);
        L.i("mFristLineMargin = " + mFristLineMargin);
        L.i("mNumTop = " + mNumTop);

        mOuterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOuterPaint.setColor(Color.BLACK);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeWidth(1);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mLinePaint.setStrokeWidth(1);

        mNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNumPaint.setColor(Color.BLACK);
        mNumPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mNumPaint.setStrokeWidth(1);
        mNumPaint.setTextSize(40);
        mNumPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawOuter(canvas);

        drawLines(canvas);

        drawNumbers(canvas);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mHalfHeight = h / 2;
        mHalfWidth = w / 2;

        L.i("mTotalWidth = " + mTotalWidth);
        L.i("mTotalHeight = " + mTotalHeight);
        L.i("mHalfWidth = " + mHalfWidth);
        L.i("mHalfHeight = " + mHalfHeight);

        int top = mHalfHeight - mHalfRuleHeight;
        mRuleBottom = top + mDividRuleHeight;
        mOutRect = new Rect(mDividRuleLeftMargin, top, mTotalWidth - mDividRuleLeftMargin, mRuleBottom);

        mLineInterval = (mTotalWidth - mDividRuleLeftMargin * 2 - mFristLineMargin * 2) / (DEFAULT_COUNT * 10 -1);

        mLineStartX = mDividRuleLeftMargin + mFristLineMargin;

        mMaxLineTop = mRuleBottom - mDividRuleHeight / 2;
        mMidLineTop = mRuleBottom - mDividRuleHeight / 3;
        mMinLineTop = mRuleBottom - mDividRuleHeight / 4;

        mRuleNumTop = top + mNumTop;

    }

    private void drawNumbers(Canvas canvas) {
        canvas.save();
        canvas.translate(mLineStartX, 0);
        for(int i = 0; i <= DEFAULT_COUNT; i ++) {
            canvas.drawText(i+"", 0, mRuleNumTop, mNumPaint);
            canvas.translate(mLineInterval * 10, 0);
        }
        canvas.restore();
    }

    private void drawLines(Canvas canvas) {
        canvas.save();
        canvas.translate(mLineStartX, 0);
        int top = mMaxLineTop;
        for(int i = 0; i <= DEFAULT_COUNT  * 10; i++) {
            if(i % 10 == 0) {
                top = mMaxLineTop;
            }else if(i % 5 == 0){
                top = mMidLineTop;
            }else {
                top = mMinLineTop;
            }

            canvas.drawLine(0, mRuleBottom, 0, top, mLinePaint);
            canvas.translate(mLineInterval, 0);
        }
        canvas.restore();
    }

    private void drawOuter(Canvas canvas) {
        canvas.drawRect(mOutRect, mOuterPaint);
    }
}
