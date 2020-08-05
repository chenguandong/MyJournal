package com.smart.journal.tools.location


import android.util.Log
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.smart.journal.app.MyApp
import com.smart.journal.tools.location.bean.LocationBean
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author guandongchen
 * @date 2018/1/5
 */

object LocationTools {
    //声明AMapLocationClient类对象
    private var mLocationClient: AMapLocationClient? = null

    //声明AMapLocationClientOption对象
    private var mLocationOption: AMapLocationClientOption? = null

    var locationBean: LocationBean? = null
        private set

    //初始化定位
    //设置定位回调监听
    //定位成功回调信息，设置相关消息
    //获取当前定位结果来源，如网络定位结果，详见定位类型表
    //获取纬度
    //获取经度
    //获取精度信息
    //定位时间
    //地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
    //国家信息
    //省信息
    //城市信息
    //城区信息
    //街道信息
    //街道门牌号信息
    //城市编码
    //地区编码
    //获取当前定位点的AOI信息
    //LogTools.d(amapLocation.toStr());
    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
    /**
     * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
     *///设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
    //获取最近3s内精度最高的一次定位结果：
    //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)
    // 接口也会被设置为true，反之不会，默认为false。
    //设置是否返回地址信息（默认返回地址信息）
    //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
    val instance: LocationTools
        get() {
            if (mLocationClient == null) {
                mLocationClient = AMapLocationClient(MyApp.instance)
            }
            if (locationBean == null) {
                locationBean = LocationBean()
            }
            mLocationClient!!.setLocationListener { amapLocation ->
                if (amapLocation != null) {
                    if (amapLocation.errorCode == 0) {
                        amapLocation.locationType
                        amapLocation.latitude
                        amapLocation.longitude
                        amapLocation.accuracy
                        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        val date = Date(amapLocation.time)
                        df.format(date)
                        amapLocation.address
                        amapLocation.country
                        amapLocation.province
                        amapLocation.city
                        amapLocation.district
                        amapLocation.street
                        amapLocation.streetNum
                        amapLocation.cityCode
                        amapLocation.adCode
                        amapLocation.poiName

                        locationBean!!.adCode = amapLocation.adCode
                        locationBean!!.latitude = amapLocation.latitude
                        locationBean!!.longitude = amapLocation.longitude
                        locationBean!!.adress = amapLocation.address
                    } else {
                        Log.e("AmapError",
                                "location Error, ErrCode:" + amapLocation.errorCode + ", errInfo:" + amapLocation.errorInfo)
                    }
                }
            }

            if (mLocationOption == null) {
                mLocationOption = AMapLocationClientOption()
            }
            mLocationOption!!.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
            mLocationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            mLocationOption!!.isOnceLocationLatest = true
            mLocationOption!!.isNeedAddress = true
            if (null != mLocationClient) {
                mLocationClient!!.setLocationOption(mLocationOption)
                mLocationClient!!.stopLocation()
                mLocationClient!!.startLocation()
            }
            return this
        }

    /*  companion object{
          val ourInstance = LocationTools();
      }*/
}
