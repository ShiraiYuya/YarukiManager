package com.example.shirayama.yarukimanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.shirayama.yarukimanager.R.drawable.habit;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        MyOpenHelper helper = new MyOpenHelper(this);


        final SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "select _id, title, declaration, due, stamp, finished from task where finished = 0;";

        Cursor c = db.rawQuery(sql, null);
        boolean mov = c.moveToFirst();

        ArrayList<TaskBean> list = new ArrayList<TaskBean>();

        Log.d("tag", "get_task");
        while (mov) {

            TaskBean task = new TaskBean();
            task.setId(c.getInt(0));
            task.setTitle(c.getString(1));
            task.setDeclaration(c.getString(2));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Calendar due = Calendar.getInstance();
            try{
                due.setTime(sdf.parse(c.getString(3)));
            }catch (ParseException e){
            }


            task.setDueDate(due);
            task.setStamp(c.getInt(4));

            list.add(task);
            mov = c.moveToNext();
        }
        c.close();
        db.close();
        helper.close();

        ListView listview = (ListView)findViewById(R.id.listview);
        android.widget.ListAdapter adapter = new TaskListAdapter(this, 0, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("tag","onitemclick");
                ListView listView = (ListView) parent;
                // クリックされたアイテムを取得します
                TaskBean task = (TaskBean) listView.getItemAtPosition(position);
                switch (view.getId()) {
                    case R.id.textView:
                        clicktitle(task);
                        break;
                    case R.id.linearLayout2:
                        popup(task);
                        break;
                    case R.id.textView3:
                        popup(task);
                        break;
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateTaskActivity.class);
                intent.putExtra("status",0);
                startActivity(intent);
            }
        });
    }

    private void clicktitle(TaskBean task) {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        intent.putExtra("id",task.getId());
        startActivity(intent);
    }

    private void popup(final TaskBean task) {
        Log.d("tag","popup");

        popup_stamp = 0;
        final LinearLayout popLayout
                = (LinearLayout)getLayoutInflater().inflate(
                R.layout.popup_layout, null);
        TextView popupTitle
                = (TextView)popLayout.findViewById(R.id.popup_title);
        popupTitle.setText(habit.getTitle());
        TextView popupDeclaration
                = (TextView)popLayout.findViewById(R.id.popup_declaration);
        popupDeclaration.setText(habit.getDeclaration());
        ImageButton stamp1 = (ImageButton)popLayout.findViewById(R.id.stampView1);
        ImageButton stamp2 = (ImageButton)popLayout.findViewById(R.id.stampView2);
        ImageButton stamp3 = (ImageButton)popLayout.findViewById(R.id.stampView3);

        final int stampmax = habit.getStamp();
        int stamp = habit.getDone_Stamp();
        popup_stamp = stamp;
        stamp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose_stamp(popLayout,1,stampmax);
            }
        });
        stamp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose_stamp(popLayout,2,stampmax);
            }
        });
        stamp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose_stamp(popLayout,3,stampmax);
            }
        });
        if(stampmax==3){

        }else if(stampmax==2){
            stamp3.setVisibility(View.INVISIBLE);
        }else{
            stamp2.setVisibility(View.INVISIBLE);
            stamp3.setVisibility(View.INVISIBLE);
        }

        if(stamp==3){
            stamp1.setBackgroundResource(R.drawable.daybtn_selected);
            stamp2.setBackgroundResource(R.drawable.daybtn_selected);
            stamp3.setBackgroundResource(R.drawable.daybtn_selected);
        }else if(stamp==2){
            stamp1.setBackgroundResource(R.drawable.daybtn_selected);
            stamp2.setBackgroundResource(R.drawable.daybtn_selected);
        }else if(stamp==1){
            stamp1.setBackgroundResource(R.drawable.daybtn_selected);
        }
        popupWindow = new PopupWindow(MainActivity.this);
        popupWindow.setContentView(popLayout);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_bg));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
        float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics());
        popupWindow.setWidth((int) width);
        popupWindow.setHeight((int) height);

        popupWindow.showAtLocation(findViewById(R.id.listview), Gravity.CENTER, 0, 0);


        //popupWindow.showAsDropDown(v, 0, 0);
        //ボタンの下にポップアップを表示

        Button closePopupButton = (Button)popLayout.findViewById(R.id.close_button);
        //ポップアップを消すボタン
        closePopupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindow.dismiss();
                //ポップアップを消す。
            }
        });

        Button updateButton = (Button)popLayout.findViewById(R.id.update_button);

        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyOpenHelper helper = new MyOpenHelper(MainActivity.this);
                final SQLiteDatabase db = helper.getWritableDatabase();
                String sql = "replace into done_habit(habit, date, stamp, cancelled) values ";
                String date = "" + calendar.get(Calendar.YEAR) + "/" +  calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DATE);
                sql += "(" + habit.getId() + "," + date + "," + popup_stamp + ","+ 0 +")";
                db.execSQL(sql);
                db.close();
                popupWindow.dismiss();
                makelist(calendar);
            }
        });
    }

}
