package com.smart.weather.remote;

import com.smart.weather.bean.TodayWeatherBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author guandongchen
 * @date 2017/11/28
 */

public interface WeatherService {
    @GET("/v3/weather/weatherInfo?key=b1107cbba36699410a4d33f64f70e378&city=410102&extensions=all")
    Call<TodayWeatherBean>getWeatherData(@Query(("city")) String cityCode);
}
