package com.example.pdprobiblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Wypozyczenia {

  @Setter
  @Getter
  private int id;
  @Setter
  @Getter
  private String dataWypozyczenia;
  @Setter
  @Getter
  private String dataOddania;
  @Setter
  @Getter
  private String terminOddania;
  @Setter
  @Getter
  private int idksiazki;
  @Setter
  @Getter
  private int iduzytkownika;

  public Wypozyczenia() {
  }

  public Wypozyczenia(int id, String dataWypozyczenia, String dataOddania, String terminOddania,
      int idksiazki, int iduzytkownika) {
    this.id = id;
    this.dataWypozyczenia = dataWypozyczenia;
    this.dataOddania = dataOddania;
    this.terminOddania = terminOddania;
    this.idksiazki = idksiazki;
    this.iduzytkownika = iduzytkownika;
  }


  @Override
  public String toString() {
    return "Wypozyczenia{id=" + id + ", Data_Wypozyczenia=" + dataWypozyczenia + ", Data_Oddania="
        + dataOddania + ", Termin_Oddania=" + terminOddania + ", id_ksiazki=" + idksiazki
        + ", id_uzytkownika=" + iduzytkownika + "}";
  }
}
