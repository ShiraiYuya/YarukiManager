package com.example.shirayama.yarukimanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class TaskListAdapter extends ArrayAdapter<TaskBean>{
    private LayoutInflater mInflater;

    public TaskListAdapter(Context context, int textViewResourceId, List<TaskBean> objects){
        super(context, textViewResourceId, objects);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        // 指定行のデータを取得

        TaskBean task = (TaskBean)getItem(position);
        int posi = position;
        Log.d("tag","listadapter:id="+task.getId());
        Log.d("tag","title:"+task.getTitle());
        Log.d("tag","due:"+task.getDueDate());

        if(null == convertView){
            convertView = mInflater.inflate(R.layout.row_task, null);
        }

        // 行のデータを項目へ設定

        TextView title = (TextView)convertView.findViewById(R.id.textView);
        title.setText(task.getTitle());
        TextView due = (TextView)convertView.findViewById(R.id.textView2);
        Calendar duecal = task.getDueDate();
        due.setText("～"+duecal.get(Calendar.MONTH)+"月"+duecal.get(Calendar.DATE)+"日");
        /*
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
        */



        return convertView;


    }





}
