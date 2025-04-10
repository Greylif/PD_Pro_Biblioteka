package com.example.pd_pro_biblioteka.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
    public AccountNotFoundException(String pesel, String message) {
        super("Account with PESEL " + pesel + " not found: " + message);
    }
}