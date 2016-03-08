package com.hua.waveeffect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mSinBtn, mDuffBtn, mLoadingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSinBtn = (Button) findViewById(R.id.wave_sin);
        mDuffBtn = (Button) findViewById(R.id.wave_porter_duff);
        mLoadingBtn = (Button) findViewById(R.id.wave_loader);
        mSinBtn.setOnClickListener(this);
        mDuffBtn.setOnClickListener(this);
        mLoadingBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wave_sin:
                mStartActivity(SinWaveActivity.class);
                break;

            case R.id.wave_porter_duff:
                mStartActivity(PorterDuffWaveActivity.class);
                break;

            case R.id.wave_loader:
                mStartActivity(WaveLoadingActivity.class);
                break;
        }
    }

    private void mStartActivity(Class<?> cls) {
        startActivity(new Intent(MainActivity.this, cls));
    }
}
