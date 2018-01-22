package com.smart.weather.module.write.bean;

import java.util.Date;

import io.realm.RealmObject;

/**
 * @author guandongchen
 * @date 2018/1/22
 */

public class JournalBeanDBBean extends RealmObject{
    private String content;
    private String weather;
    private String tags;
    private JournalLocationDBBean location;
    private Date date;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public JournalLocationDBBean getLocation() {
        return location;
    }

    public void setLocation(JournalLocationDBBean location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
