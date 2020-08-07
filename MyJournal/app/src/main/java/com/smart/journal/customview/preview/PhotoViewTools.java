package com.smart.journal.customview.preview;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by guandongchen on 02/08/2017.
 */
public class PhotoViewTools {
    public static void showPhotos(ArrayList<String> photosUrl, int currentIndex, Context context) {
        Intent intent = new Intent(context, PhotoViewPagerActivity.class);
        intent.putExtra(PhotoViewPagerActivity.Companion.getURLS(), photosUrl); //图片数组
        intent.putExtra(PhotoViewPagerActivity.Companion.getURLS_CHOOSE_INDEX(), currentIndex); //当前选中第几个图片
        context.startActivity(intent);

    }
}
