package com.hua.canvas;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hua.canvas.activity.DrawBitmapActivity;
import com.hua.canvas.activity.RotateActivity;
import com.hua.canvas.activity.ScaleActivity;
import com.hua.canvas.activity.SkewActivity;
import com.hua.canvas.activity.TranslateActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Mode> mList;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mListView = (ListView) findViewById(R.id.listview);
        if(mList == null) {
            mList = new ArrayList<>();
            mList.add(new Mode("Translate", TranslateActivity.class));
            mList.add(new Mode("Scale", ScaleActivity.class));
            mList.add(new Mode("Rotate", RotateActivity.class));
            mList.add(new Mode("Skew", SkewActivity.class));
            mList.add(new Mode("DrawBitmap", DrawBitmapActivity.class));
        }
        ModeAdapter adapter = new ModeAdapter(this);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this, mList.get(position).getCls()));
            }
        });
    }

    class ModeAdapter extends BaseAdapter{
        private Context mContext;

        public ModeAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Mode getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if(convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false);
                holder.name = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(holder);
            }else {
                holder = (Holder) convertView.getTag();
            }
            holder.name.setText(getItem(position).getName());
            return convertView;
        }
    }

    class  Holder{
        TextView name;
    }
    class Mode{
        String name;
        Class<?> cls;

        public Mode(String name, Class<?> cls) {
            this.name = name;
            this.cls = cls;
        }

        public String getName() {
            return name;
        }

        public Class<?> getCls() {
            return cls;
        }
    }
}
