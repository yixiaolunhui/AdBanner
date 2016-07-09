package com.dalong.adbannerdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.dalong.adbanner.Holder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 *
 * Created by zhouweilong on 16/7/6.
 */

public class ImageHolderView  implements Holder<String> {

    private ImageView imageView;

    @Override
    public View createView(Context context) {
        View  view= LayoutInflater.from(context).inflate(R.layout.ad_item,null);
        imageView = (ImageView) view.findViewById(R.id.ad_img);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        ImageLoader.getInstance().displayImage(data,imageView,getOptions());
    }

    public  DisplayImageOptions getOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.ic_default_adimage)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
                .considerExifParams(true)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .displayer(new FadeInBitmapDisplayer(300))// 淡入
                .build();
        return options;
    }
}
