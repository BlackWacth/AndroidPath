package com.hua.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by ZHONG WEI  HUA on 2016/2/29.
 */
public class PathView extends View{

    private Path mPath, wPath, eoPath, iwPath, ieoPath;
    private Paint mPaint;
    private RectF wRect, eoRect, iwRect, ieoRect;



    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        mPath = new Path();
        wPath = new Path();
        eoPath = new Path();
        iwPath = new Path();
        ieoPath = new Path();

        wRect = new RectF();
        eoRect = new RectF();
        iwRect = new RectF();
        ieoRect = new RectF();

        wPath.addCircle(200, 200, 100, Path.Direction.CW);
        wPath.addRect(new RectF(200, 200, 300, 300), Path.Direction.CW);
        wPath.setFillType(Path.FillType.WINDING);
        wPath.computeBounds(wRect, true);

        eoPath.addCircle(500, 200, 100, Path.Direction.CW);
        eoPath.addRect(new RectF(500, 200, 600, 300), Path.Direction.CW);
        eoPath.setFillType(Path.FillType.EVEN_ODD);
        eoPath.computeBounds(eoRect, false);

        iwPath.addCircle(200, 500, 100, Path.Direction.CW);
        iwPath.addRect(new RectF(200, 500, 300, 600), Path.Direction.CW);
        iwPath.setFillType(Path.FillType.INVERSE_WINDING);
        iwPath.computeBounds(iwRect, false);

        ieoPath.addCircle(500, 500, 100, Path.Direction.CW);
        ieoPath.addRect(new RectF(500, 500, 600, 600), Path.Direction.CW);
        ieoPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        ieoPath.computeBounds(ieoRect, false);

        Log.i("hzw", "wRect = " + wRect);
        Log.i("hzw", "eoRect = " + eoRect);
        Log.i("hzw", "iwRect = " + iwRect);
        Log.i("hzw", "ieoRect = " + ieoRect);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);

        canvas.drawPath(wPath, mPaint);
        canvas.drawPath(eoPath, mPaint);
        canvas.drawPath(iwPath, mPaint);
        canvas.drawPath(ieoPath, mPaint);
    }
}
