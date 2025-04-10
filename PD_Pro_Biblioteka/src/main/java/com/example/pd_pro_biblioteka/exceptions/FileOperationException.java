package com.example.pd_pro_biblioteka.exceptions;

public class FileOperationException extends RuntimeException {
    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}