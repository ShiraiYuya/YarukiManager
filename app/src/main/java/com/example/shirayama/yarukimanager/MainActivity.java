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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity{
    Calendar calendar;
    String[] datelist = {"日","月","火","水","木","金","土"};
    String[] datelisteng = {"sun","mon","tue","wed","thu","fri","sat"};
    private PopupWindow popupWindow;
    private int popup_stamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView left_day =(TextView) findViewById(R.id.daymenu_left);
        TextView right_day =(TextView) findViewById(R.id.daymenu_right);

        Log.d("tag","activity:Main");

        calendar = Calendar.getInstance();
        makelist(calendar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickfab();
            }
        });
        left_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.DATE, -1);
                makelist(calendar);
            }
        });
        right_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.DATE, 1);
                makelist(calendar);
            }
        });


    }


    private void makelist(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int date = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String date_string = "" + calendar.get(Calendar.YEAR) +"/"+month+"/"+day;
        Log.d("tag", "makelist:"+date_string);
        TextView textView =(TextView) findViewById(R.id.daymenu_today);
        textView.setText(String.valueOf(month+1) +"月"+ String.valueOf(day) + "日(" +datelist[date]+ ")");

        //ここからDB情報取得
        MyOpenHelper helper = new MyOpenHelper(this);

        //データベース書き換えるとき
        //final SQLiteDatabase db2 = helper.getWritableDatabase();
        //    db2.execSQL("drop table habit;");
        //    db2.execSQL("create table habit(" + "_id integer primary key autoincrement, title text not null, declaration text, mon boolean, tue boolean, wed boolean, thu boolean, fri boolean, sat boolean, sun boolean, norma integer, stamp integer, finished boolean" + ");");
        //    db2.execSQL("create table task(" + "_id integer primary key autoincrement, title text not null, declaration text, step int, unit text, stamp int, due text, created text, cancelled boolean, finished boolean);");
        //db2.close();





        //    final SQLiteDatabase db2 = helper.getReadableDatabase();
        //    String sql2 = "select count(title) from habit;";
        //   long recodeCount = DatabaseUtils.queryNumEntries(db2, "habit");
        //   Log.d("tag", "recodeCount : " + recodeCount);
        //   db2.close();

        //final SQLiteDatabase db2 = helper.getWritableDatabase();
        //String sql2 = "create table done_habit(habit integer not null, date text not null, stamp integer, cancelled boolean, primary key(habit,date))" ;
        //db2.execSQL(sql2);
        //db2.close();

        final SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "select _id, title, declaration, norma, stamp, mon, finished from habit where " + datelisteng[date] + " = 1 and finished = 0;";
        //String sql = "select _id, title, declaration, norma, stamp from habit where " + datelisteng[date] + " = 1;";
        // String sql = "select _id, title, declaration, norma, stamp, mon, finished  from habit;";
        Cursor c = db.rawQuery(sql, null);
        boolean mov = c.moveToFirst();

        ArrayList<HabitBean> list = new ArrayList<HabitBean>();

        Log.d("tag", "get_habit");
        while (mov) {

            String sql3 = "select stamp from done_habit where habit = " + c.getInt(0) + " and date = " + date_string;
            Cursor c3 = db.rawQuery(sql3, null);
            boolean mov3 = c3.moveToFirst();
            int stamp = 0;
            while(mov3){
                stamp = c3.getInt(0);
                mov3 = c3.moveToNext();
            }
            c3.close();
            Log.d("tag","done:" + stamp);
            HabitBean habit = new HabitBean();
            habit.setId(c.getInt(0));
            habit.setTitle(c.getString(1));
            habit.setDeclaration(c.getString(2));
            //habit.setDays(c.getInt(3));
            habit.setNorma(c.getInt(3));
            habit.setStamp(c.getInt(4));
            habit.setDone_Stamp(stamp);
            list.add(habit);
            Log.d("tag", "id:" + c.getInt(0));
            Log.d("tag", "title:" + c.getString(1));
            Log.d("tag","norma:" + c.getInt(3));
            Log.d("tag","stamp:" + c.getInt(4));
            Log.d("tag","done:" + stamp);
            Log.d("tag","mon:" + c.getInt(5));
            Log.d("tag","finished:" + c.getInt(6));
            mov = c.moveToNext();
        }
        c.close();
        db.close();
        helper.close();
        ListView listview = (ListView)findViewById(R.id.listview);
        android.widget.ListAdapter adapter = new ListAdapter(this, 0, list);
        //ListView listView = (ListView)findViewById(R.id.listview);
        //listView.getLayoutParams().height = 1000;
        //listView.requestLayout();
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("tag","onitemclick");
                ListView listView = (ListView) parent;
                // クリックされたアイテムを取得します
                HabitBean habit = (HabitBean) listView.getItemAtPosition(position);
                switch (view.getId()) {
                    case R.id.titleView:
                        clicktitle(habit);
                        break;
                    case R.id.wrapbtn:
                        popup(habit);
                        break;
                }
            }
        });
    }

    private void clickfab() {
        Log.d("tag","clickfab");
        Intent intent = new Intent(this, CreateHabitActivity.class);
        intent.putExtra("status",0);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clicktitle(HabitBean habit) {
        Intent intent = new Intent(this, CreateHabitActivity.class);
        intent.putExtra("id",habit.getId());
     //   intent.putExtra("title",habit.getTitle());
        intent.putExtra("declaration",habit.getDeclaration());
        intent.putExtra("mon",habit.getDays()[0]);
        intent.putExtra("tue",habit.getDays()[1]);
        intent.putExtra("wed",habit.getDays()[2]);
        intent.putExtra("thu",habit.getDays()[3]);
        intent.putExtra("fri",habit.getDays()[4]);
        intent.putExtra("sat",habit.getDays()[5]);
        intent.putExtra("sun",habit.getDays()[6]);
        intent.putExtra("norma",habit.getNorma());
        intent.putExtra("stamp",habit.getStamp());//
        startActivity(intent);
    }

    private void popup(final HabitBean habit) {
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

    private void choose_stamp(LinearLayout layout, int i,int max) {

        ImageButton[] stamps = { (ImageButton)layout.findViewById(R.id.stampView1), (ImageButton)layout.findViewById(R.id.stampView2), (ImageButton)layout.findViewById(R.id.stampView3)};
        popup_stamp = i;
        for(int j=0;j<max;j++){
            stamps[j].setBackgroundResource(R.drawable.daybtn_unselected);
        }
        for(int j=0;j<i;j++){
            stamps[j].setBackgroundResource(R.drawable.daybtn_selected);
        }
    }

}
