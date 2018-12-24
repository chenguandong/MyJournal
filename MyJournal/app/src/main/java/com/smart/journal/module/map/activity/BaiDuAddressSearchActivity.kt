package com.smart.journal.module.map.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.*
import com.baidu.mapapi.search.poi.*
import com.baidu.mapapi.utils.DistanceUtil
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.smart.journal.R
import com.smart.journal.base.BaseActivity
import kotlinx.android.synthetic.main.act_search_adress.*
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.collections4.Predicate
import java.util.*

class BaiDuAddressSearchActivity : BaseActivity() {


    private var adressSearchCityAdapter: AdressSearchCityAdapter? = null
    //PoiItem
    private val cityList = ArrayList<PoiItem>()

    //当前选中城市
    private var poiItem: PoiItem? = null

    //当前选中
    private var selPostion = -1

    private var locationPoiItem: PoiItem? = null //当前定位城市


    ////////////////////////////baidu map

    var mLocationClient: LocationClient? = null
    var myListener: BDLocationListener = MyLocationListener()
    private var mPoiSearch: PoiSearch? = null
    private var geoCoder: GeoCoder? = null
    private var latLng: LatLng? = null
    private var MAX_DISTANCE = 1500//签到范围限制

    private var mBaiduMap: BaiduMap? = null

    private var bitmap: BitmapDescriptor? = null

    private var isChickItem: Boolean = false

    private var rightMenu: MenuItem? = null

    /**
     * 反地理位置编码
     */
    internal var listener: OnGetGeoCoderResultListener = object : OnGetGeoCoderResultListener {
        override fun onGetGeoCodeResult(result: GeoCodeResult?) {

            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有检索到结果

            }
            //获取地理编码结果
        }

        override fun onGetReverseGeoCodeResult(result: ReverseGeoCodeResult?) {

            progressBar.hide()

            if (isChickItem) {
                isChickItem = false

                return
            }

            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                rightMenu!!.setEnabled(false)
                //没有找到检索结果
            } else {


                if (locationPoiItem != null) {
                    if (!TextUtils.isEmpty(result.address)) {
                        val adress = (result.address + result.sematicDescription).trim { it <= ' ' }.replace("null", "")
                        locationPoiItem!!.snippet = adress
                        locationPoiItem!!.title = adress
                        locationPoiItem!!.latitude = result.location.latitude
                        locationPoiItem!!.longitude = result.location.longitude
                    }
                }

                val poiList = result.poiList

                if (poiList != null) {
                    refreshData(poiList)
                }
            }
            //获取反向地理编码结果
        }
    }

    internal var poiListener: OnGetPoiSearchResultListener = object : OnGetPoiSearchResultListener {
        override fun onGetPoiIndoorResult(p0: PoiIndoorResult?) {
        }

        override fun onGetPoiDetailResult(p0: PoiDetailResult?) {
        }

        override fun onGetPoiDetailResult(p0: PoiDetailSearchResult?) {
        }


        override fun onGetPoiResult(poiResult: PoiResult?) {

            if (poiResult == null) {
                return
            }
            if (poiResult.allPoi != null) {

                val predicate = Predicate<PoiInfo> { poiInfo ->
                    // TODO Auto-generated method stub
                    DistanceUtil.getDistance(latLng, poiInfo.location) <= MAX_DISTANCE
                }

                val result = CollectionUtils.select(poiResult.allPoi, predicate) as List<PoiInfo>
                if (result.size != 0) {
                    refreshData(result)
                } else {
                    cityList.clear()

                    emptyView.setVisibility(View.VISIBLE)

                    emptyView.setTitle("没有找到相关数据")

                    adressSearchCityAdapter!!.notifyDataSetChanged()
                }

            } else {

                cityList.clear()

                emptyView.setVisibility(View.VISIBLE)

                emptyView.setTitle("没有找到相关数据")

                adressSearchCityAdapter!!.notifyDataSetChanged()
            }

        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_search_adress)
        initData()
        initView()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        rightMenu = menu!!.findItem(R.id.toolbar_right_action)
        rightMenu!!.setTitle("完成").isEnabled = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.toolbar_right_action->goSingn()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initMapView() {

        bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.location)

        mBaiduMap = bmapView.getMap()

        mBaiduMap!!.isMyLocationEnabled = true

        mBaiduMap!!.isBuildingsEnabled = false


        mBaiduMap!!.setOnMapStatusChangeListener(object : BaiduMap.OnMapStatusChangeListener {
            override fun onMapStatusChangeStart(mapStatus: MapStatus) {

                progressBar.show()

                if (!TextUtils.isEmpty(searchEditText.getText().toString())) {
                    searchEditText.setText("")
                }
            }

            override fun onMapStatusChangeStart(mapStatus: MapStatus, i: Int) {

            }

            override fun onMapStatusChange(mapStatus: MapStatus) {
                setScreenCenterView(mapStatus.target)
            }

            override fun onMapStatusChangeFinish(mapStatus: MapStatus) {


                if (DistanceUtil.getDistance(latLng, mapStatus.target) >= MAX_DISTANCE) {

                    cityList.clear()

                    adressSearchCityAdapter!!.notifyDataSetChanged()

                    rightMenu!!.setEnabled(false)

                    emptyView.setVisibility(View.VISIBLE)
                    emptyView.setTitle("超出签到最大范围")
                    return
                }
                emptyView.setVisibility(View.GONE)
                emptyView.setTitle("没有找到相关数据")
                setScreenCenterView(mapStatus.target)
                backSearch(mapStatus.target)
                rightMenu!!.setEnabled(true)
            }
        })
    }

    private fun initLocation() {
        val option = LocationClientOption()
        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll")
        //可选，默认gcj02，设置返回的定位结果坐标系

        val span = 0//1000*60*60;
        option.setScanSpan(span)
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true)
        //可选，设置是否需要地址信息，默认不需要

        option.isOpenGps = true
        //可选，默认false,设置是否使用gps

        option.isLocationNotify = true
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true)
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true)
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false)
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false)
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false)
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient!!.locOption = option
    }

    private fun setScreenCenterView(centLatLng: LatLng?) {
        mBaiduMap!!.clear()
        if (centLatLng == null) {

            return
        }
        //定义Maker坐标点
        //构建Marker图标

        //构建MarkerOption，用于在地图上添加Marker
        val option = MarkerOptions()
                .position(centLatLng)
                .icon(bitmap!!)
                .zIndex(9)  //设置marker所在层级
                .draggable(false)  //设置手势拖拽;
        //在地图上添加Marker，并显示
        //在地图上添加Marker，并显示
        mBaiduMap!!.addOverlay(option)


    }

    private fun setMapLocation(latLng: LatLng) {

        val locData: MyLocationData

        locData = MyLocationData.Builder()
                .accuracy(10f)//location.getRadius()
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(0f).latitude(latLng.latitude)
                .longitude(latLng.longitude).build()
        mBaiduMap!!.setMyLocationData(locData)
        setFourceLook(latLng)


    }

    //设置焦点
    private fun setFourceLook(latLngf: LatLng) {
        val ll = LatLng(latLngf.latitude,
                latLngf.longitude)
        val builder = MapStatus.Builder()
        builder.target(ll).zoom(18.0f)
        mBaiduMap!!.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
    }

    //定位成功
    internal inner class MyLocationListener : BDLocationListener {

        override fun onReceiveLocation(location: BDLocation) {
            latLng = LatLng(location.latitude, location.longitude)
            if (latLng != null) {

                //获取定位结果

                val list = location.poiList    // POI数据

                locationPoiItem = PoiItem()
                locationPoiItem!!.latitude = latLng!!.latitude
                locationPoiItem!!.longitude = latLng!!.longitude
                locationPoiItem!!.cityName = location.city
                var locationAdress = ""
                if (!TextUtils.isEmpty(location.addrStr)) {
                    locationAdress = (location.addrStr + location.locationDescribe).trim { it <= ' ' }.replace("null", "")

                } else {
                    if (list != null && list.size != 0) {
                        locationAdress = list[0].name.trim { it <= ' ' }.replace("null", "")
                        locationPoiItem!!.snippet = locationAdress
                        locationPoiItem!!.title = locationAdress
                    }
                }
                locationPoiItem!!.snippet = if (TextUtils.isEmpty(locationAdress)) "无法获取到位置信息" else locationAdress
                locationPoiItem!!.title = if ("无法获取到位置信息".startsWith(locationPoiItem!!.snippet)) "请前往设置查看定位权限是否打开" else locationAdress

                backSearch(latLng)

                setScreenCenterView(latLng)
                setMapLocation(latLng!!)


            }

        }
    }


    //关键字检索
    fun searchPlace() {

        if (latLng == null) {
            return
        }

        //  第四步，发起检索请求；
        val place = PoiNearbySearchOption()
        val s = searchEditText.getText().toString()
        if (!TextUtils.isEmpty(s)) {
            place.radius(MAX_DISTANCE).keyword(s).location(latLng).pageNum(0).sortType(PoiSortType.distance_from_near_to_far)
            mPoiSearch!!.searchNearby(place)
        } else {
            backSearch(latLng)
        }

    }

    //反编码检索
    fun backSearch(latLng: LatLng?) {
        if (latLng == null) {
            return
        }
        val revers = ReverseGeoCodeOption()
        revers.location(latLng)
        geoCoder!!.reverseGeoCode(revers)
    }


    private fun refreshData(poiList: List<PoiInfo>?) {
        runOnUiThread(Runnable {
            cityList.clear()
            progressBar.hide()

            if (locationPoiItem != null) {
                cityList.add(locationPoiItem!!)
                emptyView.setVisibility(View.GONE)
            } else {

                emptyView.setVisibility(View.VISIBLE)

                emptyView.setTitle("暂无数据")

                adressSearchCityAdapter!!.notifyDataSetChanged()

                return@Runnable
            }

            if (cityList.size != 0) {

                poiItem = cityList[0]
                selPostion = 0
                if (poiItem!!.snippet.startsWith("无法获取到位置信息")) {
                    rightMenu!!.setEnabled(false)
                } else {
                    rightMenu!!.setEnabled(true)
                }


            } else {
                poiItem = null
                selPostion = -1
                rightMenu!!.setEnabled(false)
            }
            adressSearchCityAdapter!!.selPostion = selPostion

            adressSearchCityAdapter!!.notifyDataSetChanged()

            if (poiList!!.size != 0) {
                //搜索结果

                for (poiInfo in poiList) {
                    val poiItem = PoiItem(poiInfo)
                    poiItem.cityName = poiInfo.city
                    poiItem.snippet = poiInfo.name
                    poiItem.title = poiInfo.address
                    if (poiInfo.location != null) {
                        poiItem.longitude = poiInfo.location.longitude
                        poiItem.latitude = poiInfo.location.latitude
                    }

                    cityList.add(poiItem)
                }

                //listLinearLayout.setVisibility(View.VISIBLE)

                emptyView.setVisibility(View.GONE)

            } else {
                if (cityList.size == 0) {
                    //listLinearLayout.setVisibility(View.GONE)

                    emptyView.setVisibility(View.VISIBLE)

                    emptyView.setTitle("定位失败")

                    ToastUtils.showShort("定位失败")
                }
            }

            adressSearchCityAdapter!!.notifyDataSetChanged()
        })
    }

     override fun initView() {

         mLocationClient = LocationClient(applicationContext)
         //声明LocationClient类
         mLocationClient!!.registerLocationListener(myListener)

         //注册监听函数
         mPoiSearch = PoiSearch.newInstance()

         mPoiSearch!!.setOnGetPoiSearchResultListener(poiListener)

         //创建反编码实例
         geoCoder = GeoCoder.newInstance()

         geoCoder!!.setOnGetGeoCodeResultListener(listener)

         initLocation()


         initSimpleToolbar(intent.getStringExtra(INTENT_TITLE))

        listView.adapter = adressSearchCityAdapter

         KeyboardUtils.hideSoftInput(this)

        searchEditText.setOnEditorActionListener({ v, actionId, event ->
            searchEditText.clearFocus()

            KeyboardUtils.hideSoftInput(this)

            searchPlace()


            false
        })

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                searchPlace()
            }
        })

         initMapView()
    }


   @Override public override fun initData() {
        adressSearchCityAdapter = AdressSearchCityAdapter(this, cityList)

        listView.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            if (selPostion == position && poiItem != null) {

                adressSearchCityAdapter!!.selPostion = -1

                poiItem = null
                rightMenu!!.setEnabled(false)

            } else {

                poiItem = cityList[position]

                if ("无法获取到位置信息" == poiItem!!.snippet) {
                    rightMenu!!.setEnabled(false)
                    return@OnItemClickListener
                }

                selPostion = position

                adressSearchCityAdapter!!.selPostion = selPostion

                rightMenu!!.setEnabled(true)

                adressSearchCityAdapter!!.notifyDataSetChanged()

                isChickItem = true

                val latLngL = LatLng(poiItem!!.latitude, poiItem!!.longitude)
                setFourceLook(latLngL)
                rightMenu!!.setEnabled(true)
            }
        })

    }

    override fun onStart() {
        super.onStart()

        if (!NetworkUtils.isConnected()) {

            ToastUtils.showShort("请检查是否开启网络连接!")

            //listLinearLayout.setVisibility(View.GONE)

            emptyView.setVisibility(View.VISIBLE)

        }

        mLocationClient!!.start()
    }

    override fun onResume() {
        super.onResume()
        bmapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        bmapView.onPause()
    }

    override fun onStop() {
        super.onStop()

        mLocationClient!!.stop()
    }

    public override fun onDestroy() {
        bmapView.onDestroy()
        super.onDestroy()
    }


    private fun goSingn() {

        if (poiItem != null) {

            val intent = Intent()

            intent.putExtra(INTENT_LOCATION, Gson().toJson(poiItem))

            setResult(Activity.RESULT_OK, intent)

        }


        finish()
    }

    fun onClick(view: View) {
        when (view.id) {


            R.id.locationImageView    //点击回到当前位置
            ->

                if (latLng != null) {
                    setMapLocation(latLng!!)
                }
        }
    }

    companion object {

        val INTENT_LOCATION = "INTENT_LOCATION"

        val INTENT_TITLE = "INTENT_TITLE"
        val INTENT_RIGHT_TEXT = "INTENT_RIGHT_TEXT"
        const val REQUEST_LOCATION_CODE = 999;
    }
}
