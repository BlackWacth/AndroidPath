package com.hua.waveeffect.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;

import com.hua.waveeffect.utils.UIUtils;

import java.io.PipedReader;

/**
 * Created by ZHONG WEI  HUA on 2016/3/4.
 */
public class SinWaveView extends View{

    //水波颜色
    private static final int WAVE_PAINT_COLOR = 0x880000AA;

    //y = Asin(wx+b)+h
    private static final float STRETCH_FACTOR_A = 20;
    private static final int OFFSET_Y = 0;
    private static final int TRANSLATE_X_SPEED_ONE = 7;
    private static final int TRANSLATE_X_SPEED_TWO = 5;
    private float mCycleFactorW;

    private int mTotalWidth, mTotalHeight;
    private float[] mYPositions, mResetOneYPositions, mResetTwoYPositions;
    private int mXOffsetSpeedOne, mXOffsetSpeedTwo;
    private int mXOneOffset, mXTwoOffset;

    private Paint mPaint;
    private DrawFilter mDrawFilter;

    public SinWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(WAVE_PAINT_COLOR);

        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);

        mXOffsetSpeedOne = UIUtils.dip2px(context, TRANSLATE_X_SPEED_ONE);
        mXOffsetSpeedTwo = UIUtils.dip2px(context, TRANSLATE_X_SPEED_TWO);
    }

    private void resetPositionY() {
        int yOneInterval = mYPositions.length - mXOneOffset;
        System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
        System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);

        int yTwoInterval = mYPositions.length - mXTwoOffset;
        System.arraycopy(mYPositions, mXTwoOffset, mResetTwoYPositions, 0, yTwoInterval);
        System.arraycopy(mYPositions, 0, mResetTwoYPositions, yTwoInterval, mXTwoOffset);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;

        mYPositions = new float[mTotalWidth];
        mResetOneYPositions = new float[mTotalWidth];
        mResetTwoYPositions = new float[mTotalWidth];

        mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);

        for(int i = 0; i < mTotalWidth; i++) {
            mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);
        resetPositionY();
        for(int i = 0; i < mTotalWidth; i++) {
            canvas.drawLine(i, mTotalHeight - mResetOneYPositions[i] - 400, i, mTotalHeight, mPaint);
            canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - 400, i, mTotalHeight, mPaint);
        }

        mXOneOffset += mXOffsetSpeedOne;
        mXTwoOffset += mXOffsetSpeedTwo;
        if(mXOneOffset > mTotalWidth) {
            mXOneOffset = 0;
        }
        if(mXTwoOffset > mTotalWidth) {
            mXTwoOffset = 0;
        }
        postInvalidate();
    }
}
