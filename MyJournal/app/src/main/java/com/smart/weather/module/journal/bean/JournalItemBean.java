package com.smart.weather.module.journal.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * @author guandongchen
 * @date 2018/1/23
 */

public class JournalItemBean implements Serializable{
    private Bitmap bitmap;
    private String content;
    private String date;


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
