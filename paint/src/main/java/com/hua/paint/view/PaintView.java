package com.hua.paint.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.hua.paint.R;

/**
 * Created by ZHONG WEI  HUA on 2016/3/1.
 */
public class PaintView extends View{

    private float mRedFilter;
    private float mGreenFilter;
    private float mBlueFilter;
    private float mAlphaFilter;
    private ColorMatrix mColorMatrix;
    private Bitmap mBitmap;


    private Paint mPaint;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorMatrix = new ColorMatrix((new float[] {
                -1, 0, 0, 1, 0,
                0, -1, 0, 1, 0,
                0, 0, -1, 1, 0,
                0, 0, 0, 1, 0
        }));
        mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img05);
    }

    public void setArgb(float alaph, float red, float green, float blue) {
        mRedFilter = red;
        mGreenFilter = green;
        mBlueFilter = blue;
        mAlphaFilter = alaph;
        mColorMatrix = new ColorMatrix((new float[] {
            mRedFilter, 0, 0, 0, 0,
            0, mGreenFilter, 0, 0, 0,
            0, 0, mBlueFilter, 0, 0,
            0, 0, 0, mAlphaFilter, 0
        }));
        mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap,200, 50, mPaint);
    }
}
