package com.dalong.adbanner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Viewpager适配器
 * Created by zwl on 16/7/6.
 */

public class AdPageAdapter<T> extends PagerAdapter {

    private List<T> mDatas;

    private boolean canLoop = true;

    private AdViewHolderCreater holderCreator;

    private AdBannerViewPager viewPager;

    private final int MULTIPLE_COUNT = 300;

    public AdPageAdapter(AdViewHolderCreater holderCreator, List<T> datas) {
        this.holderCreator = holderCreator;
        this.mDatas = datas;
    }

    /**
     * 设置是否可以自动轮播
     * @param canLoop
     */
    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    /**
     * 设置viewpager
     * @param viewPager
     */
    public void setViewPager(AdBannerViewPager viewPager) {
        this.viewPager = viewPager;
    }


    @Override
    public int getCount() {
        return canLoop ? getRealCount()*MULTIPLE_COUNT : getRealCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     * 获取真实数据的数量
     * @return
     */
    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = toRealPosition(position);
        View view = getView(realPosition, null, container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        int position = viewPager.getCurrentItem();
        if (position == 0) {
            position = viewPager.getFristItem();
        } else if (position == getCount() - 1) {
            position = viewPager.getLastItem();
        }
        try {
            viewPager.setCurrentItem(position, false);
        }catch (IllegalStateException e){}
    }

    /**
     * 获取真实的位置
     * @param position
     * @return
     */
    public int toRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0)
            return 0;
        int realPosition = position % realCount;
        return realPosition;
    }


    public View getView(int position, View view, ViewGroup container) {
        Holder holder = null;
        if (view == null) {
            holder = (Holder) holderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(R.id.ad_item_tag, holder);
        } else {
            holder = (Holder<T>) view.getTag(R.id.ad_item_tag);
        }
        if (mDatas != null && !mDatas.isEmpty())
            holder.UpdateUI(container.getContext(), position, mDatas.get(position));
        return view;
    }
}
