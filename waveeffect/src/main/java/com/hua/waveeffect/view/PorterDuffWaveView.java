package com.hua.waveeffect.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.hua.waveeffect.R;
import com.hua.waveeffect.utils.UIUtils;

/**
 * Created by ZHONG WEI  HUA on 2016/3/4.
 */
public class PorterDuffWaveView extends View{

    private static final int WAVE_TRANS_SPEED = 4;
    private static final int WAVE_UP_SPEED = 1;

    private Paint mBitmapPaint, mPicPaint;
    private int mTotalWidth, mTotalHeight;
    private int mCenterX, mCenterY;
    private int mCurrentTop;
    private int mSpeed, mUpSpeed;

    private Bitmap mSrcBitmap;
    private Rect mSrcRect, mDestRect;

    private PorterDuffXfermode mPorterDuffXfermode;
    private Bitmap mMaskBitmap;
    private Rect mMaskSrcRect, mMaskDestRect;
    private PaintFlagsDrawFilter mDrawFilter;

    private int mCurrentPosition;

    public PorterDuffWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mSpeed = UIUtils.dip2px(context, WAVE_TRANS_SPEED);
        mUpSpeed = UIUtils.dip2px(context, WAVE_UP_SPEED);
        mDrawFilter = new PaintFlagsDrawFilter(Paint.ANTI_ALIAS_FLAG, Paint.DITHER_FLAG);

        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.wave_2000);
        mMaskBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.circle_500);

        mBitmapPaint = new Paint();
        mBitmapPaint.setDither(true);
        mBitmapPaint.setFilterBitmap(true);

        mPicPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPicPaint.setDither(true);
        mPicPaint.setColor(Color.RED);

        new Thread(){
            @Override
            public void run() {
                while (true){
                    mCurrentPosition += mSpeed;
                    if(mCurrentPosition >= mSrcBitmap.getWidth()){
                        mCurrentPosition = 0;
                    }

                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    postInvalidate();
                }
            }
        }.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mCenterX = mTotalWidth / 2;
        mCenterY = mTotalHeight / 2;

        int maskWidth = mMaskBitmap.getWidth();
        int maskHeight = mMaskBitmap.getHeight();

        mCurrentTop = maskHeight;
        mSrcRect = new Rect();
        mDestRect = new Rect(0, mCurrentTop, maskWidth, mCurrentTop);

        mMaskSrcRect = new Rect(0, 0, maskWidth, maskHeight);
        mMaskDestRect = new Rect(0, 0, maskWidth, maskHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);
        canvas.drawColor(Color.TRANSPARENT);

        //蒋绘制的保存到新的图层
        int sc = canvas.saveLayer(0, 0, mTotalWidth, mTotalHeight, null,Canvas.ALL_SAVE_FLAG);
        //设置要绘制的波纹部分
        mSrcRect.set(mCurrentPosition, 0, mCurrentPosition + mCenterX, mTotalHeight);
        //绘制波纹
        canvas.drawBitmap(mSrcBitmap, mSrcRect, mDestRect, mBitmapPaint);

        mBitmapPaint.setXfermode(mPorterDuffXfermode);
        canvas.drawBitmap(mMaskBitmap, mMaskSrcRect, mMaskDestRect, mBitmapPaint);
        mBitmapPaint.setXfermode(null);

        mCurrentTop -= mUpSpeed;
        mDestRect.top = mCurrentTop;
        if(mCurrentTop <= -100) {
            mCurrentTop = mMaskBitmap.getHeight();
        }
        canvas.restoreToCount(sc);
    }
}
