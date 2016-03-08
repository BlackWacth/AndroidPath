package com.hua.canvas.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hua.canvas.R;
import com.hua.canvas.view.DrawBitmapView;

public class DrawBitmapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_bitmap);

        final DrawBitmapView dbv = (DrawBitmapView) findViewById(R.id.draw_bitmap_view);
        dbv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dbv.startTranslate();
                dbv.startScale(1000);
            }
        });
    }
}
