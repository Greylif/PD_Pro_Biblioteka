package com.example.pdprobiblioteka.model;

import lombok.Data;

@Data
public class MfaEnableRequest {
  private String username;
  private String role;
  private String totpCode;
}
