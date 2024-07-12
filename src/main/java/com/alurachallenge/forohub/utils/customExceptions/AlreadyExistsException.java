package com.alurachallenge.forohub.utils.customExceptions;

public class AlreadyExistsException extends RuntimeException{

    public AlreadyExistsException() {
        this("Already exists");
    }

    public AlreadyExistsException(String message) {
        super(message);
    }
}
