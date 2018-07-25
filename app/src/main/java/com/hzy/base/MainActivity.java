package com.hzy.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewStub;

import com.hzy.base.adapter.TabbarAdapter;
import com.hzy.base.fragment.ContactFragment;
import com.hzy.base.fragment.MineFragment;
import com.hzy.base.fragment.SplashFragment;
import com.hzy.base.fragment.WechatFragment;
import com.hzy.layout.TabBarLayout;
import com.hzy.views.viewpager.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ViewStub viewStub;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewStub = (ViewStub) findViewById(R.id.content_viewstub);
        //首先加载并显示splash页面
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SplashFragment()).commit();
        viewStub.inflate();//将viewstub加载进来,记载完毕后控件使用正常


        TabBarLayout mTabBarLayout = findViewById(R.id.tabbarLayout);
        NoScrollViewPager mViewPager = findViewById(R.id.viewPager);

        fragments.add(new WechatFragment());
        fragments.add(new ContactFragment());
        fragments.add(new MineFragment());

        mViewPager.setAdapter(new TabbarAdapter(getSupportFragmentManager(),fragments));
        mTabBarLayout.setViewPager(mViewPager);
        mTabBarLayout.setUnread(0,20);//设置第一个页签的未读数为20
        mTabBarLayout.setUnread(1,1001);//设置第二个页签的未读数
        mTabBarLayout.showNotify(2);//设置第三个页签显示提示的小红点
//        mTabbarLayout.setMsg(0,"NEW");//设置第四个页签显示NEW提示文字

        mViewPager.setScroll(true);
    }
}
