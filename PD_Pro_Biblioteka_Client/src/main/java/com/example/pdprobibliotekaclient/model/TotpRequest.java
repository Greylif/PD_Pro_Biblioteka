package com.example.pdprobibliotekaclient.model;

import lombok.Data;

@Data
public class TotpRequest {

  private String username;
  private Integer totp; // może być null

  // Konstruktor
  public TotpRequest(String username, Integer twoFA) {
    this.username = username;
    this.totp = twoFA;
  }

}
