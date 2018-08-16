package com.hzy.base.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzy.base.R;
import com.hzy.layout.progress.ProgressLinearLayout;

/**
 * Created by ziye_huang on 2018/8/16.
 */
public class ProgressLayoutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_layout, container, false);
        final ProgressLinearLayout progressLinearLayout = view.findViewById(R.id.progressLinearLayout);
        progressLinearLayout.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressLinearLayout.showEmpty(getLayoutInflater().inflate(R.layout.rv_error_layout, null));
            }
        }, 10000);
        return view;
    }
}
