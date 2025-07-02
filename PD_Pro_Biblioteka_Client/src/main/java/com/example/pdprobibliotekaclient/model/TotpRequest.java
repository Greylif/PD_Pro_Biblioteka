package com.example.pdprobibliotekaclient.model;

import lombok.Data;

@Data
public class TotpRequest {

  private String username;
  private Integer totp;

  public TotpRequest(String username, Integer twofa) {
    this.username = username;
    this.totp = twofa;
  }

}
