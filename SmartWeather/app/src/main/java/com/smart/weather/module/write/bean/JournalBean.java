package com.smart.weather.module.write.bean;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * @author guandongchen
 * @date 2018/1/18
 */

public class JournalBean extends RealmObject implements  MultiItemEntity {
    @Ignore
    public static final int WRITE_TAG_TEXT = 0;
    @Ignore
    public static final int WRITE_TAG_IMAGE =1;
    @Ignore
    private int itemType;
    @Ignore
    private Bitmap bitmap;

    private String content;
    private String imageBase64;

    public JournalBean(String content, String imageBase64) {
        this.content = content;
        this.imageBase64 = imageBase64;
        if (!TextUtils.isEmpty(content)){
            setItemType(WRITE_TAG_TEXT);
        }
        if (!TextUtils.isEmpty(imageBase64)){
            setItemType(WRITE_TAG_IMAGE);
        }
    }

    public JournalBean(String content) {
        this.content = content;
        setItemType(WRITE_TAG_TEXT);
    }

    public JournalBean() {
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
