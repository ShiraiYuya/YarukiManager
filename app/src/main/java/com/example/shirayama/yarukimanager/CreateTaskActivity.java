package com.example.shirayama.yarukimanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.shirayama.yarukimanager.R.drawable.stamp2;

public class CreateTaskActivity extends AppCompatActivity {
    int id_pre;
    int stamp;
    private PopupWindow popupWindow;
    Calendar calendar_duedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d("tag","activity:create_habit");

        Intent intent = getIntent();
        Integer status = intent.getIntExtra("status",-1);
        id_pre = intent.getIntExtra("id",-1);
        if(id_pre == -1){
            stamp = 1;
        }else{

        }

        TextView title = (TextView)findViewById(R.id.title);
        EditText editTitle = (EditText) findViewById(R.id.editText);
        EditText editDeclaration = (EditText) findViewById(R.id.editText2);
        EditText editUnit = (EditText) findViewById(R.id.editText3);
        TextView duedate = (TextView)findViewById(R.id.duedate);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        calendar_duedate =  Calendar.getInstance();
        int year = calendar_duedate.get(Calendar.YEAR);
        int month = calendar_duedate.get(Calendar.MONTH);
        int day = calendar_duedate.get(Calendar.DAY_OF_MONTH);
        duedate.setText(""+year+"年" +String.valueOf(month+1) +"月"+ String.valueOf(day) + "日");

        ImageButton imgbtn1 = (ImageButton)findViewById(R.id.imageButton1);
        ImageButton imgbtn2 = (ImageButton)findViewById(R.id.imageButton2);
        ImageButton imgbtn3 = (ImageButton)findViewById(R.id.imageButton3);
        Button submit = (Button)findViewById(R.id.submit);
        Button delete = (Button)findViewById(R.id.delete);

        if(stamp==2){
            imgbtn2.setImageResource(R.drawable.stamp);
        } else if (stamp == 3) {
            imgbtn2.setImageResource(R.drawable.stamp);
            imgbtn3.setImageResource(R.drawable.stamp);
        }

        if(id_pre==-1){
            submit.setText("登録する");
            delete.setVisibility(View.INVISIBLE);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    register();
                }
            });
        }else{
            title.setText("タスクの情報を更新しよう！");
            submit.setText("更新する");
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    update(id_pre);
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete(id_pre);
                }});
        }

        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickimgbtn(0);
            }
        });

        imgbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickimgbtn(1);
            }
        });

        imgbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickimgbtn(2);
            }
        });

        duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCalendar();
            }
        });
    }

    private void clickimgbtn(int selected_btn) {
        Log.d("tag","dayimgbtn_clicked:" + selected_btn);
        stamp = selected_btn + 1;
        ImageButton imgbtn1 = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton imgbtn2 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton imgbtn3 = (ImageButton) findViewById(R.id.imageButton3);
        if(selected_btn==0){
            imgbtn2.setImageResource(stamp2);
            imgbtn3.setImageResource(stamp2);
        }else if(selected_btn==1){
            imgbtn2.setImageResource(R.drawable.stamp);
            imgbtn3.setImageResource(stamp2);
        }else{
            imgbtn2.setImageResource(R.drawable.stamp);
            imgbtn3.setImageResource(R.drawable.stamp);
        }

    }

    private void viewCalendar() {
        Log.d("tag","popup_calendar");

        final LinearLayout popLayout
                = (LinearLayout)getLayoutInflater().inflate(
                R.layout.popupcalendar_layout, null);


        popupWindow = new PopupWindow(CreateTaskActivity.this);
        popupWindow.setContentView(popLayout);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_bg));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, getResources().getDisplayMetrics());
        float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, getResources().getDisplayMetrics());
        popupWindow.setWidth((int) width);
        popupWindow.setHeight((int) height);
        popupWindow.showAtLocation(findViewById(R.id.table), Gravity.CENTER, 0, 0);

        final CalendarView calendarView = (CalendarView)popLayout.findViewById(R.id.calendarView);
        calendarView.setDate(calendar_duedate.getTimeInMillis());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                calendar_duedate.set( Calendar.YEAR, year );
                calendar_duedate.set( Calendar.MONTH, month );
                calendar_duedate.set( Calendar.DAY_OF_MONTH, dayOfMonth );
                TextView duedate = (TextView)findViewById(R.id.duedate);
                duedate.setText(""+year+"年" +String.valueOf(month+1) +"月"+ dayOfMonth + "日");
            }
        });



        Button closePopupButton = (Button)popLayout.findViewById(R.id.close_due);
        //ポップアップを消すボタン
        closePopupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindow.dismiss();
                //ポップアップを消す。
            }
        });

        Button deleteButton = (Button)popLayout.findViewById(R.id.close_due);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public void register() {
        EditText edittext1 = (EditText) findViewById(R.id.editText);
        String title = edittext1.getText().toString();
        EditText edittext2 = (EditText) findViewById(R.id.editText2);
        String declaration = edittext2.getText().toString();
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        int step = Integer.parseInt(spinner.getSelectedItem().toString());
        EditText edittext3 = (EditText) findViewById(R.id.editText2);
        String unit = edittext3.getText().toString();
        String duedate = "" + calendar_duedate.get(Calendar.YEAR) +"/" + (calendar_duedate.get(Calendar.MONTH)+1) + "/" + calendar_duedate.get(Calendar.DATE);
        Calendar calendar_register =  Calendar.getInstance();
        String registerdate = "" + calendar_register.get(Calendar.YEAR) +"/" + (calendar_register.get(Calendar.MONTH)+1) + "/" + calendar_register.get(Calendar.DATE);
        if (title.equals("")) {
            Toast.makeText(CreateTaskActivity.this, "タイトルを入力してください。",
                    Toast.LENGTH_SHORT).show();
        } else {
            MyOpenHelper helper = new MyOpenHelper(this);
            final SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues insertValues = new ContentValues();
            insertValues.put("title", title);
            insertValues.put("declaration", declaration);
            insertValues.put("step", step);
            insertValues.put("stamp", stamp);
            insertValues.put("due",duedate);
            insertValues.put("created", registerdate);
            insertValues.put("cancelled", false);
            insertValues.put("finished", false);
            long id = db.insert("task", null, insertValues);
            db.close();

            Intent intent = new Intent(this, TaskActivity.class);
            startActivity(intent);
        }
    }


    public void update(int id) {
        EditText editTitle = (EditText) findViewById(R.id.editText);
        String title = editTitle.getText().toString();
        EditText editDeclaration = (EditText) findViewById(R.id.editText2);
        String declaration = editDeclaration.getText().toString();

        if (editTitle.equals("")) {
            Toast.makeText(CreateTaskActivity.this, "名前を入力してください。",
                    Toast.LENGTH_SHORT).show();
        } else {
            MyOpenHelper helper = new MyOpenHelper(this);
            final SQLiteDatabase db = helper.getWritableDatabase();
            /*ここからDB
            ContentValues updateValues = new ContentValues();
            updateValues.put("title", title);
            updateValues.put("declaration", declaration);
            updateValues.put("norma", ratingBar.getRating());
            updateValues.put("stamp", stamp);
            db.update("habit", updateValues, "_id="+String.valueOf(id), null);
            */
            db.close();


            Intent intent = new Intent(this, TaskActivity.class);
            startActivity(intent);
        }
    }

    public void delete(int id) {
        MyOpenHelper helper = new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();
        /*ここからDB
        ContentValues updateValues = new ContentValues();
        updateValues.put("finished", true);
        db.update("habit", updateValues, "_id="+String.valueOf(id), null);
        db.close();

        Log.d("tag","delete");
        Log.d("tag","id:" + id);

        */

        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }

}
