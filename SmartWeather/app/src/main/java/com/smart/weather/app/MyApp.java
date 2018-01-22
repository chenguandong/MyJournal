package com.smart.weather.app;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.smart.weather.BuildConfig;
import com.smart.weather.tools.location.LocationTools;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author guandongchen
 * @date 2018/1/5
 */

public class MyApp extends MultiDexApplication {

    private static  MyApp ourInstance ;

    public static MyApp getInstance() {
        return ourInstance;
    }

    public static final String UPDATE_APP_ID = BuildConfig.APPLICATION_ID+".fileProvider";
    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        initLogger();
        LocationTools.getInstance();
        initLeakCanary();
        com.blankj.utilcode.util.Utils.init(this);
        Fresco.initialize(this);
        Bugly.init(getApplicationContext(), "c789e27850", false);
        initRealm();
    }

    private void initRealm(){

        Realm.init(this);
        byte[] key = new byte[64];
        new SecureRandom().nextBytes(key);

        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(2)
                //.encryptionKey(key)//数据库加密
                .build();
// Use the config
        Realm.getInstance(config);


    }

    private void initLogger(){

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("com.smart.weather")
                .build();
        AndroidLogAdapter androidLogAdapter=  new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
            }
        };
        Logger.addLogAdapter(androidLogAdapter);

    }

    private void initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
