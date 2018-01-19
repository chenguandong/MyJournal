package com.smart.weather.tools;

import android.widget.ImageView;

import com.smart.weather.app.GlideApp;

/**
 * @author guandongchen
 * @date 2018/1/18
 */

public class ImageViewTools {
    public static void showImageViewGlide(String URL, ImageView imageView){
        GlideApp.with(imageView.getContext()).load(URL)
                /*.placeholder(R.mipmap.image_loading)
                .error(R.mipmap.image_error)*/
                .fitCenter()
                .into(imageView);
    }

    public static void showImageViewGlide(String URL, ImageView imageView,int placeholder){
        GlideApp.with(imageView.getContext()).load(URL)
               /* .placeholder(R.mipmap.image_loading)
                .error(placeholder)*/
                .fitCenter()
                .into(imageView);
    }
}
