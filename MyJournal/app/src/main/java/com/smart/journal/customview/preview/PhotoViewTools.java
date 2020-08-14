package com.smart.journal.customview.preview;

import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.vansz.glideimageloader.GlideImageLoader;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by guandongchen on 02/08/2017.
 */
public class PhotoViewTools {
    public static void showPhotos(ArrayList<String> photosUrl, int currentIndex, AppCompatActivity context) {
       // new PhotoViewPagerActivity(currentIndex,photosUrl).show(context.getSupportFragmentManager(),"");

        Transferee transfer = Transferee.getDefault(context);
        transfer.apply(TransferConfig.build()
            .setImageLoader(GlideImageLoader.with(context))
            .setSourceUrlList(photosUrl)
            .create()
        ).show();
    }


}
