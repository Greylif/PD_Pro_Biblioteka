package com.example.pdprobibliotekaclient.model;

/**
 * Data Transfer Object (DTO) reprezentujący użytkownika.
 * Służy do przesyłania danych użytkownika pomiędzy warstwami aplikacji,
 * bez powiązania z implementacją JavaFX.
 */
public class UzytkownikDto {

  public int id;
  public String Imie;
  public String Nazwisko;
  public String Nazwa_Uzytkownika;
  public String Haslo;
  public String Email;
  public String Data_Urodzenia;
  public boolean Zablokowany;
  public boolean Mfa_Enabled;
  public String Mfa_Secret;

}
