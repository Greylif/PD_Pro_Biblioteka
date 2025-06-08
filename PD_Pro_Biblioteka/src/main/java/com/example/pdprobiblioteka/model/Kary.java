package com.example.pdprobiblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


/**
 * Klasa reprezentująca nałożoną karę (np. za nieterminowy zwrot książki).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Kary {

  @Setter
  @Getter
  private int id;
  @Setter
  @Getter
  private double kwota;
  @Setter
  @Getter
  private String dataWydaniaKary;
  @Setter
  @Getter
  private String terminZaplaty;
  @Setter
  @Getter
  private Boolean czyZaplacono;
  @Setter
  @Getter
  private int iduzytkownika;
  @Setter
  @Getter
  private String opis;

  /**
   * Konstruktor klasy.
   *
   * @param id              identyfikator kary
   * @param kwota           kwota pieniężna kary
   * @param dataWydaniaKary data nałożenia kary
   * @param terminZaplaty   termin na zapłatę kary
   * @param czyZaplacono    boolean czy kara zaostała zapłacona
   * @param iduzytkownika   id użytkownika, który otrzymał karę
   * @param opis            opis kary
   */
  public Kary(int id, double kwota, String dataWydaniaKary, String terminZaplaty,
      Boolean czyZaplacono, int iduzytkownika, String opis) {
    this.id = id;
    this.kwota = kwota;
    this.dataWydaniaKary = dataWydaniaKary;
    this.terminZaplaty = terminZaplaty;
    this.czyZaplacono = czyZaplacono;
    this.iduzytkownika = iduzytkownika;
    this.opis = opis;
  }


  @Override
  public String toString() {
    return "Kary{id=" + id + ", Kwota=" + kwota + ", Data_Wydania_Kary=" + dataWydaniaKary
        + ", Termin_Zaplaty=" + terminZaplaty + ", Czy_Zaplacono=" + czyZaplacono
        + ", id_uzytkownika=" + iduzytkownika + ", opis=" + opis + "}";
  }
}
