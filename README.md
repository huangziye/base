
# 添加 base 到项目

- Step 1：Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```android
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

- Step 2：Add the dependency

```android
dependencies {
        implementation 'com.github.huangziye:base:1.0.0'
}
```


# 常用工具类

- 缓存工具类 ACache
- 异常管理类 CrashHandler
- 设备相关信息管理类 AppUtil
- 字节数组与字符串相互转换工具类 ByteUtil
- 密度相关工具类 DensityUtil
- 日志相关工具类 LogUtil
- 资源相关工具类 ResUtil
- 状态栏相关工具类 StatusBarUtil
- 字符串相关工具类 StringUtil
- 吐司相关工具类 ToastUtil
- 银行卡号合法性校验 BankCardUtil
- 验证手机号是否合法 PhoneNumberUtil
- 日期工具类 DateUtil
- 验证码生成器 DynamicCodeUtil
- UUID工具类 UuidUtil
- 验证邮箱 EmailUtil
- 验证邮编 PostcodeUtil
- Intent相关辅助类 IntentUtil
- File相关辅助类 FileUtil
- DES加解密 DesUtil
- 图片辅助类 ImageUtil
- Http相关工具类 HttpUtil
- zip打包工具类 ZipUtil
- 关闭Closeable对象工具类 CloseUtil
- 文件下载 DownloadWorkerTask





# 常用 Dialog

- 加载对话框 （LoadingDialog）

使用方式

```java
// 显示对话框
LoadingDialog dialog = new LoadingDialog.Builder(this).cancelable(false).cancelOutside(false).isShowMessage(true).setMessage("加载中...").create();
dialog.show();

// 取消对话框
dialog.dismiss();
```


- 下载对话框 （DownloadDialog）

使用方式

```java
// 显示对话框
DownloadDialog dialog = new DownloadDialog.Builder(this).cancelable(false).cancelOutside(false).isShowMessage(true).setMessage("下载中...").create();
dialog.show();

// 取消对话框
dialog.dismiss();
```

- 下载对话框结合下载 (DownloadDialog 和 DownloadWorkerTask)

```java
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
}).build().download("url1", "url2");
```

# About Me

Email: ziye_huang@163.com
简书: https://www.jianshu.com/u/5f3130bdf4fc


# License

```
Copyright 2018, huangziye

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```



