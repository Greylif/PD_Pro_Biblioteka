package com.example.pdprobiblioteka.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Klasa reprezentująca odpowiedź po pomyślnym uwierzytelnieniu.
 */
@Getter
@Setter
public class AuthResponse {

  private String token;

  /**
   * Konstruktor klasy.
   *
   * @param token wartość tokenu Jwt
   */
  public AuthResponse(String token) {
    this.token = token;
  }
}