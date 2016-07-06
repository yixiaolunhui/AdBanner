package com.dalong.adbanner;

import android.content.Context;
import android.view.View;

/**
 * Created by zwl on 16/7/6.
 */

public interface Holder<T>{
     View createView(Context context);
     void UpdateUI(Context context,int position,T data);
}