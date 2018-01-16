package com.smart.weather.remote;

import com.smart.weather.bean.TodayWeatherBean;
import com.smart.weather.tools.http.AppClient;
import com.smart.weather.tools.http.MyCallBack;
import com.smart.weather.tools.location.LocationTools;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author guandongchen
 * @date 2018/1/16
 */

public class WeatherApiManager {

    public interface WeatherService {
        @GET("/v3/weather/weatherInfo?key=b1107cbba36699410a4d33f64f70e378&city=410102&extensions=all")
        Call<TodayWeatherBean>getWeatherData(@Query(("city")) String cityCode);
    }

    public static void getWeatherData(MyCallBack<? extends TodayWeatherBean> myCallBack){

        Call<TodayWeatherBean> call = AppClient.retrofit().create(WeatherService.class).getWeatherData(LocationTools.getLocationBean().getAdCode());

        call.enqueue((Callback<TodayWeatherBean>) myCallBack);

    }
}
