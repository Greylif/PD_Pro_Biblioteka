package com.example.pdprobibliotekaclient.model;

/**
 * Data Transfer Object (DTO) reprezentujący książkę.
 * Używany do przesyłania danych o książce między warstwami aplikacji.
 */
public class KsiazkaDto {

  public int id;
  public String Tytul;
  public String Gatunek;
  public String Dodano;
  public int id_autora;
  public int id_placowki;
  public boolean Rezerwacja;
  public boolean czy_wypozyczono;
  public int Data_Wydania;

}
