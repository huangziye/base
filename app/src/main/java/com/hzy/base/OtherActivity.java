package com.hzy.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ziye_huang on 2018/7/18.
 */
public class OtherActivity extends AppCompatActivity {

    private int mBorderWidth = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        /*WaveView mWaveView = findViewById(R.id.wave);
        int mBorderColor = Color.parseColor("#444fb783");
        mWaveView.setBorder(mBorderWidth, mBorderColor);
        mWaveView.setShapeType(WaveView.ShapeType.CIRCLE);
        WaveHelper mWaveHelper = new WaveHelper(mWaveView);
        mWaveHelper.start();*/
    }
}
