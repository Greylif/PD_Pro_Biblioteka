package com.example.pdprobiblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Klasa reprezentująca placówkę biblioteki.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Placowka {

  @Setter
  @Getter
  private int id;
  @Setter
  @Getter
  private String adres;

  /**
   * Konstruktor Klasy.
   *
   * @param id identyfikator placówki
   * @param adres adres placówki
   */
  public Placowka(int id, String adres) {
    this.id = id;
    this.adres = adres;
  }

  @Override
  public String toString() {
    return "Placowka{id=" + id + ", Adres='" + adres + "'}";
  }
}
