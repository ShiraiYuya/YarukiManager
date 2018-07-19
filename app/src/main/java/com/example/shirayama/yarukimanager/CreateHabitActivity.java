package com.example.shirayama.yarukimanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;

import android.widget.TextView;
import android.widget.Toast;

import static com.example.shirayama.yarukimanager.R.id.ratingBar;

public class CreateHabitActivity extends AppCompatActivity {

    String title= "";
    String declaration= "";
    final CharSequence[] chkItems = {"月", "火", "水","木", "金", "土","日"};
    boolean[] chkSts = {true, true, true, true, true, false, false};
    int days;
    int daysmax;
    int stamp;
    int id_pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);
        Log.d("tag","activity:create_habit");

        Intent intent = getIntent();
        Integer status = intent.getIntExtra("status",-1);
        id_pre = intent.getIntExtra("id",-1);
        if(id_pre == -1){
            days = 5;
            stamp = 1;
        }else{
            MyOpenHelper helper = new MyOpenHelper(this);
            final SQLiteDatabase db = helper.getReadableDatabase();
            String sql = "select _id, title, declaration, norma, stamp, mon,tue,wed,thu,fri,sat,sun, finished from habit where _id = " + id_pre + ";";
            Cursor c = db.rawQuery(sql, null);
            c.moveToFirst();
            title = c.getString(1);
            declaration = c.getString(2);
            days = c.getInt(3);
            stamp = c.getInt(4);
            chkSts[0] = (c.getInt(5) == 1);
            chkSts[1] = (c.getInt(6) == 1);
            chkSts[2] = (c.getInt(7) == 1);
            chkSts[3] = (c.getInt(8) == 1);
            chkSts[4] = (c.getInt(9) == 1);
            chkSts[5] = (c.getInt(10) == 1);
            chkSts[6] = (c.getInt(11) == 1);

            c.close();
            db.close();
            helper.close();
        }

        Log.d("tag","id:" + id_pre);
        Log.d("tag", "title:" + title);
        Log.d("tag","norma:" + days);
        Log.d("tag","stamp:" + stamp);
        Log.d("tag","mon:" + chkSts[0]);

        TextView title01 = (TextView)findViewById(R.id.title01);
        EditText editTitle = (EditText) findViewById(R.id.editText);
        EditText editDeclaration = (EditText) findViewById(R.id.editText2);
        TextView btn1 = (TextView)findViewById(R.id.day1);
        TextView btn2 = (TextView)findViewById(R.id.day2);
        TextView btn3 = (TextView)findViewById(R.id.day3);
        TextView btn4 = (TextView)findViewById(R.id.day4);
        TextView btn5 = (TextView)findViewById(R.id.day5);
        TextView btn6 = (TextView)findViewById(R.id.day6);
        TextView btn7 = (TextView)findViewById(R.id.day7);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar) ;
        ImageButton imgbtn1 = (ImageButton)findViewById(R.id.imageButton1);
        ImageButton imgbtn2 = (ImageButton)findViewById(R.id.imageButton2);
        ImageButton imgbtn3 = (ImageButton)findViewById(R.id.imageButton3);
        Button submit = (Button)findViewById(R.id.submit);
        Button delete = (Button)findViewById(R.id.delete);

        editTitle.setText(title);
        editDeclaration.setText(declaration);
        daysmax = 0;
        for(int i=0;i<7;i++){
            if(chkSts[i])daysmax++;
        }
        if(chkSts[0])btn1.setBackgroundResource(R.drawable.daybtn_selected);
        if(chkSts[1])btn2.setBackgroundResource(R.drawable.daybtn_selected);
        if(chkSts[2])btn3.setBackgroundResource(R.drawable.daybtn_selected);
        if(chkSts[3])btn4.setBackgroundResource(R.drawable.daybtn_selected);
        if(chkSts[4])btn5.setBackgroundResource(R.drawable.daybtn_selected);
        if(chkSts[5])btn6.setBackgroundResource(R.drawable.daybtn_selected);
        if(chkSts[6])btn7.setBackgroundResource(R.drawable.daybtn_selected);

        ratingBar.setNumStars(daysmax);
        ratingBar.setRating(days);

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
            title01.setText("習慣の情報を更新しよう！");
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





        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickbtn(0);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickbtn(1);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickbtn(2);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickbtn(3);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickbtn(4);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickbtn(5);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickbtn(6);
            }
        });

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



    }

    private void clickbtn(int selected_btn) {
        Log.d("tag","daybtn_clicked:" + selected_btn);

        chkSts[selected_btn] = !chkSts[selected_btn];
        int[] selected = {R.id.day1,R.id.day2,R.id.day3,R.id.day4,R.id.day5,R.id.day6,R.id.day7};
        TextView btn = (TextView)findViewById(selected[selected_btn]);
        RatingBar ratingbar = (RatingBar)findViewById(ratingBar);
        if(chkSts[selected_btn]){
            btn.setBackgroundResource(R.drawable.daybtn_selected);
            daysmax++;
        }else{
            btn.setBackgroundResource(R.drawable.daybtn_unselected);
            daysmax--;
        }
        days = daysmax;
        ratingbar.setNumStars(daysmax);
        ratingbar.setRating(days);

    }

    private void clickimgbtn(int selected_btn) {
        Log.d("tag","dayimgbtn_clicked:" + selected_btn);
        stamp = selected_btn + 1;
        ImageButton imgbtn1 = (ImageButton) findViewById(R.id.imageButton1);
        ImageButton imgbtn2 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton imgbtn3 = (ImageButton) findViewById(R.id.imageButton3);
        if(selected_btn==0){
            imgbtn2.setImageResource(R.drawable.stamp2);
            imgbtn3.setImageResource(R.drawable.stamp2);
        }else if(selected_btn==1){
            imgbtn2.setImageResource(R.drawable.stamp);
            imgbtn3.setImageResource(R.drawable.stamp2);
        }else{
            imgbtn2.setImageResource(R.drawable.stamp);
            imgbtn3.setImageResource(R.drawable.stamp);
        }

    }

    public void register() {
        EditText edittext1 = (EditText) findViewById(R.id.editText);
        String title = edittext1.getText().toString();
        EditText edittext2 = (EditText) findViewById(R.id.editText2);
        String declaration = edittext2.getText().toString();
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar) ;
        if (title.equals("")) {
            Toast.makeText(CreateHabitActivity.this, "タイトルを入力してください。",
                    Toast.LENGTH_SHORT).show();
        } else {
            MyOpenHelper helper = new MyOpenHelper(this);
            final SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues insertValues = new ContentValues();
            insertValues.put("title", title);
            insertValues.put("declaration", declaration);
            insertValues.put("mon", chkSts[0]);
            insertValues.put("tue", chkSts[1]);
            insertValues.put("wed", chkSts[2]);
            insertValues.put("thu", chkSts[3]);
            insertValues.put("fri", chkSts[4]);
            insertValues.put("sat", chkSts[5]);
            insertValues.put("sun", chkSts[6]);
            insertValues.put("norma", ratingBar.getRating());
            insertValues.put("stamp", stamp);
            insertValues.put("finished", false);
            long id = db.insert("habit", null, insertValues);
            db.close();

            Log.d("tag","register");
            Log.d("tag","title:" + title);
            Log.d("tag","norma:" + days);
            Log.d("tag","stamp" + stamp);
            Log.d("tag","day" + chkSts);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void update(int id) {
        EditText editTitle = (EditText) findViewById(R.id.editText);
        String title = editTitle.getText().toString();
        EditText editDeclaration = (EditText) findViewById(R.id.editText2);
        String declaration = editDeclaration.getText().toString();
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar) ;

        if (editTitle.equals("")) {
            Toast.makeText(CreateHabitActivity.this, "名前を入力してください。",
                    Toast.LENGTH_SHORT).show();
        } else {
            MyOpenHelper helper = new MyOpenHelper(this);
            final SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues updateValues = new ContentValues();
            updateValues.put("title", title);
            updateValues.put("declaration", declaration);
            updateValues.put("mon", chkSts[0]);
            updateValues.put("tue", chkSts[1]);
            updateValues.put("wed", chkSts[2]);
            updateValues.put("thu", chkSts[3]);
            updateValues.put("fri", chkSts[4]);
            updateValues.put("sat", chkSts[5]);
            updateValues.put("sun", chkSts[6]);
            updateValues.put("norma", ratingBar.getRating());
            updateValues.put("stamp", stamp);
            db.update("habit", updateValues, "_id="+String.valueOf(id), null);
            db.close();

            Log.d("tag","update");
            Log.d("tag","id:" + id);
            Log.d("tag","title:" + title);
            Log.d("tag","norma:" + days);
            Log.d("tag","stamp" + stamp);
            Log.d("tag","day" + chkSts);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void delete(int id) {
            MyOpenHelper helper = new MyOpenHelper(this);
            final SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues updateValues = new ContentValues();
            updateValues.put("finished", true);
            db.update("habit", updateValues, "_id="+String.valueOf(id), null);
            db.close();

        Log.d("tag","delete");
        Log.d("tag","id:" + id);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        }

}

/*
    private void clickbtn2() {
        AlertDialog.Builder checkDlg = new AlertDialog.Builder(this);
        checkDlg.setTitle("目標回数を選択してください");
        String[] list = new String[days];
        for(int i=0;i<days;i++){
            list[i] = "週" + (i+1) + "回";
        }
        checkDlg.setItems(
                list,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        days = which + 1;
                        Button btn2 = (Button)findViewById(R.id.button2);
                        btn2.setText("週"+ days + "回");
                    }
                });
        checkDlg.create().show();
    }
   */


