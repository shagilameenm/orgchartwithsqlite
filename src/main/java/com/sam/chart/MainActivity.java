package com.sam.chart;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

  private   WebView webView;
  EditText name,designation,manager;
  Button addbutton,checkbutton;
  Mydatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  webView = findViewById(R.id.web);

        name=findViewById(R.id.nameet);
        designation=findViewById(R.id.designationet);
        manager=findViewById(R.id.manageret);
        addbutton=findViewById(R.id.addbt);
        checkbutton=findViewById(R.id.checkbt);
        mydatabase=new Mydatabase(this);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TextUtils.isEmpty((CharSequence) name);

                savetoDB(name,designation,manager);
                Intent recyclerintent=new Intent(MainActivity.this, Recycleractivity.class);
                startActivity(recyclerintent);
            }
        });
     //   String vname=name.getText().toString();
     //   startWeb();
        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recyclerintent=new Intent(MainActivity.this, Recycleractivity.class);
                startActivity(recyclerintent);
            }
        });
    }
    private void savetoDB(EditText name, EditText designation, EditText manager) {
        String emp = name.getText().toString();
        String des = designation.getText().toString();
        String managername = manager.getText().toString();
        mydatabase.addData(emp,des,managername);
    }

    private void startWeb() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/Org.html");
        webView.setWebViewClient(new WebViewClient());
    }
}
