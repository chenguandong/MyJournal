package com.smart.journal.module.weather.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.smart.journal.bean.TodayWeatherBean
import com.smart.journal.module.weather.repository.WeatherRepository
import com.smart.journal.module.weather.repository.WeatherRepositoryImpl
import com.smart.journal.tools.http.CallBackBean
import java.util.*

/**
 *
 * @author guandongchen
 * @date 2018/3/30
 */
class WeatherViewModel: ViewModel(), WeatherRepository {



    var repository:WeatherRepository? = null

    init {
        if (repository==null){
            repository = WeatherRepositoryImpl()
        }
    }

    override fun getWeatherLiveData(): MutableLiveData<TodayWeatherBean> {

        return repository!!.getWeatherLiveData()
    }

    override fun getTodayWeatherData(): ArrayList<TodayWeatherBean.ForecastsBean.CastsBean> {

        return repository!!.getTodayWeatherData()
    }

    override fun onError(): MutableLiveData<CallBackBean<TodayWeatherBean>> {

        return repository!!.onError()
    }
}