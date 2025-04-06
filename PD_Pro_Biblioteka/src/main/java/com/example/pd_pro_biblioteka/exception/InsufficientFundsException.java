package com.example.pd_pro_biblioteka.exception;


public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}