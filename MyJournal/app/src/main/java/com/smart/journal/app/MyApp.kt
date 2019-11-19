package com.smart.journal.app

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.smart.journal.BuildConfig
import com.smart.journal.db.AppDatabase
import com.smart.journal.tools.location.LocationTools
import com.tencent.bugly.Bugly

/**
 * @author guandongchen
 * @date 2018/1/5
 */

class MyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        instance = this
        initLogger()
        LocationTools.getInstance()
        com.blankj.utilcode.util.Utils.init(this)
        Bugly.init(applicationContext, "c789e27850", false)
        initBaiDuMao()
        AppDatabase.instance
    }

    fun initBaiDuMao() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this)
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


    private fun initLogger() {

        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("com.smart.weather")
                .build()
        val androidLogAdapter = object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return true
            }
        }
        Logger.addLogAdapter(androidLogAdapter)

    }


    companion object staticParment{
        var instance: MyApp? = null
        const val UPDATE_APP_ID = BuildConfig.APPLICATION_ID + ".fileProvider"

    }
}
