package com.smart.weather.module.write.bean;

import com.smart.weather.module.journal.bean.JournalItemBean;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * @author guandongchen
 * @date 2018/1/22
 */

public class JournalBeanDBBean extends RealmObject{
    @PrimaryKey
    private String id;
    private String content;
    private String weather;
    private String tags;
    private JournalLocationDBBean location;
    private Date date;

    @Ignore
    private JournalItemBean journalItemBean;

    public JournalItemBean getJournalItemBean() {
        return journalItemBean;
    }

    public void setJournalItemBean(JournalItemBean journalItemBean) {
        this.journalItemBean = journalItemBean;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
