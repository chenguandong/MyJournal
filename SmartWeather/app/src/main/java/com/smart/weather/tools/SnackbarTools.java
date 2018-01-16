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
