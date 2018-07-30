package com.hzy.dialog;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.hzy.R;

/**
 * 通用对话框基类
 * Created by ziye_huang on 2018/7/19.
 */
public abstract class BaseDialog extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置style
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        Window window = getDialog().getWindow();
        //去掉边框
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //        设置 dialog 的宽高
        //        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //        window.setLayout(dm.widthPixels, window.getAttributes().height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 5);
        window.setLayout(setWidth(), setHeight());
        window.setWindowAnimations(R.style.BottomDialogAnimation);
        window.setGravity(setGravity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //去除标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return createView(inflater, container);
    }

    /**
     * 设置对话框宽度
     *
     * @return
     */
    protected abstract int setWidth();

    /**
     * 设置对话框高度
     *
     * @return
     */
    protected abstract int setHeight();

    /**
     * 对话框对齐方式
     *
     * @return
     */
    protected abstract int setGravity();

    /**
     * 重写此方法，设置布局文件
     */
    protected abstract View createView(LayoutInflater inflater, ViewGroup container);

}
