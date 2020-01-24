package org.embl.emblApp.service.exception;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException() {
        super("Email is already in use!");
    }

}
