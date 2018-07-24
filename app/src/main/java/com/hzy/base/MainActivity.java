package com.hzy.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewStub;

import com.hzy.base.fragment.SplashFragment;


public class MainActivity extends AppCompatActivity {

    private ViewStub viewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewStub = (ViewStub) findViewById(R.id.content_viewstub);
        //首先加载并显示splash页面
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SplashFragment()).commit();
        viewStub.inflate();//将viewstub加载进来,记载完毕后控件使用正常

    }
}
