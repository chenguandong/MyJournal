package com.smart.journal.module.write.bean;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * @author guandongchen
 * @date 2018/1/18
 */

public class JournalBean  implements Serializable,MultiItemEntity {
    public static final int WRITE_TAG_TEXT = 0;
    public static final int WRITE_TAG_IMAGE =1;
    private int itemType;
    private String content;
    private String imageURL;

    public JournalBean(String content, String imageURL) {
        this.content = content;
        this.imageURL = imageURL;
        if (!TextUtils.isEmpty(content)){
            setItemType(WRITE_TAG_TEXT);
        }
        if (!TextUtils.isEmpty(imageURL)){
            setItemType(WRITE_TAG_IMAGE);
        }
    }

    public JournalBean(String content) {
        this.content = content;
        setItemType(WRITE_TAG_TEXT);
    }

    public JournalBean() {
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
