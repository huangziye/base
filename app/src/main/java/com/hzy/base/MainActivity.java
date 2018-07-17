package com.hzy.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hzy.utils.DynamicCodeUtil;
import com.hzy.utils.ResUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_LETTER_ONLY, 4, ""));
        tv.setTextColor(ResUtil.getColor(this, R.color.colorAccent));
    }
}
