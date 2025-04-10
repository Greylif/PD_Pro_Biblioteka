package com.example.pd_pro_biblioteka.exceptions;

import lombok.Getter;

@Getter
public class InsufficientFundsException extends RuntimeException {
    private final String accountNumber;
    private final String operation;
    public InsufficientFundsException(String accountNumber, String
            operation, String message) {
        super(message);
        this.accountNumber = accountNumber;
        this.operation = operation;
    }
}