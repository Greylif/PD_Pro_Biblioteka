package com.example.pdprobiblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Klasa reprezentująca użytkownika biblioteki.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Uzytkownik {

  private int id;
  private String imie;
  private String nazwisko;
  private String dataUrodzenia;
  private String haslo;
  private String nazwaUzytkownika;
  private String email;
  private Boolean zablokowany;
  private Boolean mfaEnabled;
  private String mfaSecret;
  private String role;


  @Override
  public String toString() {
    return "Uzytkownik{id=" + id + ", Imie='" + imie + "', Nazwisko='" + nazwisko
        + "', Data_Urodzenia='" + dataUrodzenia + "', Nazwa_Uzytkownika='" + nazwaUzytkownika
        + "', Haslo='" + haslo + "', Email=" + email
        + ", Zablokowany=" + zablokowany + ", Mfa_enabled=" + mfaEnabled
        + ", Mfa_secret=" + mfaSecret + ", Role=" + role + "}";
  }

}
