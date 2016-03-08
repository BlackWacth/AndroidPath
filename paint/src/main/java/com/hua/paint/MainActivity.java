package com.hua.paint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.hua.paint.view.PaintView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private PaintView mPaintView;
    private SeekBar mAlpha, mRed, mGreen, mBlue;
    private float mAlphaValue, mRedValue, mGreenValue, mBlueValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPaintView = (PaintView) findViewById(R.id.paintview);
        mAlpha = (SeekBar) findViewById(R.id.sb_alpha);
        mRed = (SeekBar) findViewById(R.id.sb_red);
        mGreen = (SeekBar) findViewById(R.id.sb_green);
        mBlue = (SeekBar) findViewById(R.id.sb_blue);

        mAlpha.setOnSeekBarChangeListener(this);
        mRed.setOnSeekBarChangeListener(this);
        mGreen.setOnSeekBarChangeListener(this);
        mBlue.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        float filter = (float)progress / 100;
        if(seekBar == mAlpha) {
            Log.i("hzw", "alpha --> progress = " + progress);
            mAlphaValue = filter;
        }else if(seekBar == mRed) {
            Log.i("hzw", "reb ==> progress = " + progress);
            mRedValue = filter;
        }else if(seekBar == mGreen) {
            Log.i("hzw", "green ++> progress = " + progress);
            mGreenValue = filter;
        }else if(seekBar == mBlue) {
            Log.i("hzw", "blue >>> progress = " + progress);
            mBlueValue = filter;
        }
        mPaintView.setArgb(mAlphaValue, mRedValue, mGreenValue, mBlueValue);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
