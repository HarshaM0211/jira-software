package com.mandark.jira.spi.lang;

public class ValidationException extends ApplicationException {

    private static final long serialVersionUID = 1L;


    // Constructors
    // ------------------------------------------------------------------------

    public ValidationException(String message) {
        super(message, message);
    }

    public ValidationException(String message, String userMessage) {
        super(message, userMessage);
    }

    public ValidationException(String message, Throwable throwable) {
        super(message, message, throwable);
    }

}
