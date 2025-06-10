package com.example.pdprobiblioteka.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.springframework.stereotype.Service;

/**
 * Serwis obsługując TOTP.
 */
@Service
public class TotpService {

  /**
   * Generowanie secrety dla TOTP.
   *
   * @return wartość sekretnego klucza
   */
  public String generateSecretKey() {
    GoogleAuthenticator gauth = new GoogleAuthenticator();
    return gauth.createCredentials().getKey();
  }

  /**
   * Generowanie URL dla kodu QR w celu łatwiejszej weryfikacji TOTP.
   *
   * @param username nazwa użytkownika wykorzystywana w generowaniu
   * @param secret   klucz sekretny nadany użytkownikowi
   * @return URL kodu QR
   */
  public String getQrBarcodeUrl(String username, String secret) {
    GoogleAuthenticatorKey key = new GoogleAuthenticatorKey.Builder(secret).build();
    return GoogleAuthenticatorQRGenerator.getOtpAuthURL("BibliotekaSystem", username, key);
  }

  /**
   * Weryfikacja otrzymanego kodu, na podstawie secretu użytkownika.
   *
   * @param secret klucz sekretny nadany użytkownikowi
   * @param code   kod podany przez użytkownika, wygenerowany w aplikacji uwierzytelniającej
   * @return wartość boolean, czy kod jest poprawny, czy nie
   */
  public boolean verifyCode(String secret, int code) {
    GoogleAuthenticator gauth = new GoogleAuthenticator();
    return gauth.authorize(secret, code);
  }

}
