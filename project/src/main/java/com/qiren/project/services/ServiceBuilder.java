package com.qiren.project.services;

import com.qiren.project.exceptions.ServiceInitilizeException;
import com.qiren.project.util.ExceptionCenter;

import java.util.HashMap;

public class ServiceBuilder {

    private ServiceBuilder() {

    }

    public static ServiceBuilder getInstance() {
        return Inner.instance;
    }

    private HashMap<String, Object> services = new HashMap<>();

    public <T extends AbstractService> T build(Class<T> type) {
        try {
            Object o = services.get(type.getSimpleName());
            if (null != o) {
                return (T) o;
            }
            T instance = type.getConstructor().newInstance();
            services.put(type.getSimpleName(), instance);
            return instance;
        } catch (Exception e) {
            ExceptionCenter.handleException(
                    new ServiceInitilizeException(e.getMessage()));
        }
        return null;
    }

    private static class Inner {
        static final ServiceBuilder instance = new ServiceBuilder();
    }
}
