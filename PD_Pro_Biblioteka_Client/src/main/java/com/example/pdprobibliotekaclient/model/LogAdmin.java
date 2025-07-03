package com.example.pdprobibliotekaclient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Klasa reprezentująca dane logowania administratora.
 * Zawiera informacje o tożsamości, roli, tokenie oraz czasie ważności sesji.
 * Wykorzystuje Jackson do deserializacji JSON.
 */
public class LogAdmin {

  @Getter
  public static String admIdStr;
  private static AdminModel admin;
  private static String admToken;
  @Getter
  @Setter
  private String sub;
  @Getter
  @Setter
  private String role;
  @Getter
  @Setter
  private String admId;
  @Getter
  @Setter
  private String isAdmin;
  @Getter
  @Setter
  private long iat;
  @Getter
  @Setter
  private long exp;

  @JsonCreator
  public LogAdmin(
      @JsonProperty("sub") String sub,
      @JsonProperty("role") String role,
      @JsonProperty("userId") String userId,
      @JsonProperty("isAdmin") String isAdmin,
      @JsonProperty("iat") long iat,
      @JsonProperty("exp") long exp
  ) {
    this.sub = sub;
    this.role = role;
    this.admId = userId;
    this.isAdmin = isAdmin;
    this.iat = iat;
    this.exp = exp;
    admIdStr = userId;
  }

  public static void clearAdmin() {
    admToken = null;
    admIdStr = null;
  }

  public static String getAdminToken() {
    return admToken;
  }

  public static void setAdminToken(String token) {
    admToken = token;
  }

  public static void set(AdminModel a) {
    admin = a;
  }

  public static AdminModel get() {
    return admin;
  }
}
