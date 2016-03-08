package com.hua.canvas.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.hua.canvas.L;
import com.hua.canvas.R;

/**
 * Created by ZHONG WEI  HUA on 2016/3/2.
 */
public class DrawBitmapView extends View{

    private Resources mResources;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Rect mScrRect, mDesRect;

    private int mTotalWidth, mTotalHeight;
    private int bw, bh;

    private int mStartLeft, mStartTop, mToLeft, mToTop;

    private int left, top;


    public DrawBitmapView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mResources = getResources();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);

        mBitmap = BitmapFactory.decodeResource(mResources, R.mipmap.img05);
        bw = mBitmap.getWidth();
        bh = mBitmap.getHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mScrRect, mDesRect, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalHeight = h;
        mTotalWidth = w;

        left = mTotalWidth / 2 - bw / 2;
        top = mTotalHeight / 2 - bh / 2;
        L.i("left = " + left);
        L.i("top = " + top);

        mScrRect = new Rect(0, 0, bw, bh);
        mDesRect = new Rect(left, top, left + bw, top + bh);
//        mDesRect = new Rect(0, 0, bw, bh);
    }

    public void startTranslate(){
        startTranslate(0, 0, 200, 200, 1000);
    }

    public void startTranslate(int startLeft, int startTop, int toLeft, int toTop, int duration) {
        mStartLeft =startLeft;
        mStartTop = startTop;
        mToLeft = toLeft;
        mToTop = toTop;

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = (float) animation.getAnimatedValue();
                int currentLeft = (int) ((mToLeft - mStartLeft) * fraction + mStartLeft);
                int currentTop = (int) ((mToTop - mStartTop) * fraction + mStartTop);

                if (mDesRect == null) {
                    mDesRect = new Rect(currentLeft, currentTop, currentLeft + bw, currentTop + bh);
                }

                mDesRect.left = currentLeft;
                mDesRect.top = currentTop;
                mDesRect.right = currentLeft + bw;
                mDesRect.bottom = currentTop + bh;

                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    public void startScale(long duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = (float) animation.getAnimatedValue();
                int currentLeft = (int) ((mToLeft - mStartLeft) * fraction + mStartLeft);
                int currentTop = (int) ((mToTop - mStartTop) * fraction + mStartTop);

                if (mDesRect == null) {
                    mDesRect = new Rect(left, top, left + bw, top + bh);
                }

                mDesRect.right = (int) (left + fraction * bw);

                postInvalidate();
            }
        });
        valueAnimator.start();
    }

}
