package com.smart.weather.module.weather.repository

import android.arch.lifecycle.MutableLiveData
import com.smart.weather.bean.TodayWeatherBean
import com.smart.weather.tools.http.CallBackBean
import java.util.*

/**
 *
 * @author guandongchen
 * @date 2018/3/30
 */
interface WeatherRepository{
    fun  getWeatherLiveData(): MutableLiveData<TodayWeatherBean>
    fun  getTodayWeatherData(): ArrayList<TodayWeatherBean.ForecastsBean.CastsBean>
    fun  onError():MutableLiveData<CallBackBean<TodayWeatherBean>>
}