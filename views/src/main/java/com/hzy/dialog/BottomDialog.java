package com.hzy.dialog;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzy.utils.AppUtil;

/**
 * 底部对话框
 * Created by ziye_huang on 2018/7/18.
 */
public abstract class BottomDialog extends BaseDialog {

    @Override
    protected int setWidth() {
        return AppUtil.getScreenWidth(getActivity());
    }

    @Override
    protected int setHeight() {
        return AppUtil.getScreenHeight(getActivity()) * 3 / 5;
    }

    @Override
    protected int setGravity() {
        return Gravity.BOTTOM;
    }

    /**
     * 重写此方法，设置布局文件
     */
    protected abstract View createView(LayoutInflater inflater, ViewGroup container);

}
