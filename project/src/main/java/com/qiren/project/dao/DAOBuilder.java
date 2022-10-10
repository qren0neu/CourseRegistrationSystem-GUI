package com.qiren.project.dao;

import com.qiren.project.exceptions.ServiceInitilizeException;
import com.qiren.project.util.ExceptionCenter;

import java.util.HashMap;

public class DAOBuilder {

    private HashMap<String, Object> daoMap = new HashMap<>();

    private DAOBuilder() {

    }

    public <T extends AbstractDAO> T build(Class<T> type) {
        try {
            Object o = daoMap.get(type.getSimpleName());
            if (null != o) {
                return (T) o;
            }
            T instance = type.getConstructor().newInstance();
            daoMap.put(type.getSimpleName(), instance);
            return instance;
        } catch (Exception e) {
            ExceptionCenter.handleException(
                    new ServiceInitilizeException(e.getMessage()));
        }
        return null;
    }

    public static DAOBuilder getInstance() {
        return Inner.instance;
    }

    private static class Inner {
        static final DAOBuilder instance = new DAOBuilder();
    }
}
