package com.example.pdprobibliotekaclient.service;

import com.example.pdprobibliotekaclient.model.logAdmin;
import com.example.pdprobibliotekaclient.model.logUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;

public class Jwtdecoder {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private Jwtdecoder() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static logUser decodeToLogUser(String jwt) throws JsonProcessingException {
    String[] parts = jwt.split("\\.");
    if (parts.length < 2) {
      throw new IllegalArgumentException("Invalid JWT format.");
    }
    String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
    return objectMapper.readValue(payloadJson, logUser.class);
  }

  public static logAdmin decodeToLogAdm(String jwt) throws JsonProcessingException {
    String[] parts = jwt.split("\\.");
    if (parts.length < 2) {
      throw new IllegalArgumentException("Invalid JWT format.");
    }
    String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
    return objectMapper.readValue(payloadJson, logAdmin.class);
  }

}
