package com.smart.weather.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.weather.R;
import com.smart.weather.adapter.WeatherAdapter;
import com.smart.weather.base.BaseFragment;
import com.smart.weather.bean.TodayWeatherBean;
import com.smart.weather.remote.WeatherApiManager;
import com.smart.weather.tools.http.CallBackBean;
import com.smart.weather.tools.http.MyCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private WeatherAdapter weatherAdapter;
    private List<TodayWeatherBean.ForecastsBean.CastsBean> forecastsBeans = new ArrayList<>();

    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    protected void initView() {
        weatherAdapter = new WeatherAdapter(R.layout.item_weather, forecastsBeans);
        recycleView.setLayoutManager(new LinearLayoutManager(context));
        recycleView.setAdapter(weatherAdapter);
        weatherAdapter.openLoadAnimation();

    }

    @Override
    protected void initData() {
        getWeatherData();
    }

    private void getWeatherData() {


        WeatherApiManager.getWeatherData(new MyCallBack<TodayWeatherBean>(context) {

            @Override
            protected void onSuccess(CallBackBean<TodayWeatherBean> callBackBean) {
                forecastsBeans.clear();

                forecastsBeans.addAll(callBackBean.getResponseBody().getForecasts().get(0).getCasts());

                weatherAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onFail(CallBackBean<TodayWeatherBean> callBackBean) {

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
