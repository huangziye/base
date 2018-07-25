package com.hzy.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.hzy.utils.ResUtil;
import com.hzy.utils.StatusBarUtil;

/**
 * Created by ziye_huang on 2018/7/25.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                StatusBarUtil.setStatusBarColor(activity, ResUtil.getColor(activity, R.color.colorPrimary), 0);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
