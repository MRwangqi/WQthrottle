package com.codelang.throttle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author codelang
 * date on 2019/2/26.
 */
public class WQThrottle {
    /**
     * default handler is MainHandler
     */
    private Handler handler;
    private List<CallBack> callBacks = new ArrayList<>();

    private WQThrottle() {
        handler = HandlerFactory.create(HandlerType.MAIN_THREAD, callBacks);
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
     * 切换线程
     *
     * @param type
     */
    public void init(HandlerType type) {
        handler = HandlerFactory.create(type, callBacks);
    }

    /**
     * 注册操作
     *
     * @param callBack 参数回调
     */
    public void register(CallBack callBack) {
        callBacks.add(callBack);
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

    /**
     * 反注册，移除 callback 回调
     *
     * @param callBack
     */
    public void unregister(CallBack callBack) {
        callBacks.remove(callBack);
    }

    public interface CallBack {
        /**
         * @param tag 标识
         * @param obj 参数
         */
        void throttleResult(int tag, Object obj);
    }
}


