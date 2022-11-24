package net.backend.paylens.exception.custom;

public class CustomUnauthorizedException extends Exception {

    public CustomUnauthorizedException(String message) {
        super(message);
    }
}