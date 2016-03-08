package com.hua.paint.view;

import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hua.paint.R;

/**
 * Created by ZHONG WEI  HUA on 2016/3/3.
 */
public class PorterDuffXfermodeView extends View {

    private Paint mPaint;
    private int mTotalWidth, mTotalHeight;

    private Bitmap mBottomBitmap, mTopBitmap;
    private int bw, bh;
    private Rect mBottomSrcRect, mBottomDestRect;
    private Rect mTopSrcRect, mTopDestRect;

    private Xfermode mPorterDuffXfermode;
    private PorterDuff.Mode mPorterDuffMode;



    public PorterDuffXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initBitmap();
        initPaint();
        initXfermode();
    }

    public void setXfermode(PorterDuff.Mode mode){
        if(mode != null) {
            mPorterDuffXfermode = new PorterDuffXfermode(mode);
        }else{
            mPorterDuffXfermode = null;
        }
        postInvalidate();
    }

    private void initXfermode() {
        mPorterDuffMode = PorterDuff.Mode.CLEAR;
//        mPorterDuffXfermode = new PorterDuffXfermode(mPorterDuffMode);
        mPorterDuffXfermode = null;
    }

    private void initBitmap() {
        mBottomBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.blue);
        mTopBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.red);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalHeight = h;
        mTotalWidth = w;

        int halfHeight = h / 2;

        mBottomSrcRect = new Rect(0, 0, mBottomBitmap.getWidth(), mBottomBitmap.getHeight());
        mBottomDestRect = new Rect(0, 0, mTotalWidth, halfHeight);
//        mBottomDestRect = new Rect(0, 0, mBottomBitmap.getWidth(), mBottomBitmap.getHeight());

        mTopSrcRect = new Rect(0, 0, mTopBitmap.getWidth(), mTopBitmap.getHeight());
        mTopDestRect = new Rect(0, 0, mTotalWidth, mTotalHeight);
//        mTopDestRect = new Rect(0, 0, mTopBitmap.getWidth(), mTopBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        int saveCount = canvas.saveLayer(0, 0, mTotalWidth, mTotalHeight, mPaint, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mBottomBitmap, mBottomSrcRect, mBottomDestRect, mPaint);

        mPaint.setXfermode(mPorterDuffXfermode);
        canvas.drawBitmap(mTopBitmap, mTopSrcRect, mTopDestRect, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(saveCount);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
