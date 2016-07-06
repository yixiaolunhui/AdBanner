package com.dalong.adbannerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dalong.adbanner.AdBannerView;
import com.dalong.adbanner.AdOnItemClickListener;
import com.dalong.adbanner.AdViewHolderCreater;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity implements AdOnItemClickListener {

    private AdBannerView mAdBannerView,mAdBannerView2,mAdBannerView3,mAdBannerView4,mAdBannerView5,mAdBannerView6;

    private String[] images =
            {"http://www.pp3.cn/uploads/201602/20160219003.jpg",
            "http://img05.tooopen.com/images/20140524/sy_61761371996.jpg",
            "http://cdn.duitang.com/uploads/item/201206/11/20120611174127_hzFtA.jpeg",
            "http://e.hiphotos.baidu.com/zhidao/pic/item/cefc1e178a82b9014e150b23718da9773912ef62.jpg",
            "http://img.61gequ.com/allimg/160513/122016-160513155617.jpg",
            "http://dl.bizhi.sogou.com/images/2012/03/26/65118.jpg"
    };
    private List<String> networkImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }

    private void initView() {
        mAdBannerView=(AdBannerView)findViewById(R.id.adbannerview);
        mAdBannerView2=(AdBannerView)findViewById(R.id.adbannerview2);
        mAdBannerView3=(AdBannerView)findViewById(R.id.adbannerview3);
        mAdBannerView4=(AdBannerView)findViewById(R.id.adbannerview4);
        mAdBannerView5=(AdBannerView)findViewById(R.id.adbannerview5);
        mAdBannerView6=(AdBannerView)findViewById(R.id.adbannerview6);
        networkImages= Arrays.asList(images);
        setData(mAdBannerView);
        setData(mAdBannerView2);
        setData(mAdBannerView3);
        setData(mAdBannerView4);
        setData(mAdBannerView5);
        setData(mAdBannerView6);
    }

    public void setData(AdBannerView view ){
        view.setPageDatas(new AdViewHolderCreater<ImageHolderView>() {
            @Override
            public ImageHolderView createHolder() {
                return new ImageHolderView();
            }
        },networkImages).setPageMargin(40).setOffscreenPageLimit(networkImages.size())
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                .setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        mAdBannerView.startTurning(3000);
        mAdBannerView2.startTurning(3000);
        mAdBannerView3.startTurning(3000);
        mAdBannerView4.startTurning(3000);
        mAdBannerView5.startTurning(3000);
        mAdBannerView6.startTurning(3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        mAdBannerView.stopTurning();
        mAdBannerView2.stopTurning();
        mAdBannerView3.stopTurning();
        mAdBannerView4.stopTurning();
        mAdBannerView5.stopTurning();
        mAdBannerView6.stopTurning();
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,"点击了第"+position+"个",Toast.LENGTH_SHORT).show();
    }
}
