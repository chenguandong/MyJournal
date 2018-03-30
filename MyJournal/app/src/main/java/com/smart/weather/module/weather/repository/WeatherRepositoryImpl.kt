package com.smart.weather.module.weather.repository

import android.arch.lifecycle.MutableLiveData
import com.smart.weather.bean.TodayWeatherBean
import com.smart.weather.remote.WeatherApiManager
import com.smart.weather.tools.http.CallBackBean
import com.smart.weather.tools.http.MyCallBack
import java.util.*

/**
 *
 * @author guandongchen
 * @date 2018/3/30
 */
class WeatherRepositoryImpl:WeatherRepository{

    override fun onError(): MutableLiveData<CallBackBean<TodayWeatherBean>> {
        return errorData;
    }


    private var weatherLiveData = MutableLiveData<TodayWeatherBean>();
    private var errorData = MutableLiveData<CallBackBean<TodayWeatherBean>>();
    private var weatherData = ArrayList<TodayWeatherBean.ForecastsBean.CastsBean>();

    override fun getWeatherLiveData(): MutableLiveData<TodayWeatherBean> {

        WeatherApiManager.getWeatherData(object : MyCallBack<TodayWeatherBean>() {

            override fun onSuccess(callBackBean: CallBackBean<TodayWeatherBean>) {


                weatherData.clear()

                weatherData.addAll(callBackBean.responseBody.forecasts!![0].casts!!)

                weatherLiveData.postValue(callBackBean.responseBody)
            }

            override fun onFail(callBackBean: CallBackBean<TodayWeatherBean>) {

                errorData.postValue(callBackBean)
            }
        })


        return weatherLiveData;
    }

    override fun getTodayWeatherData(): ArrayList<TodayWeatherBean.ForecastsBean.CastsBean> {

        return weatherData;
    }
}