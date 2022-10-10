package com.qiren.project.exceptions;

public class MysqlConnectionException extends RuntimeException {
    public MysqlConnectionException(String message) {
        super(message);
    }
}
