package com.hua.canvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.hua.canvas.R;

/**
 * Canvas 画布错切
 * Created by ZHONG WEI  HUA on 2016/2/27.
 */
public class SkewView extends View {

    private Paint mPaint;

    public SkewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.CYAN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);
        canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);

        canvas.skew(0.75f, 0.75f);
        mPaint.setColor(getResources().getColor(R.color.rectc));
        canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);
    }
}
