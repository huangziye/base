package com.hzy.layout.progress;

import android.view.View;

import java.util.List;

/**
 * Created by ziye_huang on 2018/8/16.
 */
public interface ProgressLayout {

    void showContent();

    /**
     * 显示内容
     *
     * @param idsOfViewsNotToShow 不需要显示的View
     */
    void showContent(List<Integer> idsOfViewsNotToShow);

    void showLoading();

    /**
     * 加载
     *
     * @param idsOfViewsNotToHide 不需要隐藏的View
     */
    void showLoading(List<Integer> idsOfViewsNotToHide);

    void showLoading(View loading);

    void showLoading(View loading, List<Integer> idsOfViewsNotToHide);

    /**
     * 显示Empty View
     *
     * @param empty
     */
    void showEmpty(View empty);

    /**
     * 显示Empty View
     *
     * @param empty
     * @param idsOfViewsNotToHide 不需要隐藏的View
     */
    void showEmpty(View empty, List<Integer> idsOfViewsNotToHide);

    /**
     * 显示错误Error View
     *
     * @param error
     */
    void showError(View error);

    /**
     * 显示错误Error View
     *
     * @param error
     * @param idsOfViewsNotToHide 不需要隐藏的View
     */
    void showError(View error, List<Integer> idsOfViewsNotToHide);

}
