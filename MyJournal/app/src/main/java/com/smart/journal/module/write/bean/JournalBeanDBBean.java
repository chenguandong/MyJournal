package com.smart.journal.module.write.bean;

import com.smart.journal.module.journal.bean.JournalItemBean;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


/**
 * @author guandongchen
 * @date 2018/1/22
 */

@Entity(tableName = "journal")
public class JournalBeanDBBean {
    @PrimaryKey(autoGenerate = true)
    private String id;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "weather")
    private String weather;
    @ColumnInfo(name = "tags")
    private String tags;
    @ColumnInfo(name = "date")
    private long date;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "address")
    private String address;

    @Ignore
    private JournalItemBean journalItemBean;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public JournalItemBean getJournalItemBean() {
        return journalItemBean;
    }

    public void setJournalItemBean(JournalItemBean journalItemBean) {
        this.journalItemBean = journalItemBean;
    }
}
