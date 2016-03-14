package com.hua.androidpath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hua.androidpath.view.HeartView;
import com.hua.androidpath.view.PathView;

public class MainActivity extends AppCompatActivity {

    private HeartView mHeartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHeartView = (HeartView) findViewById(R.id.pathview);
        mHeartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHeartView.startAnimation(2000);
            }
        });

    }
}
