package com.example.pdprobibliotekaclient.model;

public class TotpSetupResponse {

  private String qrCodeUrl;
  private String secret;

  // Gettery
  public String getQrCodeUrl() {
    return qrCodeUrl;
  }

  public String getSecret() {
    return secret;
  }
}
