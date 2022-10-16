package com.company.RESTwebservice.exceptions;

public class ArgumentIsNotANumberException extends RuntimeException{
    public ArgumentIsNotANumberException() {
        super();
    }

    public ArgumentIsNotANumberException(String message) {
        super(message);
    }

}
