package com.ppma.myxposed;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_info = findViewById(R.id.tv_info);
        bt_info = findViewById(R.id.bt_info);
        bt_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_info.setText("点我干啥？");
            }
        });
    }
    private TextView tv_info;
    private Button bt_info;
}
