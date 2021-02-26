package com.gender.detector.exceptions;

/**
 * Exception used when method of guess not exists.
 */
public class GuessMethodNotExists extends RuntimeException{

    public GuessMethodNotExists() {
        super("Guess method not exists.");
    }

    public GuessMethodNotExists(String message) {
        super(message);
    }
}
