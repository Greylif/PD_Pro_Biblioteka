package com.example.pdprobiblioteka.exceptions;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ErrorResponse {

  private final LocalDateTime timestamp;
  private final int status;
  private final String error;
  private final String message;
  @Setter
  private String path;
  @Setter
  private Map<String, String> details;

  public ErrorResponse(int status, String error, String message) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.error = error;
    this.message = message;
  }
}