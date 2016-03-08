package com.hua.androidpath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hua.androidpath.view.PathView;

public class MainActivity extends AppCompatActivity {

    private PathView mPathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPathView = (PathView) findViewById(R.id.pathview);
        mPathView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathView.startPathAnim(1000);
            }
        });

    }
}
