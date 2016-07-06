package com.dalong.adbanner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by zwl on 16/7/6.
 */

public class AdViewPagerScroller extends Scroller {

    // 滑动速度,值越大滑动越慢
    private int mScrollDuration = 800;

    private boolean zero;

    public AdViewPagerScroller(Context context) {
        super(context);
    }

    public AdViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public AdViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, zero ? 0 : mScrollDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, zero ? 0 : mScrollDuration);
    }

    /**
     * 获取滚动速度
     * @return
     */
    public int getScrollDuration() {
        return mScrollDuration;
    }

    /**
     *  设置滚动速度
     * @param scrollDuration
     */
    public void setScrollDuration(int scrollDuration) {
        this.mScrollDuration = scrollDuration;
    }

    /**
     * 获取是否是0  boolean ture 滚动设置0  false 滚动速度
     * @return
     */
    public boolean isZero() {
        return zero;
    }

    public void setZero(boolean zero) {
        this.zero = zero;
    }
}
