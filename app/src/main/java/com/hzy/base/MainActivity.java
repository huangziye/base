package com.hzy.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.hzy.utils.DownloadWorkerTask;
import com.hzy.utils.DynamicCodeUtil;
import com.hzy.utils.LogUtil;
import com.hzy.utils.ResUtil;
import com.hzy.utils.ToastUtil;

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

        //版本判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //减少是否拥有权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE + "");
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //弹出对话框接收权限
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
                return;
            }

            //            DownloadWorkerTask downloadWorkerTask = new DownloadWorkerTask(this);
            //            downloadWorkerTask.execute("http://www.zjca.com.cn/web/webs/downloads/drivers/ZCUsbKeySetupwin2000.rar");

            new DownloadWorkerTask.Builder().create(this).addCallback(new DownloadWorkerTask.DownloadCallback() {
                @Override
                public void onPreExecute() {

                }

                @Override
                public void onProgress(Integer... values) {
                    LogUtil.e("tag......", values + "");
                }

                @Override
                public void onSuccess(String s) {
                    ToastUtil.showShort(MainActivity.this, "" + s);
                }
            }).build().download("http://www.zjca.com.cn/web/webs/downloads/drivers/ZCUsbKeySetupwin2000.rar");

        } else {
            //            DownloadWorkerTask downloadWorkerTask = new DownloadWorkerTask(this);
            //            downloadWorkerTask.execute("http://192.168.1.168/attachments/download/961/ZJCAMZT-V1.1.9.20.apk");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
