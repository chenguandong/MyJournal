package com.smart.journal.tools.http;

import com.blankj.utilcode.util.NetworkUtils;

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

    public MyCallBack() {
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        callBackBean = new CallBackBean<T>(call, response);
        if (response.code() != 200) {
            callBackBean.setErrorMeg("请求出错");
        } else {
            onSuccess(callBackBean);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        callBackBean = new CallBackBean<>(call, t);
        if (!NetworkUtils.isConnected()) {
            callBackBean.setErrorMeg("请检查网络连接");
        }
        onFail(callBackBean);
    }

    protected abstract void onSuccess(CallBackBean<T> callBackBean);

    protected abstract void onFail(CallBackBean<T> callBackBean);
}
