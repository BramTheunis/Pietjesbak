package com.example.android.pietjesbak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView tv1;
    String st1;
    TextView tv2;
    String st2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv1 = findViewById(R.id.namePlayer1);
        tv2 = findViewById(R.id.namePlayer2);

        st1 = getIntent().getExtras().getString("Value1");
        tv1.setText(st1);
        st2 = getIntent().getExtras().getString("Value2");
        tv2.setText(st2);
    }
}
