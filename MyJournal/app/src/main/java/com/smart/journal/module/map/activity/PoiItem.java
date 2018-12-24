package com.smart.journal.module.map.activity;

/**
 * Created by guandongchen on 05/06/2017.
 */

public class PoiItem {

    private String snippet;

    private com.baidu.mapapi.search.core.PoiInfo PoiInfo;

    private  double latitude;
    private double longitude;

    private String cityName;
    private String adName;
    private String title;

    private String IMEI;



    //高度
    private String altitude;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PoiItem(com.baidu.mapapi.search.core.PoiInfo poiInfo) {
        PoiInfo = poiInfo;
    }

    public PoiItem() {
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public com.baidu.mapapi.search.core.PoiInfo getPoiInfo() {
        return PoiInfo;
    }

    public void setPoiInfo(com.baidu.mapapi.search.core.PoiInfo poiInfo) {
        PoiInfo = poiInfo;
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



    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }
}
