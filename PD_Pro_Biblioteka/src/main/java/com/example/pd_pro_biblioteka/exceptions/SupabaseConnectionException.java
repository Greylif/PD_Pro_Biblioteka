package com.example.pd_pro_biblioteka.exceptions;

public class SupabaseConnectionException extends RuntimeException {
    public SupabaseConnectionException(String message) {
        super(message);
    }
}