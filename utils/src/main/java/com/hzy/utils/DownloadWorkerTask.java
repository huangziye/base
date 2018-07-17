package com.hzy.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.webkit.URLUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ziye_huang on 2018/7/17.
 */
public final class DownloadWorkerTask extends AsyncTask<String, Integer, String> {
    private Context context;
    private ProgressDialog progressDialog;
    @Nullable
    private DownloadCallback callback;

    public static class Builder {
        private Context context;
        private DownloadCallback callback;

        public Builder create(Context context) {
            this.context = context;
            return this;
        }

        public Builder addCallback(DownloadCallback callback) {
            this.callback = callback;
            return this;
        }

        public DownloadWorkerTask build() {
            return new DownloadWorkerTask(this);
        }
    }

    private DownloadWorkerTask(Builder builder) {
        this.context = builder.context;
        this.callback = builder.callback;
    }

    /**
     * 该方法有MainThread注解，表示该方法是运行在主线程中的。
     * 在AsyncTask执行了execute()方法后就会在UI线程上执行
     * onPreExecute()方法，该方法在task真正执行前运行，我们通
     * 常可以在该方法中显示一个进度条，从而告知用户后台任务即将开始。
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onPreExecute();

    }

    /**
     * 开始下载
     *
     * @param urls
     */
    public void download(String... urls) {
        execute(urls);
    }

    /**
     * @param params 启动任务执行的输入参数，比如HTTP请求的URL
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        //totalByte表示所有下载的文件的总字节数
        //params是一个String数组
        for (String url : params) {
            //遍历Url数组，依次下载对应的文件
            String result = downloadSingleFile(url);
            //            int byteCount = (int)result[0];
            //            totalByte += byteCount;
            //            //在下载完一个文件之后，我们就把阶段性的处理结果发布出去
            //            publishProgress(result);
            //如果AsyncTask被调用了cancel()方法，那么任务取消，跳出for循环
            if (isCancelled()) {
                break;
            }
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param strUrl
     * @return
     */
    private String downloadSingleFile(final String strUrl) {
        InputStream in = null;
        OutputStream out = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            in = connection.getInputStream();
            //URLUtil.guessFileName(strUrl, "", "")根据url获取要下面文件的文件名
            out = new FileOutputStream(context.getExternalCacheDir().getAbsolutePath() + File.separator + URLUtil.guessFileName(strUrl, "", connection.getContentType()));

            byte[] data = new byte[1024];
            long total = 0;
            int count;
            while ((count = in.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    in.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) {// only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                }
                out.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            CloseUtil.closeQuielty(out, in);
            if (connection != null)
                connection.disconnect();
        }
        return null;
    }


    /**
     * 可以在这里更新progress
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        callback.onProgress(values);
    }

    /**
     * 下载完成会走该方法
     *
     * @param s
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callback.onSuccess(s);
    }

    public interface DownloadCallback {
        void onPreExecute();

        void onProgress(Integer... values);

        void onSuccess(String s);
    }

}
