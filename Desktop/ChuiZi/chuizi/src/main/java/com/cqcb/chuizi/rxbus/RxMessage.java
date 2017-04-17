package com.cqcb.chuizi.rxbus;

/**
 * Created by zaxcler on 2017/3/14.
 */

public class RxMessage {

    private int code;
    private Object object;

    public RxMessage() {}

    public RxMessage(int code, Object o) {
        this.code = code;
        this.object = o;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
