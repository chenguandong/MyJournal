package com.smart.journal.module.map


import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.provider.SyncStateContract
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdate
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.LocationSource
import com.amap.api.maps2d.model.*
import com.blankj.utilcode.util.LogUtils
import com.smart.journal.R
import com.smart.journal.base.BaseFragment
import com.smart.journal.customview.dialog.PreViewBottomSheetDialogFragment
import com.smart.journal.module.write.bean.JournalBeanDBBean
import com.smart.journal.module.write.db.JournalDBHelper
import com.smart.journal.tools.eventbus.MessageEvent
import com.smart.journal.tools.location.LocationTools
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_map.*
import org.greenrobot.eventbus.EventBus

/**
 * AMapV2地图中介绍自定义定位小蓝点
 */
class MapFragment : BaseFragment(), LocationSource, AMapLocationListener, AMap.OnMarkerClickListener {

    private var aMap: AMap? = null
    private var mListener: LocationSource.OnLocationChangedListener? = null
    private var mlocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null

    private var mLocMarker: Marker? = null
    private var mSensorHelper: SensorEventHelper? = null
    private var mCircle: Circle? = null
    //mark
    private var markerOption: MarkerOptions? = null

    private val realm = Realm.getDefaultInstance()

    private var journalBeanDBBeans: RealmResults<JournalBeanDBBean>? = null
    private var amapLocation:AMapLocation? = null

    internal var mFirstFix = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        EventBus.getDefault().register(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)// 此方法必须重写
    }

    override fun onStart() {
        super.onStart()
        if (aMap!=null&&amapLocation!=null) {
            aMap!!.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition(
                    LatLng(amapLocation!!.latitude, amapLocation!!.longitude), 18f, 30f, 30f)));
        }
    }

    override fun initView() {

    }

    override fun initData() {
        initMap()
    }

    /**
     * 初始化
     */
    fun initMap() {
        if (aMap == null) {
            aMap = mapView.getMap()
            setUpMap()
        }
        mSensorHelper = SensorEventHelper(context)
        if (mSensorHelper != null) {
            mSensorHelper!!.registerSensorListener()
        }
    }

    /**
     * 设置一些amap的属性
     */
    private fun setUpMap() {
        aMap!!.setLocationSource(this)// 设置定位监听
        aMap!!.uiSettings.isMyLocationButtonEnabled = true// 设置默认定位按钮是否显示
        aMap!!.isMyLocationEnabled = true// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap!!.setOnMarkerClickListener(this)
        aMap!!.setMyLocationEnabled(true)
        setupLocationStyle()
    }

    private fun setupLocationStyle() {
        // 自定义系统定位蓝点
        val myLocationStyle = MyLocationStyle()
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.gps_point))
        myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap!!.isMyLocationEnabled = true// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR)
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5f)
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR)
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap!!.setMyLocationStyle(myLocationStyle)
    }

    /**
     * 方法必须重写
     */
    override fun onResume() {
        super.onResume()
        mapView.onResume()
        if (mSensorHelper != null) {
            mSensorHelper!!.registerSensorListener()
        }
    }

    /**
     * 方法必须重写
     */
    override fun onPause() {
        super.onPause()
        if (mSensorHelper != null) {
            mSensorHelper!!.unRegisterSensorListener()
            mSensorHelper!!.setCurrentMarker(null)
            mSensorHelper = null
        }
        mapView.onPause()
        deactivate()
        mFirstFix = false
    }

    /**
     * 方法必须重写
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mapView != null) {
            mapView.onSaveInstanceState(outState)
        }
    }

    /**
     * 方法必须重写
     */
    override fun onDestroy() {
        super.onDestroy()
        if (mapView != null) {
            mapView.onDestroy()
        }
        if (null != mlocationClient) {
            mlocationClient!!.onDestroy()
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override override fun onLocationChanged(amapLocation: AMapLocation?) {

        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.errorCode == 0) {
                val location = LatLng(amapLocation.latitude, amapLocation.longitude)
                if (!mFirstFix) {
                    mFirstFix = true
                    addCircle(location, amapLocation.accuracy.toDouble())//添加定位精度圆

                    mSensorHelper!!.setCurrentMarker(mLocMarker)//定位图标旋转
                } else {
                    mCircle!!.center = location
                    mCircle!!.radius = amapLocation.accuracy.toDouble()
                    mLocMarker!!.position = location
                }


                addMarkersToMap()
                aMap!!.isMyLocationEnabled = true
                mListener!!.onLocationChanged(amapLocation)// 显示系统小蓝点
                //addMarker(location)//添加定位图标

                this@MapFragment.amapLocation = amapLocation
                //aMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 18f))
                LogUtils.d(amapLocation.toStr());

            } else {
                val errText = "定位失败," + amapLocation.errorCode + ": " + amapLocation.errorInfo
                LogUtils.d(errText);

            }
        }
    }


    private fun addMarker(latlng: LatLng) {
        if (mLocMarker != null) {
            return
        }
        val bMap = BitmapFactory.decodeResource(this.resources,
                R.mipmap.navi_map_gps_locked)
        val des = BitmapDescriptorFactory.fromBitmap(bMap)

        val options = MarkerOptions()
        options.icon(des)
        options.anchor(0.5f, 0.5f)
        options.position(latlng)
        mLocMarker = aMap!!.addMarker(options)
        mLocMarker!!.title = "当前位置"
        mSensorHelper!!.setCurrentMarker(mLocMarker)//定位图标旋转
    }

    private fun addCircle(latlng: LatLng, radius: Double) {
        val options = CircleOptions()
        options.strokeWidth(1f)
        options.fillColor(FILL_COLOR)
        options.strokeColor(STROKE_COLOR)
        options.center(latlng)
        options.radius(radius)
        mCircle = aMap!!.addCircle(options)
    }

    /**
     * 激活定位
     */
    @Override
    override fun activate(listener: LocationSource.OnLocationChangedListener) {
        mListener = listener
        if (mlocationClient == null) {
            mlocationClient = AMapLocationClient(context)
            mLocationOption = AMapLocationClientOption()
            //设置定位监听
            mlocationClient!!.setLocationListener(this)
            //设置为高精度定位模式
            mLocationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            mLocationOption!!.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
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
     * 在地图上添加marker
     */
    private fun addMarkersToMap() {

        if (aMap != null) {
            aMap!!.clear()
            if (LocationTools.getLocationBean() != null) {
                addMarker(LatLng(LocationTools.getLocationBean().latitude, LocationTools.getLocationBean().longitude))
            }
        }

        journalBeanDBBeans = JournalDBHelper.getAllJournals(realm)

        for (dataBean in journalBeanDBBeans!!) {

            if (!TextUtils.isEmpty(dataBean.location.latitude.toString() + "")) {
                markerOption = MarkerOptions().icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .title(dataBean.id + "")
                        .position(LatLng(dataBean.location.latitude, dataBean.location.longitude))
                        .draggable(false)
                aMap!!.addMarker(markerOption)
            }


        }

        mListener!!.onLocationChanged(amapLocation)// 显示系统小蓝点
        aMap!!.isMyLocationEnabled = true
    }

    /**
     * 对marker标注点点击响应事件
     */
    override fun onMarkerClick(marker: Marker): Boolean {
        if (aMap != null) {
        }
        PreViewBottomSheetDialogFragment(journalBeanDBBeans!!.where().beginsWith("id", marker.title).findAll().first()!!).show(fragmentManager!!, "")

        return true
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

    override fun onMessageEvent(event: MessageEvent) {
        super.onMessageEvent(event)
        if (event.tag == MessageEvent.NOTE_CHANGE) {
            addMarkersToMap()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
        realm.close()
    }

    override fun getData() {
        initData()
    }

    companion object {

        private val STROKE_COLOR = Color.argb(180, 3, 145, 255)
        private val FILL_COLOR = Color.argb(10, 0, 0, 180)

        fun newInstance(): MapFragment {
            val fragment = MapFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
