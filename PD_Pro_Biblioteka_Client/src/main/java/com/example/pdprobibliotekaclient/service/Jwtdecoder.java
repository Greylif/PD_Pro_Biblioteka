package com.example.pdprobibliotekaclient.service;

import com.example.pdprobibliotekaclient.model.LogAdmin;
import com.example.pdprobibliotekaclient.model.LogUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;

/**
 * Klasa narzędziowa służąca do dekodowania tokenów JWT (JSON Web Token).
 * Pozwala na wydobycie informacji zawartych w ładunku (payload) tokena,
 * konwertując je na obiekty LogUser lub LogAdmin.
 */
public class Jwtdecoder {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Prywatny konstruktor uniemożliwiający stworzenie instancji klasy.
   */
  private Jwtdecoder() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Dekoduje token JWT do obiektu LogUser.
   *
   * @param jwt token JWT w formacie "header.payload.signature"
   * @return obiekt LogUser zawierający informacje z tokena
   * @throws JsonProcessingException jeśli nie uda się przetworzyć JSON-a w ładunku tokena
   * @throws IllegalArgumentException jeśli token ma niepoprawny format
   */
  public static LogUser decodeToLogUser(String jwt) throws JsonProcessingException {
    String[] parts = jwt.split("\\.");
    if (parts.length < 2) {
      throw new IllegalArgumentException("Invalid JWT format.");
    }
    String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
    return objectMapper.readValue(payloadJson, LogUser.class);
  }

  /**
   * Dekoduje token JWT do obiektu LogAdmin.
   *
   * @param jwt token JWT w formacie "header.payload.signature"
   * @return obiekt LogAdmin zawierający informacje z tokena
   * @throws JsonProcessingException jeśli nie uda się przetworzyć JSON-a w ładunku tokena
   * @throws IllegalArgumentException jeśli token ma niepoprawny format
   */
  public static LogAdmin decodeToLogAdm(String jwt) throws JsonProcessingException {
    String[] parts = jwt.split("\\.");
    if (parts.length < 2) {
      throw new IllegalArgumentException("Invalid JWT format.");
    }
    String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
    return objectMapper.readValue(payloadJson, LogAdmin.class);
  }

}
