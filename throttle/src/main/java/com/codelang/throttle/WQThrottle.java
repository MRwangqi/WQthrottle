package com.codelang.throttle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

/**
 * @author codelang
 * date on 2019/2/26.
 */
public class WQThrottle {
    /**
     * default handler is MainHandler
     */
    private Handler handler = HandlerFactory.create(HandlerType.MAIN_THREAD, null);

    private WQThrottle() {
    }

    private static class Inner {
        private static WQThrottle throttle = new WQThrottle();
    }


    //todo 1、初始化时需要确定该操作处于什么线程的
    //todo 2、实例.delay 开始启动延时，参数回调


    public static WQThrottle getInstance() {
        return Inner.throttle;
    }

    /**
     * 初始化操作
     *
     * @param type     线程类型
     * @param callBack 参数回调
     */
    public void init(@NonNull HandlerType type, CallBack callBack) {
        if (type == null) {
            throw new RuntimeException("WQThrottle init HandlerType must not be null");
        }
        handler = HandlerFactory.create(type, callBack);
    }


    /**
     * @param tag        延时标识
     * @param timeMillis 延时时间(毫秒)
     * @param params     参数
     */
    public void delay(int tag, long timeMillis, Object params) {
        handler.removeMessages(tag);
        Message msg = handler.obtainMessage();
        msg.obj = params;
        msg.what = tag;
        handler.sendMessageDelayed(msg, timeMillis);
    }


    public interface CallBack {
        /**
         * @param tag 标识
         * @param obj 参数
         */
        void delayResult(int tag, Object obj);
    }
}


