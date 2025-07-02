package com.example.pdprobibliotekaclient.model;

import lombok.Data;

@Data
public class LoginRequest {

  private String username;
  private String password;
  private Integer totp; // może być null

  public LoginRequest(String username, String password, Integer twoFA) {
    this.username = username;
    this.password = password;
    this.totp = twoFA;
  }
}
