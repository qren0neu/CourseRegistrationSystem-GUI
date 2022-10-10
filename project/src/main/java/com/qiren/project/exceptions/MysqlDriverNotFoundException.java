package com.qiren.project.exceptions;

public class MysqlDriverNotFoundException extends RuntimeException {

    public MysqlDriverNotFoundException(String message) {
        super(message);
    }
}
