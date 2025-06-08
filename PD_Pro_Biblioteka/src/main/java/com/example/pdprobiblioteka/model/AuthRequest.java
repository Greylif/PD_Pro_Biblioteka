package com.example.pdprobiblioteka.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Klasa reprezentująca żądanie logowania.
 */
@Getter
@Setter
public class AuthRequest {

  private String username;
  private String password;
  private String totp;
}
