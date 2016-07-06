package com.dalong.adbanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dalong.adbanner.transformer.AdAlphaPageTransformer;
import com.dalong.adbanner.transformer.AdNonPageTransformer;
import com.dalong.adbanner.transformer.AdRotateDownPageTransformer;
import com.dalong.adbanner.transformer.AdRotateUpPageTransformer;
import com.dalong.adbanner.transformer.AdRotateYTransformer;
import com.dalong.adbanner.transformer.AdScaleInTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by zwl on 16/7/6.
 */

public class AdBannerView<T> extends LinearLayout {

    public final  static  int DEFAULT_TIME=3000;

    private int transformer;

    private Context mContext;

    public AdBannerViewPager mViewPager;

    public boolean turning;//是否开启了翻页

    public AdSwitchTask adSwitchTask;

    public int autoTurningTime;//翻页的时间间隔

    private ViewGroup mIndicatorLayout;//指示器布局

    private AdViewPagerScroller mAdViewPagerScroller;

    private boolean canLoop = true;//是否自动滚动

    private List<T> mDatas;

    private AdPageAdapter mAdPageAdapter;

    private int[] mIndicators;//指示器数据

    private List<ImageView> mPointViews = new ArrayList<>();

    private AdIndicatorOnPageChangeListener mAdIndicatorOnPageChangeListener;

    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    private boolean canTurn;//是否可以翻页

    private RelativeLayout mBaseadbannerLayout;

    public AdBannerView(Context context) {
        this(context,null);
    }

    public AdBannerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AdBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.AdBannerView);
        canLoop = typedArray.getBoolean(R.styleable.AdBannerView_canLoop,true);
        autoTurningTime = typedArray.getInt(R.styleable.AdBannerView_autoTurningTime,DEFAULT_TIME);
        transformer=typedArray.getInt(R.styleable.AdBannerView_transformer,3);
        typedArray.recycle();
        initView();
    }

    private void initTransformer(int transformer) {
        switch (transformer){
            case 0:
                setPageTransformer(new AdRotateDownPageTransformer());
                break;
            case 1:
                setPageTransformer(new AdRotateUpPageTransformer());
                break;
            case 2:
                setPageTransformer(new AdRotateYTransformer());
                break;
            case 3:
                setPageTransformer(AdNonPageTransformer.INSTANCE);
                break;
            case 4:
                setPageTransformer(new AdAlphaPageTransformer());
                break;
            case 5:
                setPageTransformer(new AdScaleInTransformer());
                break;
            case 6:
                setPageTransformer(new AdRotateDownPageTransformer(new AdAlphaPageTransformer()));
                break;
            case 7:
                setPageTransformer(new AdRotateDownPageTransformer(new AdAlphaPageTransformer(new AdScaleInTransformer())));
                break;
        }
    }

    private void initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.ad_viewpager_layout,this,true);
        mViewPager = (AdBannerViewPager) view.findViewById(R.id.adbanner);
        mIndicatorLayout = (ViewGroup) view.findViewById(R.id.adindicator);
        mBaseadbannerLayout = (RelativeLayout) view.findViewById(R.id.adbanner_base);
        mBaseadbannerLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mViewPager.dispatchTouchEvent(event);
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mBaseadbannerLayout != null) {
                    mBaseadbannerLayout.invalidate();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initViewPagerScroll();
        adSwitchTask = new AdSwitchTask(this);
        initTransformer(transformer);
    }

    /**
     * 设置ViewPager的滑动速度
     * */
    private void initViewPagerScroll() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mAdViewPagerScroller = new AdViewPagerScroller(mViewPager.getContext());
            mScroller.set(mViewPager, mAdViewPagerScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置数据
     * @param holderCreator
     * @param datas
     * @return
     */
    public AdBannerView setPageDatas(AdViewHolderCreater holderCreator,List<T> datas){
        this.mDatas = datas;
        mAdPageAdapter = new AdPageAdapter(holderCreator,mDatas);
        mViewPager.setAdapter(mAdPageAdapter,canLoop);
        if (mIndicators != null)
            setPageIndicator(mIndicators);
        return this;
    }


    /**
     * 设置底部指示器资源图片数据
     * @param mIndicators
     */
    public AdBannerView setPageIndicator(int[] mIndicators) {
        mIndicatorLayout.removeAllViews();
        mPointViews.clear();
        this.mIndicators = mIndicators;
        if(mDatas==null)return this;
        for (int count = 0; count < mDatas.size(); count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(5, 0, 5, 0);
            if (mPointViews.isEmpty())
                pointView.setImageResource(mIndicators[1]);
            else
                pointView.setImageResource(mIndicators[0]);
            mPointViews.add(pointView);
            mIndicatorLayout.addView(pointView);
        }
        mAdIndicatorOnPageChangeListener = new AdIndicatorOnPageChangeListener(mPointViews,
                mIndicators);
        mViewPager.setOnPageChangeListener(mAdIndicatorOnPageChangeListener);
        mAdIndicatorOnPageChangeListener.onPageSelected(mViewPager.getRealItem());
        if(mOnPageChangeListener != null)mAdIndicatorOnPageChangeListener.setOnPageChangeListener(mOnPageChangeListener);
        return this;
    }

    /**
     * 设置底部指示器是否可见
     *
     * @param visible
     */
    public AdBannerView setPointViewVisible(boolean visible) {
        mIndicatorLayout.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }


    /**
     * 指示器的位置
     */
    public enum IndicatorAlign{
        left,right,center
    }

    /**
     * 指示器的方向
     * @param align  三个方向：居左 （RelativeLayout.ALIGN_PARENT_LEFT），居中 （RelativeLayout.CENTER_HORIZONTAL），居右 （RelativeLayout.ALIGN_PARENT_RIGHT）
     * @return
     */

    public AdBannerView setPageIndicatorAlign(IndicatorAlign align) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mIndicatorLayout.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, align == IndicatorAlign.left ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, align == IndicatorAlign.right ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, align == IndicatorAlign.center ? RelativeLayout.TRUE : 0);
        mIndicatorLayout.setLayoutParams(layoutParams);
        return this;
    }

    /***
     * 是否开启了翻页
     * @return
     */
    public boolean isTurning() {
        return turning;
    }

    /***
     * 开始翻页
     * @param autoTurningTime 自动翻页时间
     * @return
     */
    public AdBannerView startTurning(int autoTurningTime) {
        //如果是正在翻页的话先停掉
        if(turning){
            stopTurning();
        }
        //设置可以翻页并开启翻页
        canTurn = true;
        this.autoTurningTime = autoTurningTime;
        turning = true;
        postDelayed(adSwitchTask, autoTurningTime);
        return this;
    }

    /**
     * 停止翻页
     */
    public void stopTurning() {
        turning = false;
        removeCallbacks(adSwitchTask);
    }

    /**
     * 是否能滚动
     * @return
     */
    public boolean isCanScroll() {
        return mViewPager.isCanScroll();
    }

    /**
     * 设置是否能滚动
     * @param isCanScroll
     */
    public void setCanScroll(boolean isCanScroll) {
        mViewPager.setCanScroll(isCanScroll);
    }

    /**
     * 获取当前的页面index
     * @return
     */
    public int getCurrentItem(){
        if (mViewPager!=null) {
            return mViewPager.getRealItem();
        }
        return -1;
    }

    /**
     * 设置当前的页面index
     * @param index
     */
    public void setCurrentItem(int index){
        if (mViewPager!=null) {
            mViewPager.setCurrentItem(index);
        }
    }

    /**
     * 设置翻页监听器
     * @param mOnPageChangeListener
     * @return
     */
    public AdBannerView setOnPageChangeListener(ViewPager.OnPageChangeListener mOnPageChangeListener) {
        this.mOnPageChangeListener = mOnPageChangeListener;
        //如果有默认的监听器（即是使用了默认的翻页指示器）则把用户设置的依附到默认的上面，否则就直接设置
        if(mAdIndicatorOnPageChangeListener != null)mAdIndicatorOnPageChangeListener.setOnPageChangeListener(mOnPageChangeListener);
        else mViewPager.setOnPageChangeListener(mOnPageChangeListener);
        return this;
    }

    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return mOnPageChangeListener;
    }

    /**
     * 设置是否可以自动滚动
     * @param canLoop
     */
    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        mViewPager.setCanLoop(canLoop);
    }

    /**
     * 获取是否可以滚动
     * @return
     */
    public boolean isCanLoop() {
        return mViewPager.isCanLoop();
    }

    /**
     * 监听item点击
     * @param onItemClickListener
     */
    public AdBannerView setOnItemClickListener(AdOnItemClickListener onItemClickListener) {
        if (onItemClickListener == null) {
            mViewPager.setOnItemClickListener(null);
            return this;
        }
        mViewPager.setOnItemClickListener(onItemClickListener);
        return this;
    }

    /**
     * 设置ViewPager的滚动速度
     * @param scrollDuration
     */
    public void setScrollDuration(int scrollDuration){
        mAdViewPagerScroller.setScrollDuration(scrollDuration);
    }

    /**
     * 获取滚动速度
     * @return
     */
    public int getScrollDuration() {
        return mAdViewPagerScroller.getScrollDuration();
    }

    /**
     * 获取viewpager对象
     * @return
     */
    public AdBannerViewPager getViewPager() {
        return mViewPager;
    }


    /**
     * 自定义翻页动画效果
     *
     * @param transformer
     * @return
     */
    public AdBannerView setPageTransformer(ViewPager.PageTransformer transformer) {
        mViewPager.setPageTransformer(true, transformer);
        return this;
    }

    /**
     * 触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP||action == MotionEvent.ACTION_CANCEL||action == MotionEvent.ACTION_OUTSIDE) {
            // 开始翻页
            if (canTurn)startTurning(autoTurningTime);
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (canTurn)stopTurning();
        }
        return super.dispatchTouchEvent(ev);
    }

    public AdBannerView  setPageMargin(int margin){
        mViewPager.setPageMargin(margin);
        return this;
    }
    public AdBannerView  setOffscreenPageLimit(int mOffscreenPageLimit){
        mViewPager.setOffscreenPageLimit(mOffscreenPageLimit);
        return this;
    }
}
