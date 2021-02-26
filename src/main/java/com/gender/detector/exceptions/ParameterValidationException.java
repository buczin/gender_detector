package com.gender.detector.exceptions;

/**
 * Exception used when operations are tried to made with wrong input parameters.
 */
public class ParameterValidationException extends RuntimeException {

    private static final long serialVersionUID = -779041494072937362L;

    public ParameterValidationException() {
        super("One of the input parameters is not correct");
    }

    public ParameterValidationException(String message) {
        super(message);
    }
}

