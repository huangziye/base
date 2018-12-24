package com.hzy.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ViewStub;
import android.view.WindowManager;

import com.hzy.base.adapter.TabbarAdapter;
import com.hzy.base.fragment.ContactFragment;
import com.hzy.base.fragment.GuideFragment;
import com.hzy.base.fragment.MineFragment;
import com.hzy.base.fragment.SplashFragment;
import com.hzy.base.fragment.WechatFragment;
import com.hzy.cache.ACache;
import com.hzy.layout.TabBarLayout;
import com.hzy.views.viewpager.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ViewStub contentViewStub;
    private List<Fragment> fragments = new ArrayList<>();
    private SplashFragment splashFragment;
    private GuideFragment guideFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentViewStub = findViewById(R.id.content_viewstub);

        //首先加载并显示splash页面
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, splashFragment = new SplashFragment()).commit();
        contentViewStub.inflate();//将viewstub加载进来,记载完毕后控件使用正常

        TabBarLayout mTabBarLayout = findViewById(R.id.tabbarLayout);
        NoScrollViewPager mViewPager = findViewById(R.id.viewPager);

        fragments.add(new WechatFragment());
        fragments.add(new ContactFragment());
        fragments.add(new MineFragment());
//        fragments.add(new RecyclerViewCardGalleryFragment());

        mViewPager.setAdapter(new TabbarAdapter(getSupportFragmentManager(), fragments));
        mTabBarLayout.setViewPager(mViewPager);
        mTabBarLayout.setUnread(0, 20);//设置第一个页签的未读数为20
        mTabBarLayout.setUnread(1, 1001);//设置第二个页签的未读数
        mTabBarLayout.showNotify(2);//设置第三个页签显示提示的小红点
        //        mTabbarLayout.setMsg(0,"NEW");//设置第四个页签显示NEW提示文字

        mViewPager.setScroll(true);
    }

    /**
     * 启动页回调函数
     */
    public void splashCallback() {
        String hasOpened = ACache.get(this).getAsString("hasOpened");
        if (!TextUtils.isEmpty(hasOpened) && hasOpened.equals("true")) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportFragmentManager().beginTransaction().remove(splashFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, guideFragment = new GuideFragment()).commit();
        }
    }

    /**
     * 引导页面回调函数
     */
    public void guideCallbck() {
        ACache.get(this).put("hasOpened", "true");
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportFragmentManager().beginTransaction().remove(guideFragment).commit();
    }
}
