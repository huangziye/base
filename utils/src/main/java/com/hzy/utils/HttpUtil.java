package com.hzy.utils;

import android.annotation.TargetApi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Http 相关工具类
 * Created by ziye_huang on 2018/7/17.
 */
public class HttpUtil {

    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String CONTENT_TYPE_HTML_FORM = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_JSON = "application/json";

    public static String CONTENT_TYPE_FILE_PREFIX = "multipart/form-data; boundary=";

    private static final String APPEND_CHARSET = "; charset=";
    private static final String LINE_FEED = "\r\n";
    private final String req_method;
    private final String boundary;
    private String reqUrl;
    private String saveDir;
    private String reqCharset;
    private String respCharset;
    private HttpURLConnection httpConn;
    private OutputStream outStm;
    private PrintWriter prtWrite;
    private boolean isFormData = false;

    private HttpUtil() {
        throw new AssertionError("No Instance.");
    }

    /**
     * POST 请求，模拟原始字符串数据提交
     *
     * @param reqUrl      请求地址
     * @param reqHeader   请求头
     * @param params      请求参数
     * @param files       上传的文件 <code>Map&lt;fieldName&lt;list files&gt;&gt;</code>
     * @param reqCharset  请求提交时编码格式，默认：UTF-8
     * @param respCharset 请求返回时编码格式，默认：UTF-8
     * @return 请求返回结果
     */
    public static String postFormData(String reqUrl, Map<String, String> reqHeader, Map<String, String> params, Map<String, List<File>> files, String reqCharset, String respCharset) {

        if (reqHeader == null) {
            reqHeader = new HashMap<>();
        }
        reqHeader.put(CONTENT_TYPE_KEY, HttpUtil.CONTENT_TYPE_FILE_PREFIX);

        HttpUtil httpUtil = new HttpUtil(reqUrl, "POST", reqHeader, reqCharset, respCharset);

        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                httpUtil.addFormDataText(entry.getKey(), entry.getValue());
            }
        }

        if (files != null && !files.isEmpty()) {
            for (Map.Entry<String, List<File>> entry : files.entrySet()) {
                String fieldName = entry.getKey();
                List<File> fileList = entry.getValue();
                for (File item : fileList) {
                    httpUtil.addFormDataFile(fieldName, item);
                }
            }
        }

        return httpUtil.request();
    }

    /**
     * POST 请求，模拟 JSON 字符串数据提交
     *
     * @param reqUrl      请求地址
     * @param reqHeader   请求头
     * @param json        请求参数，JSON 格式字符串
     * @param reqCharset  请求提交时编码格式，默认：UTF-8
     * @param respCharset 请求返回时编码格式，默认：UTF-8
     * @return 请求返回结果
     */
    public static String postJson(String reqUrl, Map<String, String> reqHeader, String json, String reqCharset, String respCharset) {

        if (reqHeader == null) {
            reqHeader = new HashMap<>();
        }
        reqHeader.put(CONTENT_TYPE_KEY, HttpUtil.CONTENT_TYPE_JSON);

        HttpUtil httpUtil = new HttpUtil(reqUrl, "POST", reqHeader, reqCharset, respCharset);

        if (json != null && !json.isEmpty()) {
            httpUtil.addRaw(json);
        }

        return httpUtil.request();
    }

    /**
     * POST 请求，模拟 HTML 表单方式的提交
     *
     * @param reqUrl      请求地址
     * @param reqHeader   请求头
     * @param params      请求参数
     * @param reqCharset  请求提交时编码格式，默认：UTF-8
     * @param respCharset 请求返回时编码格式，默认：UTF-8
     * @return 请求返回结果
     */
    public static String postHtmlForm(String reqUrl, Map<String, String> reqHeader, Map<String, Object> params, String reqCharset, String respCharset) {

        if (reqHeader == null) {
            reqHeader = new HashMap<>();
        }
        reqHeader.put(CONTENT_TYPE_KEY, HttpUtil.CONTENT_TYPE_HTML_FORM);

        HttpUtil httpUtil = new HttpUtil(reqUrl, "POST", reqHeader, reqCharset, respCharset);

        if (params != null && !params.isEmpty()) {
            httpUtil.addXWwwFormUrlencoded(params);
        }

        return httpUtil.request();
    }

    /**
     * GET 请求
     *
     * @param reqUrl      请求地址
     * @param reqHeader   请求头
     * @param params      请求参数
     * @param reqCharset  请求提交时编码格式，默认：UTF-8
     * @param respCharset 请求返回时编码格式，默认：UTF-8
     * @return 请求返回结果
     */
    public static String get(String reqUrl, Map<String, String> reqHeader, Map<String, Object> params, String reqCharset, String respCharset) {

        HttpUtil httpUtil = constructorForGet(reqUrl, reqHeader, params, reqCharset, respCharset);

        return httpUtil.request();
    }

    /**
     * GET 请求，下载文件
     *
     * @param reqUrl      请求地址
     * @param reqHeader   请求头
     * @param params      请求参数
     * @param reqCharset  请求提交时编码格式，默认：UTF-8
     * @param respCharset 请求返回时编码格式，默认：UTF-8
     * @param saveDir     文件保存路径
     * @return 请求返回结果
     */
    public static String getFile(String reqUrl, Map<String, String> reqHeader, Map<String, Object> params, String reqCharset, String respCharset, String saveDir) {

        HttpUtil httpUtil = constructorForGet(reqUrl, reqHeader, params, reqCharset, respCharset);
        httpUtil.reqUrl = reqUrl;
        httpUtil.saveDir = saveDir;

        return httpUtil.request();
    }

    /**
     * GET 请求对象构造器
     *
     * @param reqUrl      请求地址
     * @param reqHeader   请求头
     * @param params      请求参数
     * @param reqCharset  请求提交时编码格式，默认：UTF-8
     * @param respCharset 请求返回时编码格式，默认：UTF-8
     * @return HttpUtil 实例
     */
    public static HttpUtil constructorForGet(String reqUrl, Map<String, String> reqHeader, Map<String, Object> params, String reqCharset, String respCharset) {

        if (params != null && !params.isEmpty()) {
            // 若存在请求参数，获取请求参数字符串
            String query = mapToQueryString(params);
            // 判断原请求地址中是否包含参数
            String[] urlParts = reqUrl.split("\\?");
            if (urlParts.length > 1) {
                // 若请求地址包含请求参数
                reqUrl = reqUrl + "&" + query;

            } else {
                reqUrl = reqUrl + "?" + query;
            }
        }

        return new HttpUtil(reqUrl, "GET", reqHeader, reqCharset, respCharset);
    }


    /**
     * @param reqUrl      请求服务器地址
     * @param reqMethod   请求方法，例如：GET/POST
     * @param reqHeader   请求头信息
     * @param reqCharset  请求提交时编码格式，默认：UTF-8
     * @param respCharset 请求返回时编码格式，默认：UTF-8
     */
    @TargetApi(19)
    public HttpUtil(String reqUrl, String reqMethod, Map<String, String> reqHeader, String reqCharset, String respCharset) {
        // 默认编码设置
        this.reqCharset = reqCharset == null ? StandardCharsets.UTF_8.toString() : reqCharset;
        this.respCharset = respCharset == null ? StandardCharsets.UTF_8.toString() : respCharset;

        this.reqUrl = reqUrl;
        this.req_method = reqMethod;

        // 请求服务器地址
        String host = reqUrl;
        // 经过 URL 编码的请求参数
        String query = null;

        // 获取服务器地址和请求参数
        String[] urlParts = reqUrl.split("\\?");
        if (urlParts.length > 1) {
            host = urlParts[0];
            query = mapToUrlEncodingQueryString(getQueryMap(urlParts[1]), this.reqCharset);
        }

        if (query != null && query.trim().length() > 0) {
            // 构造包含请求参数的目标地址
            reqUrl = host + "?" + query;
        }

        // 根据时间戳创建唯一边界参数
        boundary = "----" + System.currentTimeMillis();

        try {
            URL url = new URL(reqUrl);
            httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setRequestMethod(reqMethod);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(!"GET".equals(reqMethod)); // GET 请求没有输出流
            httpConn.setUseCaches(false);
            httpConn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
            httpConn.setRequestProperty("connection", "keep-alive");
            httpConn.setRequestProperty("accept", "*/*");

            // 添加额外的请求头信息
            if (reqHeader != null && !reqHeader.isEmpty()) {
                for (Map.Entry<String, String> entry : reqHeader.entrySet()) {

                    if (entry.getKey().equals(CONTENT_TYPE_KEY) && entry.getValue().equals(CONTENT_TYPE_FILE_PREFIX)) {
                        httpConn.setRequestProperty(entry.getKey(), CONTENT_TYPE_FILE_PREFIX + boundary);

                    } else if (entry.getKey().equals(CONTENT_TYPE_KEY)) {
                        httpConn.setRequestProperty(entry.getKey(), entry.getValue() + APPEND_CHARSET + this.reqCharset);

                    } else {
                        httpConn.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
            }

            if (!"GET".equals(reqMethod)) {
                // GET 请求没有输出流
                outStm = httpConn.getOutputStream();
                prtWrite = new PrintWriter(new OutputStreamWriter(outStm, this.reqCharset), true);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 向请求体中添加 HTML 表单类型的参数
     * <p>
     * 请求头为 <code>Content-Type: {@link HttpUtil#CONTENT_TYPE_HTML_FORM}</code>
     *
     * @param kvMap 键值对
     */
    public void addXWwwFormUrlencoded(Map<String, Object> kvMap) {

        if ("GET".equals(req_method)) {
            throw new RuntimeException("The addXWwwFormUrlencoded not support GET method.");
        }

        prtWrite.append(mapToUrlEncodingQueryString(kvMap, this.reqCharset));
        prtWrite.flush();
    }

    /**
     * 向请求体中添加原始类型的字符串参数
     * <p>
     * 无请求头
     *
     * @param val 字符串值，比如：JSON 字符串
     */
    public void addRaw(String val) {

        if ("GET".equals(req_method)) {
            throw new RuntimeException("The addRaw not support GET method.");
        }

        prtWrite.append(val);
        prtWrite.flush();
    }

    /**
     * 向请求体中添加文件类型的文本参数
     * <p>
     * 请求头为 <code>Content-Type: {@link HttpUtil#CONTENT_TYPE_FILE_PREFIX}</code>
     *
     * @param name 属性名
     * @param val  属性值
     */
    public void addFormDataText(String name, String val) {

        if ("GET".equals(req_method)) {
            throw new RuntimeException("The addFormDataText not support GET method.");
        }

        prtWrite.append("--").append(boundary).append(LINE_FEED);
        prtWrite.append("Content-Disposition: form-data; name=\"").append(name).append("\"").append(LINE_FEED);
        prtWrite.append("Content-Type: text/plain; charset=").append(reqCharset).append(LINE_FEED);
        prtWrite.append(LINE_FEED);
        prtWrite.append(val).append(LINE_FEED);
        prtWrite.flush();

        isFormData = true;
    }

    /**
     * 向请求体中添加文件类型的文件参数
     * <p>
     * 请求头为 <code>Content-Type: {@link HttpUtil#CONTENT_TYPE_FILE_PREFIX}</code>
     *
     * @param fieldName  <code>&lt;input type="file" name="..." /&gt;</code> 中的 <code>name</code> 属性值
     * @param uploadFile 需要上传的文件
     */
    @TargetApi(19)
    public void addFormDataFile(String fieldName, File uploadFile) {

        if ("GET".equals(req_method)) {
            throw new RuntimeException("The addFormDataFile not support GET method.");
        }

        String filename = uploadFile.getName();

        prtWrite.append("--").append(boundary).append(LINE_FEED);
        prtWrite.append("Content-Disposition: form-data; name=\"").append(fieldName).append("\"; filename=\"").append(filename).append("\"").append(LINE_FEED);
        prtWrite.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(filename)).append(LINE_FEED);
        prtWrite.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        prtWrite.append(LINE_FEED);
        prtWrite.flush();

        try (FileInputStream inStm = new FileInputStream(uploadFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inStm.read(buffer)) != -1) {
                outStm.write(buffer, 0, bytesRead);
            }
            outStm.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);

        }

        prtWrite.append(LINE_FEED);
        prtWrite.flush();

        isFormData = true;
    }

    /**
     * 提交请求并获取返回信息
     *
     * @return 返回信息
     */
    @TargetApi(19)
    public String request() {

        if (isFormData) {
            // 若是文件上传，则填补下边界
            prtWrite.append(LINE_FEED).flush();
            prtWrite.append("--").append(boundary).append("--").append(LINE_FEED);
            prtWrite.close(); // 提交 form-data 中的内容
        }

        // HTTPS 请求
        if (httpConn instanceof HttpsURLConnection) {
            HttpsURLConnection httpsConn;
            try {
                // 针对 HTTPS 请求的处理，绕过证书验证过程
                httpsConn = (HttpsURLConnection) httpConn;

                // 忽略证书验证过程，忽略之后，任何 HTTPS 协议网站皆能正常访问
                // 使用忽略主机名验证器
                httpsConn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                });
                // SSL 配置
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }}, null);
                httpsConn.setSSLSocketFactory(sslContext.getSocketFactory());

                httpConn = httpsConn;

            } catch (KeyManagementException | NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        // 请求返回的输入流
        try (InputStream inStm = httpConn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST ? httpConn.getInputStream() : httpConn.getErrorStream()) {

            if (this.saveDir != null && !this.saveDir.isEmpty()) {
                return download(inStm);
            }

            return inputStreamToString(inStm);

        } catch (IOException e) {
            throw new RuntimeException(e);

        } finally {

            httpConn.disconnect();
        }
    }

    /**
     * 下载文件
     *
     * @param inStm {@link InputStream}
     * @throws IOException IO 异常
     */
    @TargetApi(19)
    private String download(InputStream inStm) throws IOException {

        String fileName = "";
        String disposition = httpConn.getHeaderField("Content-Disposition");
        String contentType = httpConn.getContentType();

        if (disposition != null) {
            // extracts file name from header field
            int index = disposition.indexOf("filename=");
            if (index > 0) {
                fileName = disposition.substring(index + 10, disposition.length() - 1);
            }
        } else {
            // extracts file name from URL
            fileName = reqUrl.substring(reqUrl.lastIndexOf("/") + 1, reqUrl.length());
        }

        // 判断是否存在后缀名
        if (!fileName.contains(".")) {
            // 获取文件后缀（）
            String extension = contentType.substring(contentType.lastIndexOf("/") + 1);
            fileName += ("(" + contentType.replace("/", ",") + ")." + extension);
        }

        // opens input stream from the HTTP connection
        String saveFilePath = saveDir + File.separator + fileName;

        // opens an output stream to save into file
        try (BufferedInputStream bufInStm = new BufferedInputStream(inStm); FileOutputStream outStm = new FileOutputStream(saveFilePath); BufferedOutputStream bufOutStm = new BufferedOutputStream(outStm)) {
            int ch;
            while ((ch = bufInStm.read()) != -1) {
                bufOutStm.write(ch);
            }
        }

        return "To download the files to: " + saveFilePath;
    }

    /**
     * 将返回流转入字符串
     *
     * @param inStm {@link InputStream}
     * @return 字符串
     */
    @TargetApi(19)
    private String inputStreamToString(InputStream inStm) {
        // 将返回的输入流转换成字符串
        StringBuilder strBuild = new StringBuilder();
        String line;
        try (InputStreamReader inStmRead = new InputStreamReader(inStm, respCharset); BufferedReader bufferRead = new BufferedReader(inStmRead)) {

            while ((line = bufferRead.readLine()) != null) {
                strBuild.append(line);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);

        }

        return strBuild.toString();
    }

    /**
     * 获取 URL 中的参数
     *
     * @param query 请求地址 ? 后的参数字符串（host+?+<code>query</code>）
     * @return params
     */
    private static Map<String, Object> getQueryMap(String query) {

        String[] params = query.split("&");

        Map<String, Object> map = new HashMap<>();

        for (String item : params) {
            String[] kv = item.split("=");
            map.put(kv[0], kv[1]);
        }

        return map;
    }

    /**
     * 获取经过 URL 编码（百分号编码）的地址栏参数字符串
     *
     * @param kvMap   键值对
     * @param charset 编码格式
     * @return 以 & 分隔且被 URL 编码后的参数字符串
     */
    @TargetApi(24)
    private static String mapToUrlEncodingQueryString(Map<String, Object> kvMap, String charset) {
        // 构造请求参数字符串
        StringJoiner stringJoiner = new StringJoiner("&");

        if (kvMap != null && !kvMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : kvMap.entrySet()) {
                // 以 HTML 表单字符处理参数，以指定编码格式正进传输
                try {
                    stringJoiner.add(URLEncoder.encode(entry.getKey(), charset) + "=" + URLEncoder.encode(entry.getValue() == null ? "" : entry.getValue().toString(), charset));

                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return stringJoiner.toString();
    }

    /**
     * 获取地址栏参数字符串
     *
     * @param kvMap 键值对
     * @return 以 & 分隔且被 URL 编码后的参数字符串
     */
    @TargetApi(24)
    private static String mapToQueryString(Map<String, Object> kvMap) {

        // 构造请求参数字符串
        StringJoiner stringJoiner = new StringJoiner("&");

        if (kvMap != null && !kvMap.isEmpty()) {
            for (Map.Entry<String, Object> entry : kvMap.entrySet()) {
                // 以 HTML 表单字符处理参数，以指定编码格式正进传输
                stringJoiner.add(entry.getKey() + "=" + entry.getValue());

            }
        }

        return stringJoiner.toString();
    }
}
