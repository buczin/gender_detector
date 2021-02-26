package com.gender.detector.exceptions;

/**
 * Exception used when method of guess not exists.
 */
public class GuessMethodNotExistsException extends RuntimeException{

    public GuessMethodNotExistsException() {
        super("Guess method not exists.");
    }

    public GuessMethodNotExistsException(String message) {
        super(message);
    }
}
