package com.example.pd_pro_biblioteka.exceptionsold;

public class SupabaseConnectionException extends RuntimeException {
    public SupabaseConnectionException(String message) {
        super(message);
    }
}