package com.example.pdprobiblioteka.model;

import lombok.Data;

/**
 * Klasa reprezentuje żądanie włączenia TOTP.
 */
@Data
public class MfaEnableRequest {
  private String username;
  private String role;
  private String totpCode;
}
