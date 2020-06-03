package com.sam.chart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class Recycleractivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Data> dataarraylist =new ArrayList<>();
    SQLiteDatabase db;
    Mydatabase mydatabaseclass;
    Myadapter adapterclassobj ;
    Data dataclassobj;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_activity);

        recyclerView=findViewById(R.id.recycleview);

        mydatabaseclass =new Mydatabase(this);
        db= mydatabaseclass.getWritableDatabase();
        String [] column={mydatabaseclass.employeename,mydatabaseclass.designation,mydatabaseclass.managername};
        cursor=db.query(mydatabaseclass.tablename,column,null,null,null,null,null);
        Log.d("cursor_count", String.valueOf(cursor.getCount()));
        if (cursor!=null)
        {
            while (cursor.moveToNext())
            {
                String getname=cursor.getString(0);
                String getdesig=cursor.getString(1);
                String getmanager=cursor.getString(2);
                dataclassobj =new Data(getname,getdesig,getmanager);
                dataarraylist.add(dataclassobj);

                Log.d("//datalistcount", dataclassobj.empname);
                Log.d("//datalistcount", dataclassobj.desig);
                Log.d("//datalistcount",dataclassobj.managername);
            }

            //adapterclassobj.notifyDataSetChanged();
        }
        db.close();
        adapterclassobj=new Myadapter(this,dataarraylist);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterclassobj);
    }
    @SuppressLint("ResourceType")
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.layout.main_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {


     if (item.getItemId() == R.id.creategroupid)
    {
        createChart();
    }
        return true;
    }

    private void createChart() {
        Intent chart=new Intent(Recycleractivity.this,orgChart.class);
        startActivity(chart);
    }
}
