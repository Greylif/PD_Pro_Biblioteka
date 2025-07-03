package com.example.pdprobibliotekaclient.model;

/**
 * Reprezentuje dane administratora w aplikacji.
 */
public class AdminDto {

  public int id;
  public String Imie;
  public String Nazwisko;
  public String Nazwa_Uzytkownika;
  public String Haslo;
  public int id_placowki;
  public boolean Mfa_Enabled;
  public String Mfa_Secret;
}
