package com.smart.journal.tools.location.bean;

import java.io.Serializable;

/**
 * @author guandongchen
 * @date 2018/1/5
 */

public class LocationBean implements Serializable{
    /**
     *   /*amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
     amapLocation.getLatitude();//获取纬度
     amapLocation.getLongitude();//获取经度
     amapLocation.getAccuracy();//获取精度信息
     amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
     amapLocation.getCountry();//国家信息
     amapLocation.getProvince();//省信息
     amapLocation.getCity();//城市信息
     amapLocation.getDistrict();//城区信息
     amapLocation.getStreet();//街道信息
     amapLocation.getStreetNum();//街道门牌号信息
     amapLocation.getCityCode();//城市编码
     amapLocation.getAdCode();//地区编码
     amapLocation.getAoiName();//获取当前定位点的AOI信息
     amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
     amapLocation.getFloor();//获取当前室内定位的楼层
     amapLocation.getGpsStatus();//获取GPS的当前状态
     //获取定位时间
     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     Date date = new Date(amapLocation.getTime());
     df.format(date);*/

    private String adCode;
    private double latitude;
    private double longitude;
    private String adress;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
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
}
