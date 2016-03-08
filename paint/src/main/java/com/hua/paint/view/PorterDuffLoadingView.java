package com.hua.paint.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hua.paint.R;

/**
 * Created by ZHONG WEI  HUA on 2016/3/4.
 */
public class PorterDuffLoadingView extends View{

    private Resources mResources;
    private Bitmap mBitmap;
    private Paint mPaint;

    private int mTotalWidth, mTotalHeight;
    private int mBitmapWidth, mBitmapHeight;
    private Rect mSrcRect, mDestRect;
    private PorterDuffXfermode mXfermode;

    private Rect mDynamicRect;
    private int mCurrentTop;
    private int mStart, mEnd;

    public PorterDuffLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mResources = getResources();
        initPaint();
        initBitmap();
        initXfermode();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
    }

    private void initBitmap() {
        mBitmap = BitmapFactory.decodeResource(mResources, R.mipmap.ga_studio);
        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();
    }

    private void initXfermode() {
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalHeight = h;
        mTotalWidth = w;

        mSrcRect = new Rect(0, 0, mBitmapWidth, mBitmapHeight);

        int left = (int) TypedValue.complexToDimension(20, mResources.getDisplayMetrics());
        mDestRect = new Rect(left, left,left + mBitmapWidth, left + mBitmapHeight);

        mStart = left + mBitmapHeight;
        mCurrentTop = mStart;
        mEnd = left;
        mDynamicRect = new Rect(left, mStart, left + mBitmapWidth, left + mBitmapHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveLayerCount = canvas.saveLayer(0, 0, mTotalWidth, mTotalHeight, mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mPaint);
        mPaint.setXfermode(mXfermode);
        canvas.drawRect(mDynamicRect, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(saveLayerCount);

        mCurrentTop -= 1;
        if(mCurrentTop <= mEnd) {
            mCurrentTop = mStart;
        }
        mDynamicRect.top = mCurrentTop;
        postInvalidate();
    }
}

