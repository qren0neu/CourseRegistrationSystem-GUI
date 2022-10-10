package com.qiren.project.exceptions;

public class PropertyFileNotFoundException extends RuntimeException {

    public static final String ERROR_MESSAGE = "Property File Not Found!";

    public PropertyFileNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
