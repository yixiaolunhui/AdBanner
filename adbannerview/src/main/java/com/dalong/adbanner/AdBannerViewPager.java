package com.dalong.adbanner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zwl on 16/7/6.
 */

public class AdBannerViewPager extends ViewPager {

    private boolean isCanScroll = true;//是否滚动

    private boolean canLoop = true;//是否轮播

    private AdPageAdapter mAdapter;//viewpager适配器

    private OnPageChangeListener mOuterPageChangeListener;//页面切换监听

    private AdOnItemClickListener onItemClickListener;//点击回调

    private float oldX = 0;//按下的X坐标

    private float newX = 0;//手指抬起X坐标

    private static final float sens = 5;//当按下和抬起差距这么大时认为是点击事件
    /**
     * 构造方法
     * @param context
     */
    public AdBannerViewPager(Context context) {
        super(context);
        init();
    }

    /**
     * 构造方法
     * @param context
     * @param attrs
     */
    public AdBannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    /**
     * 初始化
     */
    private void init() {
        super.setOnPageChangeListener(onPageChangeListener);
    }
    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = mAdapter.toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                if (mOuterPageChangeListener != null) {
                    mOuterPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            int realPosition = position;

            if (mOuterPageChangeListener != null) {
                if (realPosition != mAdapter.getRealCount() - 1) {
                    mOuterPageChangeListener.onPageScrolled(realPosition,
                            positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > .5) {
                        mOuterPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mOuterPageChangeListener.onPageScrolled(realPosition,
                                0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mOuterPageChangeListener != null) {
                mOuterPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };

    /**
     * 设置适配器
     * @param adapter
     * @param canLoop
     */
    public void setAdapter(PagerAdapter adapter, boolean canLoop) {
        mAdapter = (AdPageAdapter) adapter;
        mAdapter.setCanLoop(canLoop);
        mAdapter.setViewPager(this);
        super.setAdapter(mAdapter);
        setCurrentItem(getFristItem(), false);
    }


    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mOuterPageChangeListener = listener;
    }

    /**
     * 获取第一个item
     * @return
     */
    public int getFristItem() {
        return canLoop ? mAdapter.getRealCount() : 0;
    }

    /**
     * 获取最后一个item
     * @return
     */
    public int getLastItem() {
        return mAdapter.getRealCount() - 1;
    }


    /**
     * 获取是否能滚动
     * @return
     */
    public boolean isCanScroll() {
        return isCanScroll;
    }


    /**
     * 设置是否能滚动
     * @param isCanScroll
     */
    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    /**
     * 获取真实的位置
     * @return
     */
    public int getRealItem() {
        return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
    }

    /**
     * 是否可以自动轮播
     * @return
     */
    public boolean isCanLoop() {
        return canLoop;
    }

    /**
     *  设置自动轮播
     * @param canLoop
     */
    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        if (canLoop == false) {
            setCurrentItem(getRealItem(), false);
        }
        if (mAdapter == null) return;
        mAdapter.setCanLoop(canLoop);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置监听
     * @param onItemClickListener
     */
    public void setOnItemClickListener(AdOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 获取适配器
     * @return
     */
    public AdPageAdapter getAdapter() {
        return mAdapter;
    }


    /**
     * touch 事件
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            if (onItemClickListener != null) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldX = ev.getX();
                        break;

                    case MotionEvent.ACTION_UP:
                        newX = ev.getX();
                        if (Math.abs(oldX - newX) < sens) {
                            onItemClickListener.onItemClick((getRealItem()));
                        }
                        oldX = 0;
                        newX = 0;
                        break;
                }
            }
            return super.onTouchEvent(ev);
        } else
            return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll)
            return super.onInterceptTouchEvent(ev);
        else
            return false;
    }

}
