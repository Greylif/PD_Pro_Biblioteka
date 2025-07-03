package com.example.pdprobiblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Klasa reprezentująca administratora systemu bibliotecznego.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

  private int id;
  private String imie;
  private String nazwisko;
  private String nazwaUzytkownika;
  private String haslo;
  private Integer idplacowki;
  private Boolean mfaEnabled;
  private String mfaSecret;
  private String role;

  @Override
  public String toString() {
    return "Admin{id=" + id + ", Imie='" + imie + "', Nazwisko='" + nazwisko
        + "', Nazwa_Uzytkownika='" + nazwaUzytkownika + "', Haslo='" + haslo + "', id_placowki="
        + idplacowki + "', mfa_enabled=" + mfaEnabled + ", mfa_secret=" + mfaSecret
        + ", role='" + role + "}";
  }
}
