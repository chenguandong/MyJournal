package com.smart.journal.module.weather.repository

import android.arch.lifecycle.MutableLiveData
import com.smart.journal.bean.TodayWeatherBean
import com.smart.journal.tools.http.CallBackBean
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