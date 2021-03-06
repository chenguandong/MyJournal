package com.smart.journal.app

import android.content.Context
import android.os.Build
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import androidx.room.Room
import com.facebook.stetho.Stetho
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.smart.journal.BuildConfig
import com.smart.journal.db.AppDatabase
import com.smart.journal.tools.location.LocationTools
import com.tencent.bugly.Bugly
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method


/**
 * @author guandongchen
 * @date 2018/1/5
 */

class MyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initRoom()
        initLogger()
        LocationTools.instance
        com.blankj.utilcode.util.Utils.init(this)
        Bugly.init(applicationContext, "c789e27850", false)
        Stetho.initializeWithDefaults(this);
    }

    fun initRoom() {
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database-name")
                .allowMainThreadQueries()
                //.addMigrations(MIGRATION_2_3)
                .build()
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


    companion object staticParment {
        var instance: MyApp? = null
        var database: AppDatabase? = null
        const val UPDATE_APP_ID = BuildConfig.APPLICATION_ID + ".fileProvider"

    }

    private fun closeAndroidPDialog() {
        if (Build.VERSION.SDK_INT < 28) return
        try {
            val aClass = Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor = aClass.getDeclaredConstructor(String::class.java)
            declaredConstructor.setAccessible(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod: Method = cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.setAccessible(true)
            val activityThread: Any = declaredMethod.invoke(null)
            val mHiddenApiWarningShown: Field = cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.setAccessible(true)
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
