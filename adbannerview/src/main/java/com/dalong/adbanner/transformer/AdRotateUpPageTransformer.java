package com.dalong.adbanner.transformer;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
/**
 * RotateUp
 * Created by zwl on 16/7/6.
 */
public class AdRotateUpPageTransformer extends AdBasePageTransformer {

    private static final float DEFAULT_MAX_ROTATE = 15.0f;
    private float mMaxRotate = DEFAULT_MAX_ROTATE;

    public AdRotateUpPageTransformer() {
    }

    public AdRotateUpPageTransformer(float maxRotate)
    {
        this(maxRotate, AdNonPageTransformer.INSTANCE);
    }

    public AdRotateUpPageTransformer(ViewPager.PageTransformer pageTransformer)
    {
        this(DEFAULT_MAX_ROTATE, pageTransformer);
    }

    public AdRotateUpPageTransformer(float maxRotate, ViewPager.PageTransformer pageTransformer)
    {
        mMaxRotate = maxRotate;
        mPageTransformer = pageTransformer;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void pageTransform(View view, float position)
    {
        if (position < -1)
        { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setRotation(mMaxRotate);
            view.setPivotX(view.getWidth());
            view.setPivotY(0);

        } else if (position <= 1) // a页滑动至b页 ； a页从 0.0 ~ -1 ；b页从1 ~ 0.0
        { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            if (position < 0)//[0，-1]
            {
                view.setPivotX(view.getWidth() * (0.5f + 0.5f * (-position)));
                view.setPivotY(0);
                view.setRotation(-mMaxRotate * position);
            } else//[1,0]
            {
                view.setPivotX(view.getWidth() * 0.5f * (1 - position));
                view.setPivotY(0);
                view.setRotation(-mMaxRotate * position);
            }
        } else
        { // (1,+Infinity]
            // This page is way off-screen to the right.
//            ViewHelper.setRotation(view, ROT_MAX);
            view.setRotation(-mMaxRotate);
            view.setPivotX(0);
            view.setPivotY(0);
        }
    }
}  