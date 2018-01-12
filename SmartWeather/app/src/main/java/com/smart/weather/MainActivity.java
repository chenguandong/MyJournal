package com.smart.weather;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.smart.weather.adapter.WeatherAdapter;
import com.smart.weather.bean.TodayWeatherBean;
import com.smart.weather.remote.AppClient;
import com.smart.weather.remote.WeatherService;
import com.smart.weather.tools.location.LocationTools;
import com.smart.weather.tools.logs.LogTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    private WeatherAdapter weatherAdapter;

    private List<TodayWeatherBean.ForecastsBean.CastsBean>forecastsBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        weatherAdapter = new WeatherAdapter(R.layout.item_weather,forecastsBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(weatherAdapter);
        weatherAdapter.openLoadAnimation();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();

                getWeatherData();

            }
        });
        getWeatherData();
    }

    private void getWeatherData(){

        Retrofit retrofit = AppClient.retrofit();

        WeatherService service = retrofit.create(WeatherService.class);

        Call<TodayWeatherBean> call = service.getWeatherData(LocationTools.getLocationBean().getAdCode());

        call.enqueue(new Callback<TodayWeatherBean>() {
            @Override
            public void onResponse(Call<TodayWeatherBean> call, Response<TodayWeatherBean> response) {
                LogTools.json(JSON.toJSONString(response.body()));
                //contentTextView.setText(JSON.toJSONString(response.body()));

                forecastsBeans.clear();

                forecastsBeans.addAll(response.body().getForecasts().get(0).getCasts());

                weatherAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TodayWeatherBean> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
