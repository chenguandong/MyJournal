package com.smart.weather.tools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by guandongchen on 2017/9/12.
 */

public class PermissionTools {
    /**
     *
     *      CALENDAR = new String[]{"android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR"};
             CAMERA = new String[]{"android.permission.CAMERA"};
             CONTACTS = new String[]{"android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.GET_ACCOUNTS"};
             LOCATION = new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
             MICROPHONE = new String[]{"android.permission.RECORD_AUDIO"};
             PHONE = new String[]{"android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE", "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG", "android.permission.USE_SIP", "android.permission.PROCESS_OUTGOING_CALLS"};
             SENSORS = new String[]{"android.permission.BODY_SENSORS"};
             SMS = new String[]{"android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_SMS", "android.permission.RECEIVE_WAP_PUSH", "android.permission.RECEIVE_MMS"};
             STORAGE = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
     }
     *
     */

    public enum PermissionType{
        PERMISSION_TYPE_LOCATION,
        PERMISSION_TYPE_STORAGE,
        PERMISSION_TYPE_CAMERA
    }

    public interface PermissionCallBack{
        void permissionYES();
        void permissionNO();
    }

    public static void openSettingWithSnackBar(final Context context , String permissionName){

        SnackbarTools.showSettingSnackBar(context,permissionName+"权限未开启,无法正常使用该功能");
    }

    public static void checkPermission(final Activity context, PermissionType permissionType,final PermissionCallBack permissionCallBack){
        if (permissionCallBack==null){
            return;
        }

        String permissionName = "";

        String[]permissions = new String[0];

        switch (permissionType){
            case PERMISSION_TYPE_CAMERA:
                permissionName = "相机";
                permissions = new String[]{Manifest.permission.CAMERA};
                break;
            case PERMISSION_TYPE_LOCATION:
                permissionName = "定位";
                permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
                break;
            case PERMISSION_TYPE_STORAGE:
                permissionName = "存储";
                permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                break;
        }

        RxPermissions rxPermissions = new RxPermissions(context);

        String finalPermissionName = permissionName;
        Consumer<Boolean> consumer = granted -> {

            if (granted) {
                // I can control the camera now
                permissionCallBack.permissionYES();
            } else {
                // Oups permission denied
                permissionCallBack.permissionNO();
                PermissionTools.openSettingWithSnackBar(context, finalPermissionName);
            }
        };
        rxPermissions
                .request(permissions)
                .subscribe(consumer);


    }



}
