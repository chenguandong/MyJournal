package com.smart.journal.module.weather


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smart.journal.R
import com.smart.journal.adapter.WeatherAdapter
import com.smart.journal.base.BaseFragment
import com.smart.journal.module.weather.viewmodel.WeatherViewModel
import com.smart.journal.tools.SnackbarTools
import kotlinx.android.synthetic.main.fragment_weather.*

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : BaseFragment() {
    override fun getData() {
        init()
    }


    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var weatherViewModel:WeatherViewModel?=null

    private var weatherAdapter: WeatherAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        weatherViewModel!!.getWeatherLiveData().observe(this, androidx.lifecycle.Observer {

            todayWeatherBean->weatherAdapter!!.notifyDataSetChanged()

            swipeRefreshLayout.isRefreshing = false

        })
        weatherViewModel!!.onError().observe(this, Observer {
            callBackBean->SnackbarTools.showSimpleSnackbar(context,callBackBean!!.errorMeg)

            swipeRefreshLayout.isRefreshing = false
        })

    }

    override fun initView() {
        weatherAdapter = WeatherAdapter(R.layout.item_weather, weatherViewModel!!.getTodayWeatherData())
        recycleView!!.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context) as androidx.recyclerview.widget.RecyclerView.LayoutManager?
        recycleView!!.adapter = weatherAdapter
        weatherAdapter!!.openLoadAnimation()

        swipeRefreshLayout.setOnRefreshListener {
            initData()
        }

    }

    override fun initData() {
        swipeRefreshLayout.isRefreshing = true
        weatherViewModel!!.getWeatherLiveData()
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
