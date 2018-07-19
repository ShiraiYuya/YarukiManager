package com.example.shirayama.yarukimanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<HabitBean>{
    private LayoutInflater mInflater;

    public ListAdapter(Context context, int textViewResourceId, List<HabitBean> objects){
        super(context, textViewResourceId, objects);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        // 指定行のデータを取得

        HabitBean habit = (HabitBean)getItem(position);
        int posi = position;
        Log.d("tag","listadapter:id="+habit.getId());
        Log.d("tag","title:"+habit.getTitle());
        Log.d("tag","days:"+habit.getDays());
        Log.d("tag","norma:"+habit.getNorma());
        Log.d("tag","stamp:"+habit.getStamp());

        if(null == convertView){
            convertView = mInflater.inflate(R.layout.row, null);
        }

        // 行のデータを項目へ設定

        TextView title = (TextView)convertView.findViewById(R.id.titleView);
        LinearLayout stamps = (LinearLayout) convertView.findViewById(R.id.wrapbtn);
        title.setText(habit.getTitle());
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, R.id.titleView);
            }
        });
        stamps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ListView) parent).performItemClick(v, position, R.id.wrapbtn);
            }
        });

        ImageButton stamp1 = (ImageButton)convertView.findViewById(R.id.stampView1);
        ImageButton stamp2 = (ImageButton)convertView.findViewById(R.id.stampView2);
        ImageButton stamp3 = (ImageButton)convertView.findViewById(R.id.stampView3);
        LinearLayout wrapper = (LinearLayout)convertView.findViewById(R.id.wrapper);
        int stamp = habit.getStamp();
        int done_stamp = habit.getDone_Stamp();
        if(stamp == 1){
            stamp2.setVisibility(View.INVISIBLE);
            stamp3.setVisibility(View.INVISIBLE);
        }else if(stamp == 2){
            stamp3.setVisibility(View.INVISIBLE);
        }else{

        }

        if(done_stamp == 1){
            stamp1.setBackgroundResource(R.drawable.daybtn_selected);
        }else if(done_stamp == 2){
            stamp1.setBackgroundResource(R.drawable.daybtn_selected);
            stamp2.setBackgroundResource(R.drawable.daybtn_selected);
        }else if(done_stamp == 3){
            stamp1.setBackgroundResource(R.drawable.daybtn_selected);
            stamp2.setBackgroundResource(R.drawable.daybtn_selected);
            stamp3.setBackgroundResource(R.drawable.daybtn_selected);
        }


        return convertView;


    }





}
