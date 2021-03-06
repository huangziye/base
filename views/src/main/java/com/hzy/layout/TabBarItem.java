package com.hzy.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzy.views.R;

import java.util.Locale;

/**
 * 底部tab条目
 * Created by ziye_huang on 2018/7/24.
 */
public class TabBarItem extends LinearLayout {

    private Context mContext;
    private int mIconNormalResId;//普通状态图标的资源id
    private int mIconSelectedResId;//选中状态图标的资源id
    private String mText;//文本
    private int mTextSize = 12;//文字大小 默认为12sp
    private int mTextColorNormal = 0xFF999999;    //描述文本的默认显示颜色
    private int mTextColorSelected = 0xFF46C01B;  //述文本的默认选中显示颜色
    private int mWhiteColor = 0xFFFFFFFF;  //白色
    private int mMarginTop = 0;//文字和图标的距离,默认0dp
    private boolean mOpenTouchBg = false;// 是否开启触摸背景，默认关闭
    private Drawable mTouchDrawable;//触摸时的背景
    private int mIconWidth;//图标的宽度
    private int mIconHeight;//图标的高度
    private int mItemPadding;//TabbarItemLayout的padding


    private ImageView mImageView;
    private TextView mTvUnread;
    private TextView mTvNotify;
    private TextView mTvMsg;
    private TextView mTextView;

    private int mUnreadTextSize = 10; //未读数默认字体大小10sp
    private int mMsgTextSize = 6; //消息默认字体大小6sp
    private int unreadNumThreshold = 99;//未读数阈值
    private int mUnreadTextColor;//未读数字体颜色
    private Drawable mUnreadTextBg;
    private int mMsgTextColor;
    private Drawable mMsgTextBg;
    private Drawable mNotifyPointBg;

    public TabBarItem(Context context) {
        this(context, null);
    }

    public TabBarItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabBarItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabBarItem);
        initAttrs(ta); //初始化属性
        ta.recycle();
        checkValues();//检查值是否合法
        init();//初始化相关操作
    }

    private void initAttrs(TypedArray ta) {
        mIconNormalResId = ta.getResourceId(R.styleable.TabBarItem_iconNormal, -1);
        mIconSelectedResId = ta.getResourceId(R.styleable.TabBarItem_iconSelected, -1);

        mText = ta.getString(R.styleable.TabBarItem_itemText);
        mTextSize = ta.getDimensionPixelSize(R.styleable.TabBarItem_itemTextSize, sp2px(mContext, mTextSize));

        mTextColorNormal = ta.getColor(R.styleable.TabBarItem_textColorNormal, mTextColorNormal);
        mTextColorSelected = ta.getColor(R.styleable.TabBarItem_textColorSelected, mTextColorSelected);

        mMarginTop = ta.getDimensionPixelSize(R.styleable.TabBarItem_itemMarginTop, dp2px(mContext, mMarginTop));

        mOpenTouchBg = ta.getBoolean(R.styleable.TabBarItem_openTouchBg, mOpenTouchBg);
        mTouchDrawable = ta.getDrawable(R.styleable.TabBarItem_touchDrawable);

        mIconWidth = ta.getDimensionPixelSize(R.styleable.TabBarItem_iconWidth, 0);
        mIconHeight = ta.getDimensionPixelSize(R.styleable.TabBarItem_iconHeight, 0);
        mItemPadding = ta.getDimensionPixelSize(R.styleable.TabBarItem_itemPadding, 0);

        mUnreadTextSize = ta.getDimensionPixelSize(R.styleable.TabBarItem_unreadTextSize, sp2px(mContext, mUnreadTextSize));
        mUnreadTextColor = ta.getColor(R.styleable.TabBarItem_unreadTextColor, 0xFFFFFFFF);
        mUnreadTextBg = ta.getDrawable(R.styleable.TabBarItem_unreadTextBg);

        mMsgTextSize = ta.getDimensionPixelSize(R.styleable.TabBarItem_msgTextSize, sp2px(mContext, mMsgTextSize));
        mMsgTextColor = ta.getColor(R.styleable.TabBarItem_msgTextColor, 0xFFFFFFFF);
        mMsgTextBg = ta.getDrawable(R.styleable.TabBarItem_msgTextBg);

        mNotifyPointBg = ta.getDrawable(R.styleable.TabBarItem_notifyPointBg);

        unreadNumThreshold = ta.getInteger(R.styleable.TabBarItem_unreadThreshold, 99);
    }

    /**
     * 检查传入的值是否完善
     */
    private void checkValues() {
        if (mIconNormalResId == -1) {
            throw new IllegalStateException("您还没有设置默认状态下的图标，请指定iconNormal的图标");
        }

        if (mIconSelectedResId == -1) {
            throw new IllegalStateException("您还没有设置选中状态下的图标，请指定iconSelected的图标");
        }

        if (mOpenTouchBg && mTouchDrawable == null) {
            //如果有开启触摸背景效果但是没有传对应的drawable
            throw new IllegalStateException("开启了触摸效果，但是没有指定touchDrawable");
        }

        if (mUnreadTextBg == null) {
            mUnreadTextBg = getResources().getDrawable(R.drawable.shape_unread);
        }

        if (mMsgTextBg == null) {
            mMsgTextBg = getResources().getDrawable(R.drawable.shape_msg);
        }

        if (mNotifyPointBg == null) {
            mNotifyPointBg = getResources().getDrawable(R.drawable.shape_notify_point);
        }
    }

    @TargetApi(16)
    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        View view = initView();

        mImageView.setImageResource(mIconNormalResId);

        if (mIconWidth != 0 && mIconHeight != 0) {
            //如果有设置图标的宽度和高度，则设置ImageView的宽高
            FrameLayout.LayoutParams imageLayoutParams = (FrameLayout.LayoutParams) mImageView.getLayoutParams();
            imageLayoutParams.width = mIconWidth;
            imageLayoutParams.height = mIconHeight;
            mImageView.setLayoutParams(imageLayoutParams);
        }

        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);//设置底部文字字体大小

        mTvUnread.setTextSize(TypedValue.COMPLEX_UNIT_PX, mUnreadTextSize);//设置未读数的字体大小
        mTvUnread.setTextColor(mUnreadTextColor);//设置未读数字体颜色
        mTvUnread.setBackground(mUnreadTextBg);//设置未读数背景

        mTvMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, mMsgTextSize);//设置提示文字的字体大小
        mTvMsg.setTextColor(mMsgTextColor);//设置提示文字的字体颜色
        mTvMsg.setBackground(mMsgTextBg);//设置提示文字的背景颜色

        mTvNotify.setBackground(mNotifyPointBg);//设置提示点的背景颜色

        mTextView.setTextColor(mTextColorNormal);//设置底部文字字体颜色
        mTextView.setText(mText);//设置标签文字

        LayoutParams textLayoutParams = (LayoutParams) mTextView.getLayoutParams();
        textLayoutParams.topMargin = mMarginTop;
        mTextView.setLayoutParams(textLayoutParams);

        if (mOpenTouchBg) {
            //如果有开启触摸背景
            setBackground(mTouchDrawable);
        }

        addView(view);
    }

    @NonNull
    private View initView() {
        View view = View.inflate(mContext, R.layout.item_tab_bar, null);
        if (mItemPadding != 0) {
            //如果有设置item的padding
            view.setPadding(mItemPadding, mItemPadding, mItemPadding, mItemPadding);
        }
        mImageView = (ImageView) view.findViewById(R.id.iv_icon);
        mTvUnread = (TextView) view.findViewById(R.id.tv_unred_num);
        mTvMsg = (TextView) view.findViewById(R.id.tv_msg);
        mTvNotify = (TextView) view.findViewById(R.id.tv_point);
        mTextView = (TextView) view.findViewById(R.id.tv_text);
        return view;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setIconNormalResourceId(int mIconNormalResourceId) {
        this.mIconNormalResId = mIconNormalResourceId;
    }

    public void setIconSelectedResourceId(int mIconSelectedResourceId) {
        this.mIconSelectedResId = mIconSelectedResourceId;
    }

    public void setStatus(boolean isSelected) {
        mImageView.setImageDrawable(getResources().getDrawable(isSelected ? mIconSelectedResId : mIconNormalResId));
        mTextView.setTextColor(isSelected ? mTextColorSelected : mTextColorNormal);
    }

    private void setTvVisiable(TextView tv) {
        //都设置为不可见
        mTvUnread.setVisibility(GONE);
        mTvMsg.setVisibility(GONE);
        mTvNotify.setVisibility(GONE);

        tv.setVisibility(VISIBLE);//设置为可见
    }

    public int getUnreadNumThreshold() {
        return unreadNumThreshold;
    }

    public void setUnreadNumThreshold(int unreadNumThreshold) {
        this.unreadNumThreshold = unreadNumThreshold;
    }

    /**
     * 设置未读数
     *
     * @param unreadNum 小于等于{@link TabBarItem#unreadNumThreshold}则隐藏，
     *                  大于0小于{@link TabBarItem#unreadNumThreshold}则显示对应数字，
     *                  超过{@link TabBarItem#unreadNumThreshold}
     *                  显示{@link TabBarItem#unreadNumThreshold}+
     */
    public void setUnreadNum(int unreadNum) {
        setTvVisiable(mTvUnread);
        if (unreadNum <= 0) {
            mTvUnread.setVisibility(GONE);
        } else if (unreadNum <= unreadNumThreshold) {
            mTvUnread.setText(String.valueOf(unreadNum));
        } else {
            mTvUnread.setText(String.format(Locale.CHINA, "%d+", unreadNumThreshold));
        }
    }

    public void setMsg(String msg) {
        setTvVisiable(mTvMsg);
        mTvMsg.setText(msg);
    }

    public void hideMsg() {
        mTvMsg.setVisibility(GONE);
    }

    public void showNotify() {
        setTvVisiable(mTvNotify);
    }

    public void hideNotify() {
        mTvNotify.setVisibility(GONE);
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        //这个得到的不应该叫做密度，应该是密度的一个比例。不是真实的屏幕密度，而是相对于某个值的屏幕密度。
        //也可以说是相对密度
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
