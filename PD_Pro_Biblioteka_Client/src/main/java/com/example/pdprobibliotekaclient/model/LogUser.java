package com.example.pdprobibliotekaclient.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class LogUser {

  @Getter
  public static String userIdStr;
  private static Uzytkownik user;
  private static String userToken;
  private static String userEmail;
  @Getter
  @Setter
  private String sub;
  @Getter
  @Setter
  private String role;
  @Getter
  @Setter
  private String userId;
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
  public LogUser(
      @JsonProperty("sub") String sub,
      @JsonProperty("role") String role,
      @JsonProperty("userId") String userId,
      @JsonProperty("isAdmin") String isAdmin,
      @JsonProperty("iat") long iat,
      @JsonProperty("exp") long exp
  ) {
    this.sub = sub;
    this.role = role;
    this.userId = userId;
    this.isAdmin = isAdmin;
    this.iat = iat;
    this.exp = exp;
    userIdStr = userId;
  }

  public static void setUserIdStr(String token) {
    userIdStr = token;
  }

  public static void clearUser() {
    userToken = null;
    userIdStr = null;
  }

  public static String getUserEmail() {
    return userEmail;
  }

  public static void setUserEmail(String email) {
    userEmail = email;
  }

  public static String getUserToken() {
    return userToken;
  }

  public static void setUserToken(String token) {
    userToken = token;
  }

  public static void set(Uzytkownik u) {
    user = u;
  }

  public static Uzytkownik get() {
    return user;
  }

  public static void clear() {
    user = null;
  }
}
