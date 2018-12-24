package com.smart.journal.tools.http;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author guandongchen
 * @date 2018/1/16
 */

public class CallBackBean<T> {

    private Call<T> call;
    private Throwable throwable;
    private Response<T> response;
    private T responseBody;
    //错误消息提醒
    private String errorMeg;

    public CallBackBean(Call<T> call, Response<T> response) {
        this.call = call;
        this.response = response;
        setResponseBody(response.body());

    }

    public CallBackBean(Call<T> call, Throwable throwable) {
        this.call = call;
        this.throwable = throwable;
    }

    public Call<T> getCall() {
        return call;
    }

    public void setCall(Call<T> call) {
        this.call = call;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Response<T> getResponse() {
        return response;
    }

    public void setResponse(Response<T> response) {
        this.response = response;
    }

    public String getErrorMeg() {
        return errorMeg;
    }

    public void setErrorMeg(String errorMeg) {
        this.errorMeg = errorMeg;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }
}
