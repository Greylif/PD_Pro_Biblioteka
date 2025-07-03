package com.example.pdprobibliotekaclient.model;

/**
 * Klasa DTO (Data Transfer Object) reprezentująca dane książki z filtrem.
 * Używana do przesyłania danych między klientem a serwerem.
 */
public class FiltrDto {

  public int id;
  public String Tytul;
  public String Gatunek;
  public int id_autora;
  public int Data_Wydania;
  public String Dodano;
  public boolean Rezerwacja;
  public boolean czy_wypozyczono;
  public int id_placowki;
  public String Imie;
  public String Nazwisko;
}
