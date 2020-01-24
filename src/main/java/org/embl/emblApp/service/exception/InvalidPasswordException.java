package org.embl.emblApp.service.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("Incorrect password");
    }

}
