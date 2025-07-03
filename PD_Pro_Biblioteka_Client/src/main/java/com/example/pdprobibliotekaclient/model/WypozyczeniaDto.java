package com.example.pdprobibliotekaclient.model;

/**
 * Data Transfer Object (DTO) reprezentujący wypożyczenie książki.
 * Służy do przenoszenia danych pomiędzy warstwami aplikacji (np. klient-serwer).
 * Zawiera podstawowe informacje o wypożyczeniu, takie jak daty i identyfikatory.
 */
public class WypozyczeniaDto {

  public int id;
  public String Data_Wypozyczenia;
  public String Data_Oddania;
  public String Termin_Oddania;
  public int id_ksiazki;
  public int id_uzytkownika;

}
