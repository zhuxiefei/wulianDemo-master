package com.wulian.business.gateway.model;

import org.springframework.stereotype.Component;
//由于mqttcallback中不能抛异常，抛出异常就会被捕获并断开mqtt连接，故写此类
//用于根据不同的i值抛出不同的异常
@Component
public class Count {
    private int i;
    public Count(){

    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}