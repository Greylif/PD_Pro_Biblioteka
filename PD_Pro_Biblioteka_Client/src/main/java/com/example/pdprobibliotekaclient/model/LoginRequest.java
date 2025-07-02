package com.example.pdprobibliotekaclient.model;

import lombok.Data;

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
