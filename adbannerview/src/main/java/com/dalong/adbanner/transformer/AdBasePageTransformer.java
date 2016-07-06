package com.dalong.adbanner.transformer;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
/**
 *
 * Created by zwl on 16/7/6.
 */
public abstract class AdBasePageTransformer implements ViewPager.PageTransformer{

    protected ViewPager.PageTransformer mPageTransformer = AdNonPageTransformer.INSTANCE;
    public static final float DEFAULT_CENTER = 0.5f;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void transformPage(View view, float position) {
        if (mPageTransformer != null) {
            mPageTransformer.transformPage(view, position);
        }

        pageTransform(view, position);
    }
    protected abstract void pageTransform(View view, float position);

}
