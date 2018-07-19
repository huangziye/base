package com.hzy.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hzy.dialog.LoadingDialog;
import com.hzy.utils.ResUtil;
import com.hzy.utils.VersionUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(VersionUtil.compareVersion("1.22.232", "1.22.34") + "");
        tv.setTextColor(ResUtil.getColor(this, R.color.colorAccent));
    }

    public void download(View view) {

        LoadingDialog dialog = new LoadingDialog.Builder(this).isShowMessage(true).setMessage("加载中...").cancelable(true).cancelOutside(true).create();
        dialog.show();
        /*final DownloadDialog dialog = new DownloadDialog.Builder(this).cancelable(false).cancelOutside(false).isShowMessage(true).setMessage("下载中...").create();
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
        }).build().download("http://www.zjca.com.cn/web/webs/downloads/drivers/ZCUsbKeySetupwin2000.rar", "http://www.zjca.com.cn/web/webs/downloads/drivers/ZCUsbKeySetup.rar");*/

        /*BDialog dialog = new BDialog();
        dialog.show(getSupportFragmentManager(),"dialog");*/

        //        startActivity(new Intent(this,OtherActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
