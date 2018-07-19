package com.hzy.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.hzy.R;
import com.hzy.views.progress.CircleProgressView;

/**
 * Created by ziye_huang on 2018/7/18.
 */
public final class DownloadDialog extends AlertDialog {

    static CircleProgressView circleProgressView;

    protected DownloadDialog(Context context) {
        super(context);
    }

    protected DownloadDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DownloadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setProgress(int progress) {
        circleProgressView.setProgress(progress);
    }

    public static class Builder {
        private Context context;
        private String message;
        private boolean isShowMessage = true;
        private boolean isCancelable = false;
        private boolean isCancelOutside = false;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示信息
         *
         * @param message
         * @return
         */

        public DownloadDialog.Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 设置是否显示提示信息
         *
         * @param isShowMessage
         * @return
         */
        public DownloadDialog.Builder isShowMessage(boolean isShowMessage) {
            this.isShowMessage = isShowMessage;
            return this;
        }

        /**
         * 设置是否可以按返回键取消
         *
         * @param isCancelable
         * @return
         */

        public DownloadDialog.Builder cancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        /**
         * 设置是否可以取消
         *
         * @param isCancelOutside
         * @return
         */
        public DownloadDialog.Builder cancelOutside(boolean isCancelOutside) {
            this.isCancelOutside = isCancelOutside;
            return this;
        }

        public DownloadDialog create() {
            /*View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
            DownloadDialog loadingDailog = new DownloadDialog(context, R.style.LoadingDialogStyle);
            TextView msgText = (TextView) view.findViewById(R.id.tipTextView);
            if (isShowMessage) {
                msgText.setText(message);
            } else {
                msgText.setVisibility(View.GONE);
            }
            loadingDailog.setContentView(view);
            loadingDailog.setCancelable(isCancelable);
            loadingDailog.setCanceledOnTouchOutside(isCancelOutside);*/




            View view = LayoutInflater.from(context).inflate(R.layout.dialog_cirle_ring_progress, null);
            DownloadDialog downloadDialog = new DownloadDialog(context, R.style.LoadingDialogStyle);
             circleProgressView = view.findViewById(R.id.circleProgressView);
            //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
           /* android.view.WindowManager.LayoutParams layoutParams = downloadDialog.getWindow().getAttributes();  //获取对话框当前的参数值
            layoutParams.height = DensityUtil.dp2px(context, 150);   //高度设置为屏幕的0.3
            layoutParams.width = DensityUtil.dp2px(context, 150);    //宽度设置为屏幕的0.5
            downloadDialog.getWindow().setAttributes(layoutParams);     //设置生效
            downloadDialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()*/
//            downloadDialog.setContentView(view);
            downloadDialog.setView(view);
            downloadDialog.setCancelable(isCancelable);
            downloadDialog.setCanceledOnTouchOutside(isCancelOutside);

            return downloadDialog;
        }


    }
}
