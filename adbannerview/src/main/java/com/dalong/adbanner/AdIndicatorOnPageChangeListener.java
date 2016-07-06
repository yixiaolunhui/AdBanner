package com.dalong.adbanner;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 翻页指示器适配器
 * Created by zwl on 16/7/6.
 */

public class AdIndicatorOnPageChangeListener implements ViewPager.OnPageChangeListener {

    private List<ImageView> pointViews;
    private int[] mIndicators;
    private ViewPager.OnPageChangeListener onPageChangeListener;

    public AdIndicatorOnPageChangeListener(List<ImageView> pointViews,int mIndicators[]){
        this.pointViews=pointViews;
        this.mIndicators = mIndicators;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(onPageChangeListener != null)onPageChangeListener.onPageScrollStateChanged(state);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(onPageChangeListener != null)onPageChangeListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int index) {
        Log.v("888888","onPageSelected:"+index);
        for (int i = 0; i < pointViews.size(); i++) {
            pointViews.get(index).setImageResource(mIndicators[1]);
            if (index != i) {
                pointViews.get(i).setImageResource(mIndicators[0]);
            }
        }
        if(onPageChangeListener != null)onPageChangeListener.onPageSelected(index);

    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }
}
