package com.qiren.project.exceptions;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(String pageName) {
        super("Page " + pageName + " Not Found!");
    }
}
