package com.smart.weather.tools;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;

import com.smart.weather.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * @author guandongchen
 * @date 2017/12/15
 */

public class DividerItemDecorationTools {

    public static RecyclerView.ItemDecoration getItemDecoration(Context context, int size , @ColorRes int colorId){
       return new HorizontalDividerItemDecoration.Builder(context).size(SizeTools.dp2px(size)).colorResId(colorId).build();
    }
    public static RecyclerView.ItemDecoration getItemDecoration(Context context, int size){
        return new HorizontalDividerItemDecoration.Builder(context).size(SizeTools.dp2px(size)).colorResId(R.color.divider).build();
    }

    public static RecyclerView.ItemDecoration getItemDecoration(Context context){
        return new HorizontalDividerItemDecoration.Builder(context).colorResId(R.color.divider).build();
    }
}
