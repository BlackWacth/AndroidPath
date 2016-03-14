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

import com.hua.waveeffect.L;
import com.hua.waveeffect.R;
import com.hua.waveeffect.utils.UIUtils;

/**
 * Created by ZHONG WEI  HUA on 2016/3/4.
 */
public class WaveLoadingView extends View{
    public static final int WAVE_TRANS_SPEED = 4;
    public static final int WAVE_UP_SPEED = 1;

    private Paint mBoxPaint, mWavePaint;
    private Rect mBoxSrcRect, mBoxDestRect, mWaveSrcRect, mWaveDestRect;
    private int mWaveTransSpeed, mWaveUpSpeed;

    private int mCurrentWaveLeft, mCurrentWaveTop;

    private Bitmap mBox, mWave;

    private int mBoxWidth, mBoxHeight;
    private int mBoxLeft, mBoxTop;
    private int mWaveUpStart, mWaveUpEnd;
    private PaintFlagsDrawFilter mDrawFilter;
    private PorterDuffXfermode mPorterDuffXfermode;
    private int mTotalWidth, mTotalHeight;

    public WaveLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mDrawFilter = new PaintFlagsDrawFilter(Paint.ANTI_ALIAS_FLAG, Paint.DITHER_FLAG);

        mWaveTransSpeed = UIUtils.dip2px(context, WAVE_TRANS_SPEED);
        mWaveUpSpeed = UIUtils.dip2px(context, WAVE_UP_SPEED);

        mBoxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoxPaint.setAntiAlias(true);

        mWavePaint = new Paint(mBoxPaint);

        mBoxSrcRect = new Rect();
        mBoxDestRect = new Rect();
        mWaveSrcRect = new Rect();
        mWaveDestRect = new Rect();

        mBox = BitmapFactory.decodeResource(getResources(), R.mipmap.circle_500);
        mWave = BitmapFactory.decodeResource(getResources(), R.mipmap.wave_2000);
        mBoxWidth = mBox.getWidth();
        mBoxHeight = mBox.getHeight();
        L.i("mBoxHeight : " + mBoxHeight);

        updateWave();
    }

    private void updateWave() {
        new Thread(){
            @Override
            public void run() {
                while (true) {
                    mCurrentWaveLeft += mWaveTransSpeed;
                    mCurrentWaveTop -= mWaveUpSpeed;
                    if(mCurrentWaveLeft >= mWave.getWidth()) {
                        mCurrentWaveLeft = 0;
                    }
                    if(mCurrentWaveTop <= mWaveUpEnd) {
                        mCurrentWaveTop = mWaveUpStart;
                    }
                    try {
                        Thread.sleep(40);
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

        mBoxLeft = (w - mBoxWidth) / 2;
        mBoxTop = (h - mBoxHeight) / 2;

        mWaveUpStart = mBoxTop + mBoxHeight;
        mWaveUpEnd = mBoxTop;
        L.i("=======mWaveUpStart : " + mWaveUpStart);

        mCurrentWaveLeft = 0;
        mCurrentWaveTop = mWaveUpStart;
        L.i("=======mWaveUpEnd : " + mWaveUpEnd);


        mBoxSrcRect.set(0, 0, mBoxWidth, mBoxHeight);
        mBoxDestRect.set(mBoxLeft, mBoxTop, mBoxLeft + mBoxWidth, mWaveUpStart);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);
        canvas.drawColor(Color.TRANSPARENT);

        //将绘制的保存到新的图层
        int sc = canvas.saveLayer(0, 0, mTotalWidth, mTotalHeight, null,Canvas.ALL_SAVE_FLAG);
        mWaveSrcRect.set(mCurrentWaveLeft, 0, mCurrentWaveLeft + mBoxWidth, mBoxHeight);
        mWaveDestRect.set(mBoxLeft, mCurrentWaveTop, mBoxLeft + mBoxWidth, mWaveUpStart);
        canvas.drawBitmap(mWave, mWaveSrcRect, mWaveDestRect, mWavePaint);

        mBoxPaint.setXfermode(mPorterDuffXfermode);
        canvas.drawBitmap(mBox, mBoxSrcRect, mBoxDestRect, mBoxPaint);
        mBoxPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }
}
