package com.example.pd_pro_biblioteka.exceptions;

import lombok.Getter;

@Getter
public class AccountValidationException extends RuntimeException {
    private final String field;
    public AccountValidationException(String field, String message) {
        super(message);
        this.field = field;
    }
}
