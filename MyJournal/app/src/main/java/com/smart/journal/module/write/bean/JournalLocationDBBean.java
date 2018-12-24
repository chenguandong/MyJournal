package com.smart.journal.module.write.bean;

import io.realm.RealmObject;

/**
 * @author guandongchen
 * @date 2018/1/22
 */

public class JournalLocationDBBean extends RealmObject{
    private double latitude;
    private double longitude;
    private String adress;


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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
