package com.oasis.assessment.errors;

public class ResourceAlreadyExistException extends RuntimeException{

    public ResourceAlreadyExistException() {
        super();
    }

    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
