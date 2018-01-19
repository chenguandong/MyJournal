package com.smart.weather.module.write.bean;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * @author guandongchen
 * @date 2018/1/18
 */

public class JournalBean implements Serializable, MultiItemEntity {

    public static final int WRITE_TAG_TEXT = 0;
    public static final int WRITE_TAG_IMAGE =1;

    private int itemType;

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
