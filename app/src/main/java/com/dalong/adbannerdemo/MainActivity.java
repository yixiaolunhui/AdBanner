package com.dalong.adbannerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dalong.adbanner.AdBannerView;
import com.dalong.adbanner.AdOnItemClickListener;
import com.dalong.adbanner.AdViewHolderCreater;
import com.dalong.adbanner.transformer.AdAlphaPageTransformer;
import com.dalong.adbanner.transformer.AdNonPageTransformer;
import com.dalong.adbanner.transformer.AdRotateDownPageTransformer;
import com.dalong.adbanner.transformer.AdRotateUpPageTransformer;
import com.dalong.adbanner.transformer.AdRotateYTransformer;
import com.dalong.adbanner.transformer.AdScaleInTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        AdOnItemClickListener, AdapterView.OnItemClickListener{

    private AdBannerView mAdBannerView;

    private List<String> transformerList = new ArrayList<String>();
    private String[] images =
            {"http://img4.imgtn.bdimg.com/it/u=3990828539,4061622243&fm=21&gp=0.jpg",
            "http://www.6188.com/upload_6188s/flashAll/s800/20130716/1373936192eF9wjM.jpg",
            "http://d.3987.com/xingganmeinv_140814/007.jpg",
            "http://img1d.xgo-img.com.cn/pics/1717/1716154.jpg",
            "http://img1c.xgo-img.com.cn/pics/1545/a1544728.jpg",
            "http://k.zol-img.com.cn/ideapad/5039/a5038432_s.jpg"
    };
    private String[] transformers={
            "RotateDown",
            "RotateUp",
            "RotateY",
            "Standard",
            "Alpha",
            "ScaleIn",
            "RotateDown_Alpha",
            "RotateDown_Alpha_ScaleIn",
            "ScaleIn_Alpha"
    };
    private List<String> networkImages;
    private ListView listView;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setData();
    }


    private void initView() {
        networkImages= Arrays.asList(images);
        transformerList= Arrays.asList(transformers);
        mAdBannerView=(AdBannerView)findViewById(R.id.adbannerview);
        listView=(ListView)findViewById(R.id.listView);
        adapter= new ArrayAdapter(this,R.layout.adapter_transformer,transformerList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    public void setData(){
        mAdBannerView.setPageDatas(new AdViewHolderCreater<ImageHolderView>() {
            @Override
            public ImageHolderView createHolder() {
                return new ImageHolderView();
            }
        },networkImages).setPageMargin(0).setOffscreenPageLimit(networkImages.size())
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                .setOnItemClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        mAdBannerView.startTurning(3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        mAdBannerView.stopTurning();
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,"点击了第"+position+"个",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                mAdBannerView.setPageTransformer(new AdRotateDownPageTransformer());
                break;
            case 1:
                mAdBannerView.setPageTransformer(new AdRotateUpPageTransformer());
                break;
            case 2:
                mAdBannerView.setPageTransformer(new AdRotateYTransformer());
                break;
            case 3:
                mAdBannerView.setPageTransformer(AdNonPageTransformer.INSTANCE);
                break;
            case 4:
                mAdBannerView.setPageTransformer(new AdAlphaPageTransformer());
                break;
            case 5:
                mAdBannerView.setPageTransformer(new AdScaleInTransformer());
                break;
            case 6:
                mAdBannerView.setPageTransformer(new AdRotateDownPageTransformer(new AdAlphaPageTransformer()));
                break;
            case 7:
                mAdBannerView.setPageTransformer(new AdRotateDownPageTransformer(new AdAlphaPageTransformer(new AdScaleInTransformer())));
                break;
            case 8:
                mAdBannerView.setPageTransformer(new AdScaleInTransformer(new AdAlphaPageTransformer()));
                break;
        }

    }
}
