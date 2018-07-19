package com.hzy.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzy.dialog.BottomDialog;

/**
 * Created by ziye_huang on 2018/7/18.
 */
public class BDialog extends BottomDialog {
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_bottom, container,false);
        return view;
    }
}
