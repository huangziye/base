# Common tools

- 缓存工具类 ACacheC
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
- 版本相关辅助工具类 VersionUtil
- 顺时针、水平方向的半圆周运动 SemicircleAnimation
- 倒计时 CountDownTimerUtil
- Uri相关工具类 UriUtil
- Button 相关工具类 ButtonUtil





# Common Dialog

- Load dialog （LoadingDialog）

**effect picture：**

![加载效果图](https://github.com/huangziye/base/blob/master/screenshot/loading.gif)

usage mode

```java
// 显示对话框
LoadingDialog dialog = new LoadingDialog.Builder(this).cancelable(false).cancelOutside(false).isShowMessage(true).setMessage("加载中...").create();
dialog.show();

// 取消对话框
dialog.dismiss();
```


- Download dialog （DownloadDialog）

usage

```java
// 显示对话框
DownloadDialog dialog = new DownloadDialog.Builder(this).cancelable(false).cancelOutside(false).isShowMessage(true).setMessage("下载中...").create();
dialog.show();

// 取消对话框
dialog.dismiss();
```

- Download the dialog together with download (DownloadDialog 和 DownloadWorkerTask)

**effect picture：**

![下载效果图](https://github.com/huangziye/base/blob/master/screenshot/download.gif)

usage

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


- Bottom Dialog (BottomDialog)

usage

- Inherited from `BottomDialog`
- Override `createView` method
- Show dialog `new XXXDialog().show(getSupportFragmentManager(),"dialog");`




- Base dialog

usage

It is a abstract base class. Custom dialogs are inherited from BaseDialog  and override method.

- Inherited from `BaseDialog`
- Override method
- Show dialog `new XXXDialog().show(getSupportFragmentManager(),"dialog");`



# Wave view （WaveView）

**effect picture：**

![下载效果图](https://github.com/huangziye/base/blob/master/screenshot/wave.gif)

usage

- xml

```xml
<com.hzy.views.waveview.WaveView
    android:id="@+id/wave"
    android:layout_width="200dp"
    android:layout_height="200dp"/>
```

- start

```java
mWaveView.setBorder(mBorderWidth, mBorderColor);
mWaveView.setShapeType(WaveView.ShapeType.CIRCLE);
WaveHelper mWaveHelper = new WaveHelper(mWaveView);
mWaveHelper.start();
```

- cancel

```java
mWaveHelper.cancel();
```


# DynamicWave view （DynamicWaveView）
dynamic
**effect picture：**

![下载效果图](https://github.com/huangziye/base/blob/master/screenshot/wave.gif)



# SemicircleAnimation

```java
// 5 相对于自己在x轴上移动的距离（相对于自身宽度）
SemicircleAnimation anim = new SemicircleAnimation(0,5);
anim.setStartOffset(300);
anim.setInterpolator(new LinearInterpolator());
anim.setFillAfter(true);
anim.setDuration(500);
imageView.startAnimation(anim);



android:duration：设置动画持续时间
android:fillAfter：如果fillAfter设为true，则动画执行后，控件将停留在动画结束的状态
android:fillBefore：如果fillBefore设为true，则动画执行后，控件将回到动画开始的状态
android:startOffset(long startOffset)：设置动画执行之前等待的时间（单位：毫秒）
android:repeatCount(int repeatCount)：设置动画重复的次数
android:interpolator：设置动画的变化速度
```


# 底部导航布局 （TabBarLayout 和 TabBarItem）

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              xmlns:app="http://schemas.android.com/apk/res-auto"
              android:id="@+id/content"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:orientation="vertical">

    <android.support.v4.view.ViewPager
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"/>

    <com.hzy.layout.TabBarLayout
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:layout_gravity="center"
        android:gravity="center"
        android:orientation="horizontal">

        <com.hzy.layout.TabBarItem
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            app:iconHeight="25dp"
            app:iconNormal="@mipmap/icon_wechat_normal"
            app:iconSelected="@mipmap/icon_wechat_checked"
            app:iconWidth="25dp"
            app:itemMarginTop="2dp"
            app:itemText="微信"
            app:itemTextSize="10dp"
            app:openTouchBg="false"
            app:textColorNormal="@color/tabNormalColor"
            app:textColorSelected="@color/tabSelectedColor"
            app:touchDrawable="@drawable/tabbar_selector_bg"/>

        <com.hzy.layout.TabBarItem
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            app:iconHeight="25dp"
            app:iconNormal="@mipmap/icon_contact_normal"
            app:iconSelected="@mipmap/icon_contact_checked"
            app:iconWidth="25dp"
            app:itemMarginTop="2dp"
            app:itemText="通讯录"
            app:itemTextSize="10dp"
            app:openTouchBg="false"
            app:textColorNormal="@color/tabNormalColor"
            app:textColorSelected="@color/tabSelectedColor"
            app:touchDrawable="@drawable/tabbar_selector_bg"
            app:unreadThreshold="999"/>


        <com.hzy.layout.TabBarItem
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            app:iconHeight="25dp"
            app:iconNormal="@mipmap/icon_mine_normal"
            app:iconSelected="@mipmap/icon_mine_checked"
            app:iconWidth="25dp"
            app:itemMarginTop="2dp"
            app:itemText="我"
            app:itemTextSize="10dp"
            app:openTouchBg="false"
            app:textColorNormal="@color/tabNormalColor"
            app:textColorSelected="@color/tabSelectedColor"
            app:touchDrawable="@drawable/tabbar_selector_bg"/>

    </com.hzy.layout.TabBarLayout>

</LinearLayout>
```

**开启滑动效果**

页签之间的切换默认关闭了滑动效果，如果需要开启可以通过调用 TabBarLayout 的 `setSmoothScroll()` 方法:

```
mTabBarLayout.setSmoothScroll(true);
```

也可以在布局文件中指定 TabBarLayout 的 `smoothScroll` 属性为 `true`。

**设置条目选中的监听**

```
mTabBarLayout.setOnItemSelectedListener(new TabBarLayout.OnItemSelectedListener() {
    @Override
    public void onItemSelected(final TabBarItem tabBarItem, int position) {
           //do something
    }
});
```


**显示未读数、提示小红点、提示消息**

```
mTabBarLayout.setUnread(0,20);//设置第一个页签的未读数为20
mTabBarLayout.setUnread(1,101);//设置第二个页签的未读数
mTabBarLayout.showNotify(2);//设置第三个页签显示提示的小红点
mTabBarLayout.setMsg(3,"NEW");//设置第四个页签显示NEW提示文字
```

当设置的未读数小于或等于0时，消失未读数的小红点将会消失；

当未读数为1-99时，则显示对应的数字；

当未读数大于99时，显示99+；

**设置未读数阈值**

未读数的阈值可以指定 TabBarItem 的 `unreadThreshold` 属性设置，默认该值为99，如设置 `app:unreadThreshold="999"` , 若未读数超过该值，则显示"999+"。

隐藏提示小红点、提示消息。

```
mTabBarLayout.hideNotify(2);//隐藏第三个页签显示提示的小红点
mTabBarLayout.hideMsg(3);//隐藏第四个页签显示的提示文字
```


**设置未读数字体颜色**

```
app:unreadTextColor="@color/unreadTextColor"
```


**设置未读数背景**

```
app:unreadTextBg="@drawable/shape_unread"
```

drawable的编写如下：

```
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <corners android:radius="20dp" />
    <solid android:color="@color/red" />
    <padding
        android:left="4dp"
        android:right="4dp"
        android:top="1dp"
        android:bottom="1dp"/>
</shape>
```


**设置提示文字字体颜色、背景**


```
 app:msgTextColor="@color/msgTextColor"
 app:msgTextBg="@drawable/shape_msg"
 ```


**设置提示点背景**

```
app:notifyPointBg="@drawable/shape_notify_point"
```

# VerticalRollingTextView 使用

```xml
<com.hzy.views.rolling.VerticalRollingTextView
    android:id="@+id/verticalRollingView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center_vertical"
    android:layout_marginLeft="10dp"
    android:layout_marginRight="10dp"
    android:layout_weight="1"
    android:duration="1000"
    android:ellipsize="end"
    android:maxLines="1"
    android:textColor="@color/white"
    android:textSize="@dimen/_14dp"
    app:animInterval="2000"
    app:maxTextSize="@dimen/_14dp"
    app:minTextSize="@dimen/_6dp"/>
```

**java代码**

```java
mVerticalRollingTextView.setAdapter(new VerticalRollingTextAdapter<Ads>(adsList) {
    @Override
    protected CharSequence text(Ads ads) {
        return ads.getTitle();
    }
});
mVerticalRollingTextView.run();
```

# 卡片叠加效果

使用方式

```java
List<String> mData = new ArrayList<>();
mData.add("aaaaaaaaa");
mData.add("bbbbbbbbb");
mData.add("ccccccccc");
mData.add("ddddddddd");
mData.add("eeeeeddde");

recyclerView.setAdapter(adapter = new BaseRecyclerViewAdapter(this, mData, R.layout.item_recycler) {
    @Override
    public void convert(BaseRecyclerViewViewHolder viewHolder, Object o) {
        viewHolder.setText(R.id.tv_name, o.toString());
    }
});

ItemTouchHelper.Callback itemTouchHelperCallback = new RecyclerViewItemTouchHelperCallback<String>(recyclerView, adapter, mData);
ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
recyclerView.setLayoutManager(new RecyclerViewLayoutManager(recyclerView, itemTouchHelper));
itemTouchHelper.attachToRecyclerView(recyclerView);
```

**effect picture：**

![下载效果图](https://github.com/huangziye/base/blob/master/screenshot/stackview.gif)




# StepView

```xml
<com.hzy.stepview.widget.StepView
        android:id="@+id/step_view"
        android:layout_width="match_parent"
        android:layout_height="80dp"
        android:background="@color/white"
        android:padding="16dp"
        app:sv_animationDuration="200"
        app:sv_animationType="All"
        app:sv_defaultCircleColor="@color/hint_text_color"
        app:sv_doneCircleColor="@color/main_text_blue_color"
        app:sv_doneCircleRadius="14dp"
        app:sv_doneStepLineColor="@color/main_text_blue_color"
        app:sv_doneStepMarkColor="#f00"
        app:sv_doneTextColor="@color/main_text_blue_color"
        app:sv_nextStepLineColor="@color/hint_text_color"
        app:sv_nextTextColor="@color/hint_text_color"
        app:sv_selectedCircleColor="@color/main_text_blue_color"
        app:sv_selectedCircleRadius="14dp"
        app:sv_selectedStepNumberColor="@color/white"
        app:sv_selectedTextColor="@color/main_text_blue_color"
        app:sv_stepLineWidth="0.5dp"
        app:sv_stepNumberTextSize="12dp"
        app:sv_stepPadding="12dp"
        app:sv_steps="@array/steps"
        app:sv_stepsNumber="3"
        app:sv_textPadding="12dp"
        app:sv_textSize="14dp"/>
```


```java
stepView.go(step, true);
```


# RecyclerView实现Card Gallery效果，替代ViewPager方案。能够快速滑动并最终定位到居中位置。

效果图：

![效果图](https://github.com/huangziye/base/blob/master/screenshot/speedrecyclerview.gif)


使用方式详见 `RecyclerViewCardGalleryFragment`




# 增加了心形View（HeartView）










