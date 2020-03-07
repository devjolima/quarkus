package com.jonathaslima.flights.rest.exception;

public class FlyNotFoundException extends Exception {
	
    private String message;

    public static FlyNotFoundException createWith(String message) {
        return new FlyNotFoundException(message);
    }

    public FlyNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "User '" + message + "' not found";
    }

}
