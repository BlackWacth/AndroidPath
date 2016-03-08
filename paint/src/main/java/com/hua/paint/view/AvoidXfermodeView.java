package com.hua.paint.view;

import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.hua.paint.R;

/**
 * Created by ZHONG WEI  HUA on 2016/3/3.
 */
public class AvoidXfermodeView extends View {

    private Paint mBitmapPaint, mAvoidPaint;
    private int mTotalWidth, mTotalHeight;

    private Bitmap mBitmap;
    private int bw, bh;
    private Rect mOriginSrcRect, mOriginDestRect;
    private Rect mAvoidSrcRect, mAvoidDestRect;

    private AvoidXfermode mAvoidXfermode;

    public AvoidXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mAvoidXfermode = new AvoidXfermode(Color.BLUE, 150, AvoidXfermode.Mode.TARGET);
        initBitmap();
        initPaint();
    }

    private void initBitmap() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bluelogo);
        bw = mBitmap.getWidth();
        bh = mBitmap.getHeight();
    }

    private void initPaint() {
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);  //去锯齿
        mBitmapPaint.setDither(true);  //防抖动
        mBitmapPaint.setFilterBitmap(true); //图片过滤

        mAvoidPaint = new Paint(mBitmapPaint);
        mAvoidPaint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalHeight = h;
        mTotalWidth = w;
        mOriginSrcRect = new Rect(0, 0, bw, bh);
        int left = (mTotalWidth - bw) / 2;
        mOriginDestRect = new Rect(left, 0, left + bw, bh);

        mAvoidSrcRect = new Rect(mOriginSrcRect);
        int distance = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        mAvoidDestRect = new Rect(left, distance + bh, left + bw, distance + bh * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mOriginSrcRect, mOriginDestRect, mBitmapPaint);

        canvas.drawBitmap(mBitmap, mAvoidSrcRect, mAvoidDestRect, mAvoidPaint);
        mAvoidPaint.setXfermode(mAvoidXfermode);
        canvas.drawRect(mAvoidDestRect, mAvoidPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
