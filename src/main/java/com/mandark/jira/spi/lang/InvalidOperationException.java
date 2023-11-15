package com.mandark.jira.spi.lang;

public class InvalidOperationException extends ApplicationException {

    private static final long serialVersionUID = 1L;


    // Constructors
    // ------------------------------------------------------------------------

    public InvalidOperationException(String message) {
        super(message, message);
    }

    public InvalidOperationException(String message, String userMessage) {
        super(message, userMessage);
    }

}