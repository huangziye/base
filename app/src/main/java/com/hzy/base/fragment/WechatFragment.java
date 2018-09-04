package com.hzy.base.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzy.base.R;
import com.hzy.layout.TitleBar;
import com.hzy.utils.ViewUtil;
import com.hzy.views.rolling.VerticalRollingTextView;
import com.hzy.views.rolling.adapter.VerticalRollingTextAdapter;

import java.util.Arrays;

/**
 * Created by ziye_huang on 2018/7/25.
 */
public class WechatFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, container, false);
        TitleBar bar = view.findViewById(R.id.titleBar);


        ViewUtil.setText(view, R.id.tv, "http://www.baidu.com 或 hzy@zjca.com.cn");
        ViewUtil.setTextColor(view, R.id.tv, Color.RED);
        ViewUtil.setChecked(view, R.id.cb, true);
        ViewUtil.linkify(view, R.id.tv);
        ViewUtil.setTypeface(view, R.id.tv, Typeface.SERIF);
        ViewUtil.setImageResource(view, R.id.iv, R.mipmap.ic_launcher);

        ViewUtil.setWindowBackgroundDim(getActivity());

        VerticalRollingTextView verticalRollingTextView = view.findViewById(R.id.verticalRollingTextView);

        verticalRollingTextView.setAdapter(new VerticalRollingTextAdapter<String>(Arrays.asList("aaaaaaa", "bbbbbbb")) {
            @Override
            protected CharSequence text(String str) {
                return str;
            }
        });
        verticalRollingTextView.run();
        return view;
    }
}
