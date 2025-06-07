package com.example.pdprobiblioteka;

import static org.junit.jupiter.api.Assertions.assertFalse;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;

/**
 * Klasa testowa służąca do sprawdzania obecności zmiennej środowiskowej EMAIL_PASSWORD.
 * Wykorzystuje bibliotekę dotenv do wczytania zmiennych środowiskowych z pliku .env.
 */
class EnvVarTest {

  @Test
  void testEmailPasswordDotenv() {
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    String emailPassword = dotenv.get("EMAIL_PASSWORD");
    System.out.println("EMAIL_PASSWORD = " + emailPassword);
    assertFalse(emailPassword == null || emailPassword.isEmpty(), "EMAIL_PASSWORD should be set");
  }
}
