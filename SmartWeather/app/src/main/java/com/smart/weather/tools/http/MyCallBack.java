package com.smart.weather.tools.http;

import android.content.Context;

import com.blankj.utilcode.util.NetworkUtils;
import com.smart.weather.tools.SnackbarTools;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author guandongchen
 * @date 2018/1/16
 */

public abstract class MyCallBack<T extends Serializable> implements Callback<T> {

    private CallBackBean<T> callBackBean;

    private Context context;

    public MyCallBack(Context context) {
        this.callBackBean = callBackBean;
        this.context = context;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        callBackBean = new CallBackBean<T>(call, response);
        if (response.code() != 200) {
            callBackBean.setErrorMeg("请求出错");
        }
        if (context != null) {
            onSuccess(callBackBean);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        callBackBean = new CallBackBean<T>(call, t);
        if (!NetworkUtils.isConnected()) {
            SnackbarTools.showSimpleSnackbar(context, "请检查网络连接");
            callBackBean.setErrorMeg("请检查网络连接");
        }
        if (context != null) {

            onFail(callBackBean);
        }

    }

    protected abstract void onSuccess(CallBackBean<T> callBackBean);

    protected abstract void onFail(CallBackBean<T> callBackBean);

}
