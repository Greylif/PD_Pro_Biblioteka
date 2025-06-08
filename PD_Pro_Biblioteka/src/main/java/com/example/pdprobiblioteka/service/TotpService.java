package com.example.pdprobiblioteka.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.springframework.stereotype.Service;

@Service
public class TotpService {

  public String generateSecretKey() {
    GoogleAuthenticator gauth = new GoogleAuthenticator();
    return gauth.createCredentials().getKey();
  }

  public String getQrBarcodeUrl(String username, String secret) {
    GoogleAuthenticatorKey key = new GoogleAuthenticatorKey.Builder(secret).build();
    return GoogleAuthenticatorQRGenerator.getOtpAuthURL("BibliotekaSystem", username, key);
  }

  public boolean verifyCode(String secret, int code) {
    GoogleAuthenticator gauth = new GoogleAuthenticator();
    return gauth.authorize(secret, code);
  }

}
