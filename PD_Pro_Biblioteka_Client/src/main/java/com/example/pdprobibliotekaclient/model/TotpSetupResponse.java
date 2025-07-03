package com.example.pdprobibliotekaclient.model;

/**
 * Klasa reprezentująca odpowiedź serwera przy inicjalizacji konfiguracji TOTP (Time-based One-Time Password).
 * Zawiera URL do kodu QR oraz sekret do skonfigurowania aplikacji uwierzytelniającej.
 */
public class TotpSetupResponse {

  private String qrCodeUrl;
  private String secret;

  public String getQrCodeUrl() {
    return qrCodeUrl;
  }

  public String getSecret() {
    return secret;
  }
}
