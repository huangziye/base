package com.hzy.base.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzy.base.R;
import com.hzy.layout.TitleBar;

/**
 * Created by ziye_huang on 2018/7/25.
 */
public class WechatFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, container, false);
        TitleBar bar = view.findViewById(R.id.titleBar);
        TextView tv = view.findViewById(R.id.tv);
//        tv.setText("TESdfdf");
        return view;
    }
}
