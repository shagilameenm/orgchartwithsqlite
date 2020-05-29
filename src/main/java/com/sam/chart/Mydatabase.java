package com.sam.chart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Mydatabase extends SQLiteOpenHelper {

    public static final String dbname ="orgdb.db";
    public static final String tablename ="orgdata";
    public static final String employeename ="EmpName";
    public static final String designation ="Designation";
    public static final String managername ="Manager";
    private static final String createtable = "create table " + tablename + "(" + employeename + " TEXT NOT NULL," + designation +" TEXT NOT NULL," + managername + " TEXT )";

    SQLiteDatabase db;

    public Mydatabase(Context context) {
        super(context, dbname, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createtable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tablename);
    }
    public void addData(String empname,String desig,String manager)
    {
        db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(employeename,empname);
        contentValues.put(designation,desig);
        contentValues.put(managername,manager);
        db.insert(tablename,null,contentValues);
        db.close();
        getData();

    }
    private void getData() {
        db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+tablename,null);
        Log.d( "//CURSOR: ", String.valueOf(cursor.getCount()));
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            System.out.println("//Empname: "+cursor.getString(cursor.getColumnIndex(employeename)));
            System.out.println("//des: "+cursor.getString(cursor.getColumnIndex(designation)));
            System.out.println("//manager: "+cursor.getString(cursor.getColumnIndex(managername)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
    }
}
