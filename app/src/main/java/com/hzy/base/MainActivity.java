package com.hzy.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hzy.dialog.DownloadDialog;
import com.hzy.utils.DownloadWorkerTask;
import com.hzy.utils.DynamicCodeUtil;
import com.hzy.utils.ResUtil;
import com.hzy.utils.ToastUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_LETTER_ONLY, 4, ""));
        tv.setTextColor(ResUtil.getColor(this, R.color.colorAccent));
    }

    public void download(View view) {

        final DownloadDialog dialog = new DownloadDialog.Builder(this).cancelable(false).cancelOutside(false).isShowMessage(true).setMessage("下载中...").create();
        new DownloadWorkerTask.Builder(this).callback(new DownloadWorkerTask.DownloadCallback() {
            @Override
            public void onPreExecute() {
                dialog.show();
            }

            @Override
            public void onProgress(Integer... values) {
                dialog.setProgress(values[0]);
            }

            @Override
            public void onSuccess(List<String> paths, String s) {
                dialog.dismiss();
                ToastUtil.showShort(MainActivity.this, "下载成功");
            }
        }).build().download("http://www.zjca.com.cn/web/webs/downloads/drivers/ZCUsbKeySetupwin2000.rar", "http://www.zjca.com.cn/web/webs/downloads/drivers/ZCUsbKeySetup.rar");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
