package com.dalong.adbanner;

import java.lang.ref.WeakReference;

/**
 * Created by zwl on 16/7/6.
 */

public  class AdSwitchTask implements Runnable{

    private final WeakReference<AdBannerView> reference;

    public AdSwitchTask(AdBannerView convenientBanner) {
        this.reference = new WeakReference<AdBannerView>(convenientBanner);
    }

    @Override
    public void run() {
        AdBannerView convenientBanner = reference.get();

        if(convenientBanner != null){
            if (convenientBanner.mViewPager != null && convenientBanner.turning) {
                int page = convenientBanner.mViewPager.getCurrentItem() + 1;
                convenientBanner.mViewPager.setCurrentItem(page);
                convenientBanner.postDelayed(convenientBanner.adSwitchTask, convenientBanner.autoTurningTime);
            }
        }
    }
}
