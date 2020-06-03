package com.sam.chart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder> {

    private Context c;
    ArrayList<Data> dataArrayList;
    SQLiteDatabase db;
    public Myadapter(@NonNull Context context, ArrayList<Data> dataArrayList) {
        c = context;
        this.dataArrayList = dataArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View displayName = layoutInflater.inflate(R.layout.textactivity, parent, false);
        ViewHolder viewHolder = new ViewHolder(displayName);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.empname.setText(dataArrayList.get(position).empname);
        holder.desig.setText(dataArrayList.get(position).desig);
        holder.managername.setText(dataArrayList.get(position).managername);
        holder.empname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater=(LayoutInflater) Myadapter.this.c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView=layoutInflater.inflate(R.layout.activity_options,null);
                final Button updatebt,deletebt,editbt;
                final EditText upempname,updesig,upmanager;
                boolean focusable = true;
                updatebt = popupView.findViewById(R.id.upbt);
                deletebt=popupView.findViewById(R.id.delbt);
                upempname=popupView.findViewById(R.id.upnameet);
                updesig=popupView.findViewById(R.id.updes);
                upmanager=popupView.findViewById(R.id.upman);
                editbt=popupView.findViewById(R.id.edbt);

                PopupWindow popupWindow=new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT, true);
                popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);

                editbt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upempname.setText(String.valueOf(dataArrayList.get(position).empname));
                        updesig.setText(String.valueOf(dataArrayList.get(position).desig));
                        upmanager.setText(String.valueOf(dataArrayList.get(position).managername));
                        upempname.setVisibility(View.VISIBLE);
                        updesig.setVisibility(View.VISIBLE);
                        upmanager.setVisibility(View.VISIBLE);
                        updatebt.setVisibility(View.VISIBLE);
                    }
                });
                updatebt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Mydatabase mydatabase ;
                        mydatabase=new Mydatabase(c);
                        db= mydatabase.getWritableDatabase();
                        String newname = upempname.getText().toString();
                        String newdesig = updesig.getText().toString();
                        String newmanager = upmanager.getText().toString();
                        db.execSQL("update " +Mydatabase.tablename+" set EmpName= '"+newname+"', Designation='"+newdesig+"',Manager='"+newmanager+"'  where EmpName='"+dataArrayList.get(position).empname+"'");
                        Toast.makeText(c,"Successfully Updated",Toast.LENGTH_SHORT).show();
                        db.close();
                    }
                });
                //Myadapter.notifyDataSetChanged();

                deletebt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteData(dataArrayList.get(position).empname);
                        //Myadapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public void deleteData(String emp) {
        Mydatabase mydatabase ;
        mydatabase=new Mydatabase(c);
        db= mydatabase.getWritableDatabase();
       int numberofrowdeleted= db.delete(Mydatabase.tablename,   "EmpName=? ", new String[]{emp});
        //  Log.d("//deleted: ",String.valueOf(username));
          Log.d("//deleted: ", String.valueOf(numberofrowdeleted));
        db.close();
        Toast.makeText(c,"Deleted "+numberofrowdeleted+" Employee Data",Toast.LENGTH_SHORT).show();
    }
    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView empname,desig,managername;

        public ViewHolder(View view) {
            super(view);
            this.empname = view.findViewById(R.id.emptv);
            this.desig = view.findViewById(R.id.desigtv);
            this.managername = view.findViewById(R.id.mantv);
        }
    }
}
