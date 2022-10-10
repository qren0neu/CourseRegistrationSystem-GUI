package com.qiren.project.ui.core;

import java.util.HashMap;

public class Intend {

    private String pageRequest;
    private String target;
    private String previous;

    private HashMap<String, String> extras;

    public Intend(String pageRequest, String previous) {
        this.pageRequest = pageRequest;
        this.previous = previous;
    }

    public Intend(String pageRequest, String previous, String target) {
        this.pageRequest = pageRequest;
        this.target = target;
        this.previous = previous;
    }

    public void addExtra(String key, String value) {
        if (null == extras) {
            extras = new HashMap<>();
        }
        extras.put(key, value);
    }

    public String getExtra(String key) {
        if (null == extras) {
            extras = new HashMap<>();
        }
        return extras.get(key);
    }

    public String getPageRequest() {
        return pageRequest;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public HashMap<String, String> getExtras() {
        return extras;
    }
}
