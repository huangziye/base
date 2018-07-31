package com.hzy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzy.views.R;
import com.hzy.tools.ResUtil;

/**
 * Created by ziye_huang on 2018/7/18.
 */
public final class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {

        private Context context;
        private String message;
        private boolean isShowMessage = true;
        private boolean isCancelable = false;
        private boolean isCancelOutside = false;
        private int bgColor = -1;
        private int textColor = -1;
        private int textSize = -1;


        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示信息
         *
         * @param message
         * @return
         */

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 设置是否显示提示信息
         *
         * @param isShowMessage
         * @return
         */
        public Builder isShowMessage(boolean isShowMessage) {
            this.isShowMessage = isShowMessage;
            return this;
        }

        /**
         * 设置是否可以按返回键取消
         *
         * @param isCancelable
         * @return
         */

        public Builder cancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        /**
         * 设置是否可以取消
         *
         * @param isCancelOutside
         * @return
         */
        public Builder cancelOutside(boolean isCancelOutside) {
            this.isCancelOutside = isCancelOutside;
            return this;
        }

        /**
         * 设置对话框背景
         *
         * @param bgColor
         * @return
         */
        public Builder bgColor(@DrawableRes int bgColor) {
            this.bgColor = bgColor;
            return this;
        }

        /**
         * 设置字体大小
         *
         * @param textSize
         * @return
         */
        public Builder textSize(@DimenRes int textSize) {
            this.textSize = textSize;
            return this;
        }

        /**
         * 设置字体颜色
         *
         * @param textColor
         * @return
         */
        public Builder textColor(@IntegerRes int textColor) {
            this.textColor = textColor;
            return this;
        }

        public LoadingDialog create() {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
            LoadingDialog loadingDailog = new LoadingDialog(context, R.style.LoadingDialogStyle);
            LinearLayout llLoadingDialog = view.findViewById(R.id.ll_loading_dialog);
            TextView msgText = (TextView) view.findViewById(R.id.tipTextView);
            llLoadingDialog.setBackgroundDrawable(ResUtil.getDrawable(context, -1 == bgColor ? R.drawable.bg_loading_dialog : bgColor));
            if (isShowMessage) {
                if (-1 != textSize) {
                    msgText.setTextSize(textSize);
                }
                if (-1 != textColor) {
                    msgText.setTextColor(textColor);
                }
                msgText.setText(message);
            } else {
                msgText.setVisibility(View.GONE);
            }
            loadingDailog.setContentView(view);
            loadingDailog.setCancelable(isCancelable);
            loadingDailog.setCanceledOnTouchOutside(isCancelOutside);
            return loadingDailog;
        }


    }
}
