package com.hzy.crash;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 异常管理类
 * Created by ziye_huang on 2018/7/17.
 */
public final class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler crashHandler = new CrashHandler();
    private Context mContext;
    //错误日志
    private File logFile = null;
    private boolean mIsRestartApp = false;//出现异常是否需要重启
    private Activity mSplashActivity;//出现异常需要重启的页面

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        if (null == crashHandler) {
            synchronized (CrashHandler.class) {
                if (null == crashHandler) {
                    crashHandler = new CrashHandler();
                }
            }
        }
        return crashHandler;
    }

    public void init(Context context) {
        mContext = context;
        logFile = new File(mContext.getExternalCacheDir(), "carshLog.trace");
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置为线程默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * uncaughtException回调函数
     *
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 打印异常信息
        ex.printStackTrace();
        // 我们没有处理异常 并且默认异常处理不为空 则交给系统处理
        if (!handlelException(ex) && mDefaultHandler != null) {
            // 系统处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                // 上传错误日志到服务器
                upLoadErrorFileToServer(logFile);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (mIsRestartApp) { // 如果需要重启
                Intent intent = new Intent(mContext.getApplicationContext(), getSplashActivity().getClass());
                AlarmManager mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                //重启应用，得使用PendingIntent
                PendingIntent restartIntent = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                mAlarmManager.set(AlarmManager.RTC, System.currentTimeMillis(), restartIntent); // 重启应用
            }
            // 结束应用
            //            BaseApp.exitApp();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(-1);

            /*Intent intent = new Intent(mContext, SplashActivity.class);
            // 新开任务栈
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);*/
            // 杀死我们的进程
            /*Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            }, 1000);*/

        }
    }

    public Activity getSplashActivity() {
        return mSplashActivity;
    }

    public void setSplashActivity(Activity activity) {
        this.mSplashActivity = activity;
    }

    private boolean handlelException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "程序发生异常，正在退出", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        PrintWriter pw = null;
        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            pw = new PrintWriter(logFile);
            // 收集手机及错误信息
            logFile = collectInfoToSDCard(pw, ex);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 上传错误日志到服务器
     *
     * @param logFile
     * @throws IOException
     */
    private void upLoadErrorFileToServer(File logFile) throws IOException {

    }

    /**
     * 收集手机信息
     *
     * @throws PackageManager.NameNotFoundException
     */
    private File collectInfoToSDCard(PrintWriter pw, Throwable ex) throws PackageManager.NameNotFoundException {

        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        // 错误发生时间
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        pw.print("time : ");
        pw.println(time);
        // 版本信息
        pw.print("versionCode : ");
        pw.println(pi.versionCode);
        // 应用版本号
        pw.print("versionName : ");
        pw.println(pi.versionName);
        try {
            /** 暴力反射获取数据 */
            Field[] Fields = Build.class.getDeclaredFields();
            for (Field field : Fields) {
                field.setAccessible(true);
                pw.print(field.getName() + " : ");
                pw.println(field.get(null).toString());
            }
        } catch (Exception e) {
            Log.i(TAG, "an error occured when collect crash info" + e);
        }

        // 打印堆栈信息
        ex.printStackTrace(pw);
        return logFile;
    }
}
