package com.smart.weather.app;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.smart.weather.tools.location.LocationTools;
import com.squareup.leakcanary.LeakCanary;

/**
 * @author guandongchen
 * @date 2018/1/5
 */

public class MyApp extends Application{

    private static  MyApp ourInstance ;

    public static MyApp getInstance() {
        return ourInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        initLogger();
        LocationTools.getInstance();
        initLeakCanary();
        com.blankj.utilcode.util.Utils.init(this);
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
