package com.smart.journal.module.map.activity

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.LocationSource
import com.amap.api.maps2d.model.MyLocationStyle
import com.smart.journal.R
import kotlinx.android.synthetic.main.activity_amap_adress_search.*
import kotlinx.android.synthetic.main.activity_amap_adress_search.searchEditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener
import com.smart.journal.base.BaseActivity
import com.smart.journal.module.map.adapter.AMapSearchAdapter
import com.smart.journal.module.map.bean.MjPoiItem
import com.smart.journal.tools.logs.LogTools
import kotlin.jvm.internal.PropertyReference0


class AMapAdressSearchActivity : BaseActivity(), LocationSource,
        AMapLocationListener {
    companion object  {

        const val INTENT_LOCATION = "location"
        const val INTENT_TITLE = "title"
    }

    override fun initData() {

    }

    private var aMap: AMap? = null
    private var mListener: LocationSource.OnLocationChangedListener? = null
    private var mlocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null
    private var aMapLocation:AMapLocation? = null;
    private var aMapSearchAdapter:AMapSearchAdapter?=null;
    private var mMjPoiItemList:MutableList<MjPoiItem> = ArrayList<MjPoiItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.smart.journal.R.layout.activity_amap_adress_search)
        mapView.onCreate(savedInstanceState)// 此方法必须重写
        init()
    }

    /**
     * 初始化AMap对象
     */
    override fun init() {
        if (aMap == null) {
            aMap = mapView.map
            setUpMap()
        }
        initView()
    }
    override fun initView(){
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                searchPlace()
            }
        })
        aMapSearchAdapter = AMapSearchAdapter(R.layout.item_select_location_subtitle,mMjPoiItemList)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = aMapSearchAdapter;

    }

    private fun searchPlace() {
        var  query = PoiSearch.Query(searchEditText.text.toString(), "", aMapLocation!!.cityCode)
//keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10)// 设置每页最多返回多少条poiitem
        query.setPageNum(1)//设置查询页码
        progressBar.show()
        var poiSearch: PoiSearch = PoiSearch(this, query).also {
            it.run {
                setOnPoiSearchListener(object :OnPoiSearchListener{
                    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
                        LogTools.d("p1="+p1+","+p0)

                    }

                    override fun onPoiSearched(p0: PoiResult?, p1: Int) {
                        LogTools.d("p1="+p1+","+p0)
                        mMjPoiItemList.clear()
                        if (p0!!.pois.size!=0){
                            for (itemPoint in p0.pois){
                                var poiItem:MjPoiItem = MjPoiItem();
                                poiItem.snippet  =itemPoint.snippet
                                poiItem.title = itemPoint.title;
                                poiItem.latitude = itemPoint.latLonPoint.latitude
                                poiItem.longitude = itemPoint.latLonPoint.longitude
                                        mMjPoiItemList.add(poiItem)
                            }
                        }

                        aMapSearchAdapter!!.notifyDataSetChanged();
                        progressBar.hide()
                    }

                })
            }
        }
        poiSearch.searchPOIAsyn()

    }

    /**
     * 设置一些amap的属性
     */
    private fun setUpMap() {
        // 自定义系统定位小蓝点
        val myLocationStyle = MyLocationStyle()
        /*myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker))// 设置小蓝点的图标*/
        myLocationStyle.strokeColor(Color.BLACK)// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180))// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f)// 设置圆形的边框粗细
        aMap!!.setMyLocationStyle(myLocationStyle)
        aMap!!.setLocationSource(this)// 设置定位监听
        aMap!!.getUiSettings().isMyLocationButtonEnabled = true// 设置默认定位按钮是否显示
        aMap!!.setMyLocationEnabled(true)// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // aMap.setMyLocationType()
    }

    /**
     * 方法必须重写
     */
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    /**
     * 方法必须重写
     */
    override fun onPause() {
        super.onPause()
        mapView.onPause()
        deactivate()
    }

    /**
     * 方法必须重写
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    /**
     * 方法必须重写
     */
    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    /**
     * 定位成功后回调函数
     */
    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.errorCode == 0) {
                aMapLocation = amapLocation;
                mListener!!.onLocationChanged(amapLocation)// 显示系统小蓝点
                searchPlace();

            } else {
                val errText = "定位失败," + amapLocation.errorCode + ": " + amapLocation.errorInfo
                Log.e("AmapErr", errText)
            }
        }
    }

    /**
     * 激活定位
     */
    override fun activate(listener: LocationSource.OnLocationChangedListener) {
        mListener = listener
        if (mlocationClient == null) {
            mlocationClient = AMapLocationClient(this)
            mLocationOption = AMapLocationClientOption()
            //设置定位监听
            mlocationClient!!.setLocationListener(this)
            //设置为高精度定位模式
            mLocationOption!!.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy)
            //设置定位参数
            mlocationClient!!.setLocationOption(mLocationOption)
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient!!.startLocation()
        }
    }

    /**
     * 停止定位
     */
    override fun deactivate() {
        mListener = null
        if (mlocationClient != null) {
            mlocationClient!!.stopLocation()
            mlocationClient!!.onDestroy()
        }
        mlocationClient = null
    }
}
