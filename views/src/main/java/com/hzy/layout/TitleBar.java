package com.hzy.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzy.R;

/**
 * 自定义标题栏
 * Created by ziye_huang on 2018/7/25.
 */
public class TitleBar extends RelativeLayout {
    //左侧文字
    private TextView mLeftText;
    //默认字体大小
    private int mDefaultTextSize = 48;
    //默认文字字体颜色
    private int mDefaultTextColor = Color.parseColor("#FFFFFF");
    //左侧图片
    private ImageView mLeftImage;
    private TextView mTitleText;
    private TextView mRightText;
    private ImageView mRightImage;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        initLeftTextView(context, typedArray);
        initLeftImageView(context, typedArray);
        initTitleView(context, typedArray);
        initRightTextView(context, typedArray);
        initRightImage(context, typedArray);
        typedArray.recycle();
    }

    /**
     * 初始化左侧文字
     *
     * @param context
     * @param typedArray
     */
    private void initLeftTextView(Context context, TypedArray typedArray) {
        int leftTextResId = typedArray.getResourceId(R.styleable.TitleBar_left_text, 0);
        CharSequence charSequence = leftTextResId > 0 ? typedArray.getResources().getText(leftTextResId) : typedArray.getString(R.styleable.TitleBar_left_text);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mLeftText = createTextView(context, R.id.tv_left_text, charSequence, params);

        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        int leftImageResId = typedArray.getResourceId(R.styleable.TitleBar_left_image, 0);
        if (leftImageResId > 0) {
            mLeftText.setVisibility(View.GONE);
        }

        float leftTextSize = typedArray.getDimension(R.styleable.TitleBar_left_text_size, mDefaultTextSize);
        int leftTextColor = typedArray.getColor(R.styleable.TitleBar_left_text_color, mDefaultTextColor);
        mLeftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
        mLeftText.setTextColor(leftTextColor);
        addView(mLeftText);
    }


    @NonNull
    private TextView createTextView(Context context, int id, CharSequence charSequence, LayoutParams params) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setId(id);
        textView.setText(charSequence);
        return textView;
    }

    private void initLeftImageView(Context context, TypedArray typedArray) {
        int leftImageResId = typedArray.getResourceId(R.styleable.TitleBar_left_image, 0);
        if (0 == leftImageResId) {
            return;
        }
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mLeftImage = createImageView(context, R.id.tv_left_image, leftImageResId, params);

        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        addView(mLeftImage);
    }

    @NonNull
    private ImageView createImageView(Context context, int id, int drawable, LayoutParams params) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setId(id);
        imageView.setImageResource(drawable);
        return imageView;
    }


    private void initTitleView(Context context, TypedArray typedArray) {
        int titleTextResId = typedArray.getResourceId(R.styleable.TitleBar_title_text, 0);
        CharSequence titleText = titleTextResId > 0 ? typedArray.getResources().getText(titleTextResId) : typedArray.getString(R.styleable.TitleBar_title_text);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mTitleText = createTextView(context, R.id.tv_title_text, titleText, params);

        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        float titleTextSize = typedArray.getDimension(R.styleable.TitleBar_title_text_size, mDefaultTextSize);
        int titleTextColor = typedArray.getColor(R.styleable.TitleBar_title_text_color, mDefaultTextColor);

        mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        mTitleText.setTextColor(titleTextColor);
        addView(mTitleText);
    }

    private void initRightTextView(Context context, TypedArray typedArray) {
        int rightTextResId = typedArray.getResourceId(R.styleable.TitleBar_right_text, 0);
        CharSequence charSequence = rightTextResId > 0 ? typedArray.getResources().getText(rightTextResId) : typedArray.getString(R.styleable.TitleBar_right_text);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mRightText = createTextView(context, R.id.tv_right_text, charSequence, params);

        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        int leftImageResId = typedArray.getResourceId(R.styleable.TitleBar_right_image, 0);
        if (leftImageResId > 0) {
            mRightText.setVisibility(View.GONE);
        }

        float leftTextSize = typedArray.getDimension(R.styleable.TitleBar_right_text_size, mDefaultTextSize);
        int leftTextColor = typedArray.getColor(R.styleable.TitleBar_right_text_color, mDefaultTextColor);
        mRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
        mRightText.setTextColor(leftTextColor);
        addView(mRightText);
    }

    private void initRightImage(Context context, TypedArray typedArray) {
        int rightImageResId = typedArray.getResourceId(R.styleable.TitleBar_right_image, 0);
        if (0 == rightImageResId) {
            return;
        }
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mRightImage = createImageView(context, R.id.tv_right_image, rightImageResId, params);

        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addView(mRightImage);
    }

    @NonNull
    public TextView getLeftText() {
        return mLeftText;
    }

    public void setLeftText(TextView mLeftText) {
        this.mLeftText = mLeftText;
    }

    @NonNull
    public ImageView getLeftImage() {
        return mLeftImage;
    }

    public void setLeftImage(ImageView mLeftImage) {
        this.mLeftImage = mLeftImage;
    }

    @NonNull
    public TextView getTitleText() {
        return mTitleText;
    }

    public void setTitleText(TextView mTitleText) {
        this.mTitleText = mTitleText;
    }

    @NonNull
    public TextView getRightText() {
        return mRightText;
    }

    public void setRightText(TextView mRightText) {
        this.mRightText = mRightText;
    }

    @NonNull
    public ImageView getRightImage() {
        return mRightImage;
    }

    public void setRightImage(ImageView mRightImage) {
        this.mRightImage = mRightImage;
    }
}
