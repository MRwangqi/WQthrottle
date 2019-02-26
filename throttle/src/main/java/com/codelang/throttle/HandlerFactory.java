package com.codelang.throttle;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * @author codelang
 * date on 2019/2/26.
 */
public class HandlerFactory {
    static Handler create(HandlerType type) {
        switch (type) {
            case MAIN_THREAD:
                return generateMainHandler();
            case THREAD:
                return generateThreadHandler();
        }
        return null;
    }

    private static Handler generateMainHandler() {
        return new Handler(Looper.getMainLooper());
    }

    private static Handler generateThreadHandler() {
        HandlerThread handlerThread = new HandlerThread("throttle");
        Handler handle = new Handler(handlerThread.getLooper());
        handlerThread.start();
        return handle;
    }
}
