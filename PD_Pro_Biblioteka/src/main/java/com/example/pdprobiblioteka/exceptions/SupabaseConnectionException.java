package com.example.pdprobiblioteka.exceptions;

public class SupabaseConnectionException extends RuntimeException {

  public SupabaseConnectionException(String message, Throwable cause) {
    super(message, cause);
  }
}