package com.example.pdprobibliotekaclient.service;

import com.example.pdprobibliotekaclient.model.LogAdmin;
import com.example.pdprobibliotekaclient.model.LogUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;

public class Jwtdecoder {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private Jwtdecoder() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static LogUser decodeToLogUser(String jwt) throws JsonProcessingException {
    String[] parts = jwt.split("\\.");
    if (parts.length < 2) {
      throw new IllegalArgumentException("Invalid JWT format.");
    }
    String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
    return objectMapper.readValue(payloadJson, LogUser.class);
  }

  public static LogAdmin decodeToLogAdm(String jwt) throws JsonProcessingException {
    String[] parts = jwt.split("\\.");
    if (parts.length < 2) {
      throw new IllegalArgumentException("Invalid JWT format.");
    }
    String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
    return objectMapper.readValue(payloadJson, LogAdmin.class);
  }

}
