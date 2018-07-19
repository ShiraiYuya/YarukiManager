package com.example.shirayama.yarukimanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class ReportActivity extends AppCompatActivity {
    Calendar calendar;
    String[] datelist = {"日","月","火","水","木","金","土"};
    String[] datelisteng = {"sun","mon","tue","wed","thu","fri","sat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        calendar = Calendar.getInstance();
        makecard(calendar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void makecard(Calendar calendar) {
        Calendar day = Calendar.getInstance();
        day.set( Calendar.DAY_OF_WEEK, 1 );
        int firstmonth = day.get( Calendar.MONTH );
        int firstday = day.get(Calendar.DAY_OF_MONTH);

        day.set( Calendar.DAY_OF_WEEK, 7 );
        int lastmonth = day.get(Calendar.MONTH);
        int lastday = day.get(Calendar.DAY_OF_MONTH);

        TextView textView =(TextView) findViewById(R.id.daymenu_today);
        String weekstring = "" + firstmonth +"月" + firstday + "日(日)～" + lastmonth + "月" + lastday + "日(土)";
        textView.setText(weekstring);

        MyOpenHelper helper = new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getReadableDatabase();
        int stampindex = 1;
        for(int i=6; i<=7; i++){
            day.set( Calendar.DAY_OF_WEEK, i );
            String date = "" + day.get(Calendar.YEAR) + "/" +  day.get(Calendar.MONTH) + "/" + day.get(Calendar.DATE);
            String date_display = "" + day.get(Calendar.MONTH) + "/" + day.get(Calendar.DATE);
            String sql = "select habit.title, done_habit.stamp from done_habit ";
            sql += "inner join habit on done_habit.habit = habit._id where done_habit.date = " + date + ";";
            Log.d("date",sql);

            Cursor c = db.rawQuery(sql, null);
            boolean mov = c.moveToFirst();
            while (mov) {
                final String habit = c.getString(0);
                int stamp = c.getInt(1);
                Log.d("date",""+stamp);
                for(int j=0;j<stamp;j++){
                    String table_id_str = "data_" + stampindex;
                    int table_id = getResources().getIdentifier(table_id_str, "id", getPackageName());
                    TextView textview= (TextView) findViewById(table_id);
                    textview.setVisibility(View.VISIBLE);
                    textview.setBackgroundResource(R.drawable.done_red);
                    textview.setText(date_display);
                    textview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TextView textview= (TextView) findViewById(R.id.detail);
                            textview.setText(habit);
                        }
                    });
                    stampindex++;
                }
                mov = c.moveToNext();
            }
            c.close();
        }
        db.close();
        helper.close();
    }

}
