package com.hzy.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzy.base.MainActivity;
import com.hzy.base.R;

import static android.app.Activity.RESULT_OK;

/**
 * 启动页（以fragment的方式）
 * Created by ziye_huang on 2018/7/23.
 */
public class SplashFragment extends Fragment {

    public SplashFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        initData();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (null != getActivity()) {
                    ((MainActivity) getActivity()).splashCallback();
                }
            }
        }, 3000);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
        }
    }
}
