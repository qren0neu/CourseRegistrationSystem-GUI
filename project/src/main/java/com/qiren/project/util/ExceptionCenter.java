package com.qiren.project.util;

import java.util.HashMap;
import java.util.Map;

public class ExceptionCenter {

    private static Map<String, ExceptionListener> handlerMap = new HashMap<>();

    public static void registerExceptionHandler(Class<? extends Exception> c, ExceptionListener listener) {
        handlerMap.put(c.getSimpleName(), listener);
    }

    public static void handleException(Exception e) {
        if (null == e) {
            return;
        }
        ExceptionListener listener = handlerMap.get(e.getClass().getSimpleName());
        if (null == listener) {
            LoggingCenter.error("Handler not found for: " + e.getClass().getSimpleName());
            LoggingCenter.error(e.getMessage());
        } else {
            listener.onHandleException(e);
        }
    }

    public interface ExceptionListener {
        void onHandleException(Exception e);
    }
}
