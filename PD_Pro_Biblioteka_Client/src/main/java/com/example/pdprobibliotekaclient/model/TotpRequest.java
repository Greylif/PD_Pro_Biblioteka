package com.example.pdprobibliotekaclient.model;

import lombok.Data;

/**
 * Klasa reprezentująca żądanie uwierzytelnienia dwuskładnikowego (TOTP).
 * Zawiera nazwę użytkownika oraz kod TOTP (Time-based One-Time Password).
 */
@Data
public class TotpRequest {

  private String username;
  private Integer totp;

  public TotpRequest(String username, Integer twofa) {
    this.username = username;
    this.totp = twofa;
  }

}
