# AdBanner
 Can display the left and right part of the advertising bar controls, so that you can easily achieve the effect of advertising. Support an infinite loop, can set automatic page turning and time (and very intelligent, finger is suspended for turning the pages of a book, automatically leave began to turn the pages. You can also set in onpause interface does not automatically flip, onresume continue to automatic page), and offer a variety of flip effects. Can also set up your own custom flip effects. Compared to other advertising bar controls, most of the need to change the source code to load the network image, or to help you do not integrate the image you need to cache. And the library can make code cleanliness of your joy, do not need to modify the library source code in you can use any you like web photo gallery to cooperate. Supports custom view, custom cursor position. Convenient and easy to use.

##isplay
![image](https://github.com/dalong982242260/AdBanner/blob/master/gif/adbanner.gif?raw=true)

##How to use

xml:

         <com.dalong.adbanner.AdBannerView
                android:id="@+id/adbannerview"
                app:transformer="Alpha"
                app:canLoop="true"
                app:displayScale="0.7"
                android:layout_width="match_parent"
                android:layout_height="150dp"/>


java:
          mAdBannerView=(AdBannerView)findViewById(R.id.adbannerview);
          mAdBannerView.setPageDatas(new AdViewHolderCreater<ImageHolderView>() {
                     @Override
                     public ImageHolderView createHolder() {
                         return new ImageHolderView();
                     }
                 },networkImages)
                         .setOffscreenPageLimit(4)
                         .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                         .setOnItemClickListener(this);
##thanks
https://github.com/hongyangAndroid/MagicViewPager
https://github.com/saiwu-bigkoo/Android-ConvenientBanner
