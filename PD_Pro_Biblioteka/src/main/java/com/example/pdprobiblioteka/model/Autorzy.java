package com.example.pdprobiblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Klasa reprezentująca autora książek.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Autorzy {

  @Setter
  @Getter
  private int id;
  @Setter
  @Getter
  private String imie;
  @Setter
  @Getter
  private String nazwisko;
  @Setter
  @Getter
  private int rokUrodzenia;

  /**
   * Konstruktor klasy.
   *
   * @param id identyfikator autora
   * @param imie imie autora
   * @param nazwisko nazwisko autora
   * @param rokUrodzenia rok urodzenia autora
   */
  public Autorzy(int id, String imie, String nazwisko, int rokUrodzenia) {
    this.id = id;
    this.imie = imie;
    this.nazwisko = nazwisko;
    this.rokUrodzenia = rokUrodzenia;
  }

  @Override
  public String toString() {
    return "Autorzy{id=" + id + ", Imie='" + imie + "', Nazwisko='" + nazwisko + "', Rok_Urodzenia="
        + rokUrodzenia + "}";
  }
}
