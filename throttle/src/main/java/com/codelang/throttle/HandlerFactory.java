package com.codelang.throttle;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.List;

/**
 * @author codelang
 * date on 2019/2/26.
 */
public class HandlerFactory {
    static Handler create(HandlerType type, List<WQThrottle.CallBack> callBacks) {
        switch (type) {
            case MAIN_THREAD:
                return generateMainHandler(callBacks);
            case THREAD:
                return generateThreadHandler(callBacks);
        }
        return null;
    }

    private static Handler generateMainHandler(final List<WQThrottle.CallBack> callBacks) {
        return new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                sendMessages(msg, callBacks);
            }
        };
    }

    private static Handler generateThreadHandler(final List<WQThrottle.CallBack> callBacks) {
        HandlerThread handlerThread = new HandlerThread("throttle");
        handlerThread.start();
        return new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                sendMessages(msg, callBacks);
            }
        };
    }

    private static void sendMessages(Message msg, List<WQThrottle.CallBack> callBacks) {
        for (int i = 0, len = callBacks.size(); i < len; i++) {
            callBacks.get(i).throttleResult(msg.what, msg.obj);
        }
    }
}
