package com.codelang.throttle;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * @author codelang
 * date on 2019/2/26.
 */
public class HandlerFactory {
    static Handler create(HandlerType type, WQThrottle.CallBack callBack) {
        switch (type) {
            case MAIN_THREAD:
                return generateMainHandler(callBack);
            case THREAD:
                return generateThreadHandler(callBack);
        }
        return null;
    }

    private static Handler generateMainHandler(final WQThrottle.CallBack callBack) {
        return new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (callBack != null)
                    callBack.delayResult(msg.what, msg.obj);
            }
        };
    }

    private static Handler generateThreadHandler(final WQThrottle.CallBack callBack) {
        HandlerThread handlerThread = new HandlerThread("throttle");
        handlerThread.start();
        return new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (callBack != null)
                    callBack.delayResult(msg.what, msg.obj);
            }
        };
    }
}
