package com.example.shirayama.yarukimanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper  {

    public MyOpenHelper(Context context) {
        super(context, "mydata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("drop table habit;");
        db.execSQL("create table habit(" + "_id integer primary key autoincrement, title text not null, declaration text, mon boolean, tue boolean, wed boolean, thu boolean, fri boolean, sat boolean, sun boolean, norma integer, stamp integer, boolean finished" + ");");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
