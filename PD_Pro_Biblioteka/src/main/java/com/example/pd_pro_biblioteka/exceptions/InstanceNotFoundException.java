package com.example.pd_pro_biblioteka.exceptions;

public class InstanceNotFoundException extends RuntimeException {
    public InstanceNotFoundException(String message) {
        super(message);
    }
    public InstanceNotFoundException(String Name, String message) {
        super("Blad odczytu z tabeli " + Name + ": " + message);
    }
}