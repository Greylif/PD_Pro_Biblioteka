package com.example.pdprobibliotekaclient.model;

import lombok.Data;

@Data
public class TOTPRequest {

  private String username;
  private Integer totp; // może być null

  // Konstruktor
  public TOTPRequest(String username, Integer twoFA) {
    this.username = username;
    this.totp = twoFA;
  }

}
