package com.ppma.myxposed;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView hintTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSharedPreferences("xx",0).getString("xx","");

        hintTxt = (TextView)findViewById(R.id.hinttxt);

        findViewById(R.id.savemethod).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }});
    }

}
