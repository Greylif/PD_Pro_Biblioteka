package com.example.pdprobibliotekaclient.model;

import lombok.Data;

/**
 * Klasa reprezentująca żądanie logowania użytkownika.
 * Zawiera dane potrzebne do uwierzytelnienia: nazwę użytkownika, hasło oraz opcjonalny kod TOTP (2FA).
 */
@Data
public class LoginRequest {

  private String username;
  private String password;
  private Integer totp;

  public LoginRequest(String username, String password, Integer twofa) {
    this.username = username;
    this.password = password;
    this.totp = twofa;
  }
}
