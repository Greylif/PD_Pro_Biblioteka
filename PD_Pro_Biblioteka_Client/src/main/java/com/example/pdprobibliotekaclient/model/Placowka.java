package com.example.pdprobibliotekaclient.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa reprezentująca placówkę biblioteczną.
 * Zawiera identyfikator oraz adres placówki.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Placowka {

  @NotNull(message = "Musi posiadac ID")
  private IntegerProperty id = new SimpleIntegerProperty();

  @NotBlank(message = "Adres nie moze byc pusty")
  private StringProperty Adres = new SimpleStringProperty();

  public Placowka(int pid, String adr) {
    this.id.set(pid);
    this.Adres.set(adr);
  }

  public IntegerProperty idProperty() {
    return id;
  }

  public StringProperty adresProperty() {
    return Adres;
  }

  @Override
  public String toString() {
    return "Placowka{id=" + id + ", Adres='" + Adres + "'}";
  }
}
