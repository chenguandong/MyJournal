package com.smart.weather.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smart.weather.R
import com.smart.weather.adapter.WeatherAdapter
import com.smart.weather.base.BaseFragment
import com.smart.weather.bean.TodayWeatherBean
import com.smart.weather.remote.WeatherApiManager
import com.smart.weather.tools.http.CallBackBean
import com.smart.weather.tools.http.MyCallBack
import kotlinx.android.synthetic.main.fragment_weather.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : BaseFragment() {


    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null


    private var weatherAdapter: WeatherAdapter? = null
    private val forecastsBeans = ArrayList<TodayWeatherBean.ForecastsBean.CastsBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun initView() {
        weatherAdapter = WeatherAdapter(R.layout.item_weather, forecastsBeans)
        recycleView!!.layoutManager = LinearLayoutManager(context)
        recycleView!!.adapter = weatherAdapter
        weatherAdapter!!.openLoadAnimation()

    }

    override fun initData() {
        getWeatherData()
    }

    private fun getWeatherData() {


        WeatherApiManager.getWeatherData(object : MyCallBack<TodayWeatherBean>(context) {

            override fun onSuccess(callBackBean: CallBackBean<TodayWeatherBean>) {
                forecastsBeans.clear()

                forecastsBeans.addAll(callBackBean.responseBody.forecasts[0].casts)

                weatherAdapter!!.notifyDataSetChanged()
            }

            override fun onFail(callBackBean: CallBackBean<TodayWeatherBean>) {

            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"


        fun newInstance(): WeatherFragment {
            val fragment = WeatherFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, "")
            args.putString(ARG_PARAM2, "")
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
