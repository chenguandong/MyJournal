package com.smart.weather.tools;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.smart.weather.R;

/**
 * @author guandongchen
 * @date 2018/1/16
 */

public class SnackbarTools {

    public static final int SNACKBAR_DURATION = 5000;

    public static void showSettingSnackBar(final Context context,String titleInfo){

        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);

        Snackbar snackBar  = Snackbar.make(rootView,titleInfo,Snackbar.LENGTH_LONG)
                .setAction("去设置", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActionTools.openSetting(context);

                    }
                })
                .setActionTextColor(ContextCompat.getColor(context, R.color.black))
                .setDuration(SNACKBAR_DURATION);

        snackBar.getView().setBackgroundColor(ContextCompat.getColor(context,R.color.black));

        snackBar.show();
    }

    public static void showGPSSettingSnackBar(final Context context,String titleInfo){

        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);

        Snackbar snackBar  = Snackbar.make(rootView,titleInfo,Snackbar.LENGTH_LONG)
                .setAction("去设置", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActionTools.settingLocation(context);

                    }
                })
                .setActionTextColor(ContextCompat.getColor(context, R.color.black))
                .setDuration(SNACKBAR_DURATION);

        snackBar.getView().setBackgroundColor(ContextCompat.getColor(context,R.color.black));

        snackBar.show();
    }

    public static void showSimpleSnackbar(Context context,String message){

        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);

        Snackbar snackBar  = Snackbar.make(rootView,message,Snackbar.LENGTH_LONG)
                .setAction("", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(ContextCompat.getColor(context, R.color.black))
                .setDuration(2000);

        snackBar.getView().setBackgroundColor(ContextCompat.getColor(context,R.color.black));

        snackBar.show();
    }
}
