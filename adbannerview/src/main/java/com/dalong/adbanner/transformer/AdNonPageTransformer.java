package com.dalong.adbanner.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 没有动画的
 * Created by zhy on 16/5/7.
 */
public class AdNonPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        page.setScaleX(0.999f);//hack
    }

    public static final ViewPager.PageTransformer INSTANCE = new AdNonPageTransformer();
}
