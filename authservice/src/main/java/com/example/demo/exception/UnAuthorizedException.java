package com.example.demo.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
        super(
                "UNAUTHORIZED TOKEN"
        );
    }
}
