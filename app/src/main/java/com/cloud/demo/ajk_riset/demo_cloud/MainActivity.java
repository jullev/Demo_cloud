package com.cloud.demo.ajk_riset.demo_cloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button tombol1,tombol2;
    EditText Isian1,Isian2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tombol1 = (Button) findViewById(R.id.button);
        tombol2 = (Button) findViewById(R.id.button2);
        Isian1 = (EditText) findViewById(R.id.editText);
        Isian2 = (EditText) findViewById(R.id.editText2);
        tombol1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}
