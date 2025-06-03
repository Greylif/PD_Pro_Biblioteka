package com.example.pdprobiblioteka.exceptions;

public class InstanceNotFoundException extends RuntimeException {

  public InstanceNotFoundException(String message) {
    super(message);
  }

  public InstanceNotFoundException(String name, String message) {
    super("Blad odczytu z tabeli " + name + ": " + message);
  }
}