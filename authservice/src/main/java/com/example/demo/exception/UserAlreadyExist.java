package com.example.demo.exception;

public class UserAlreadyExist extends RuntimeException{
    public UserAlreadyExist() {
        super("User Already Exist");
    }
}
