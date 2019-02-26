package com.codelang.throttle;

/**
 * @author codelang
 * date on 2019/2/26.
 */
public class WQThrottle {

    private WQThrottle() {
    }

    private static class Inner {
        private static WQThrottle throttle = new WQThrottle();
    }


    //todo 1、初始化时需要确定该操作处于什么线程的
    //todo 2、实例.delay 开始启动延时，参数回调


    public WQThrottle getInstance() {
        return Inner.throttle;
    }


    public void init() {

    }

    /**
     * @param tag        延时标识
     * @param timeMillis 延时时间(毫秒)
     * @param params     参数
     */
    public void delay(int tag, long timeMillis, Object params) {

    }


}


