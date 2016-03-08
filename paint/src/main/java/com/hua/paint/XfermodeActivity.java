package com.hua.paint;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.hua.paint.view.PorterDuffXfermodeView;

public class XfermodeActivity extends AppCompatActivity {

    private PorterDuffXfermodeView duffview;
    private TextView mModeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfermode);
        duffview = (PorterDuffXfermodeView) findViewById(R.id.duff_view);
        mModeText = (TextView) findViewById(R.id.mode_text);
        mModeText.setText("NULL");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_xfermode, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PorterDuff.Mode mode = null;
        switch (item.getItemId()) {
            case R.id.action_mode_null:
                mode = null;
                break;

            case R.id.action_mode_clear:
                mode = PorterDuff.Mode.CLEAR;
                break;

            case R.id.action_mode_src:
                mode = PorterDuff.Mode.SRC;
                break;

            case R.id.action_mode_dst:
                mode = PorterDuff.Mode.DST;
                break;

            case R.id.action_mode_src_over:
                mode = PorterDuff.Mode.SRC_OVER;
                break;

            case R.id.action_mode_dst_over:
                mode = PorterDuff.Mode.DST_OVER;
                break;

            case R.id.action_mode_src_in:
                mode = PorterDuff.Mode.SRC_IN;
                break;

            case R.id.action_mode_dst_in:
                mode = PorterDuff.Mode.DST_IN;
                break;

            case R.id.action_mode_src_out:
                mode = PorterDuff.Mode.SRC_OUT;
                break;

            case R.id.action_mode_dst_out:
                mode = PorterDuff.Mode.DST_OUT;
                break;

            case R.id.action_mode_src_atop:
                mode = PorterDuff.Mode.SRC_ATOP;
                break;

            case R.id.action_mode_dst_atop:
                mode = PorterDuff.Mode.DST_ATOP;
                break;

            case R.id.action_mode_xor:
                mode = PorterDuff.Mode.XOR;
                break;

            case R.id.action_mode_darken:
                mode = PorterDuff.Mode.DARKEN;
                break;

            case R.id.action_mode_lighten:
                mode = PorterDuff.Mode.LIGHTEN;
                break;

            case R.id.action_mode_multiply:
                mode = PorterDuff.Mode.MULTIPLY;
                break;

            case R.id.action_mode_screen:
                mode = PorterDuff.Mode.SCREEN;
                break;

            case R.id.action_mode_add:
                mode = PorterDuff.Mode.ADD;
                break;

            case R.id.action_mode_overlay:
                mode = PorterDuff.Mode.OVERLAY;
                break;
        }
        duffview.setXfermode(mode);
        mModeText.setText(item.getTitle().toString());

        return super.onOptionsItemSelected(item);
    }
}
