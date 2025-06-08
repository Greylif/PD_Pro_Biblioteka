package com.example.pdprobiblioteka.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.springframework.stereotype.Service;

@Service
public class TotpService {

  private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

  public String generateSecretKey() {
    GoogleAuthenticator gAuth = new GoogleAuthenticator();
    return gAuth.createCredentials().getKey();
  }

  public String getQRBarcodeURL(String username, String secret) {
    GoogleAuthenticatorKey key = new GoogleAuthenticatorKey.Builder(secret).build();
    return GoogleAuthenticatorQRGenerator.getOtpAuthURL("BibliotekaSystem", username, key);
  }

  public boolean verifyCode(String secret, int code) {
    GoogleAuthenticator gAuth = new GoogleAuthenticator();
    return gAuth.authorize(secret, code);
  }

}
