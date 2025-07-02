package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ksiazka {

  @NotNull(message = "Musi posiadać ID")
  private IntegerProperty id = new SimpleIntegerProperty();

  @NotBlank(message = "Tytuł nie może być pusty")
  private StringProperty tytul = new SimpleStringProperty();

  @NotBlank(message = "Gatunek nie może być pusty")
  private StringProperty gatunek = new SimpleStringProperty();

  @NotNull(message = "Data wydania nie może być pusta")
  private IntegerProperty dataWydania = new SimpleIntegerProperty();

  @NotNull(message = "Data dodania nie może być pusta")
  private StringProperty dodano = new SimpleStringProperty();

  @NotNull(message = "Książka musi mieć przypisanego autora")
  private IntegerProperty idAutora = new SimpleIntegerProperty();

  @NotNull(message = "Książka musi być przypisana do placówki")
  private IntegerProperty idPlacowki = new SimpleIntegerProperty();

  private BooleanProperty Rezerwacja = new SimpleBooleanProperty();

  private BooleanProperty Wypozyczenie = new SimpleBooleanProperty();

  private StringProperty autorName = new SimpleStringProperty();

  public Ksiazka(int id, String tytul, String gatunek, int dataWydania, String dodano, int idAutora,
      int idPlacowki, Boolean rezerwacja, Boolean wypozyczenia) {
    this.id.set(id);
    this.tytul.set(tytul);
    this.gatunek.set(gatunek);
    this.dataWydania.set(dataWydania);
    this.dodano.set(dodano);
    this.idAutora.set(idAutora);
    this.idPlacowki.set(idPlacowki);
    this.Rezerwacja.set(rezerwacja);
    this.Wypozyczenie.set(wypozyczenia);
  }

  public StringProperty autorNameProperty() {
    return autorName;
  }

  public void setAutorName(String autorName) {
    this.autorName.set(autorName);
  }

  public IntegerProperty idProperty() {
    return id;
  }

  public StringProperty tytulProperty() {
    return tytul;
  }

  public StringProperty gatunekProperty() {
    return gatunek;
  }

  public IntegerProperty dataWydaniaProperty() {
    return dataWydania;
  }

  public StringProperty dodanoProperty() {
    return dodano;
  }

  public IntegerProperty idAutoraProperty() {
    return idAutora;
  }

  public IntegerProperty idPlacowkiProperty() {
    return idPlacowki;
  }

  public BooleanProperty RezerwacjaProperty() {
    return Rezerwacja;
  }

  public BooleanProperty WypozyczenieProperty() {
    return Wypozyczenie;
  }

}
