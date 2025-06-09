package com.example.pdprobiblioteka.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Klasa reprezentuje odpowiedź na żądanie włączania TOTP.
 */
@Data
@AllArgsConstructor
public class MfaSetupResponse {
  private String secret;
  private String qrCodeUri;
}
