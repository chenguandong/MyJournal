package com.smart.weather.tools.eventbus;

import java.io.Serializable;

/**
 * @author guandongchen
 * @date 2018/12/11
 */
public class MessageEvent implements Serializable {
    //内容有更新
    public static final  int NOTE_CHANGE =  0X000000001;

    private String message;
    private int tag;

    public MessageEvent(String message, int tag) {
        this.message = message;
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
