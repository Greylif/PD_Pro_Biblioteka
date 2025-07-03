package com.example.pdprobibliotekaclient.model;

/**
 * Klasa DTO reprezentująca dane kary.
 * Używana do transferu danych między warstwami aplikacji.
 */
public class KaryDto {

  public int id;
  public double Kwota;
  public String Data_Wydania_Kary;
  public String Termin_Zaplaty;
  public String Czy_Zaplacono;
  public int id_uzytkownika;
  public String opis;
}
