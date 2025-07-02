package com.example.pdprobibliotekaclient.model;

import jakarta.validation.constraints.NotNull;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wypozyczenia {

  private final StringProperty imie = new SimpleStringProperty();
  private final StringProperty nazwisko = new SimpleStringProperty();
  private final StringProperty tytul = new SimpleStringProperty();
  private final StringProperty autor = new SimpleStringProperty();
  private final StringProperty dataWypozyczenia = new SimpleStringProperty();
  private final StringProperty terminOddania = new SimpleStringProperty();
  @NotNull(message = "Musi posiadac ID")
  private IntegerProperty id = new SimpleIntegerProperty();
  @NotNull(message = "Data wypozyczenia nie moze byc pusta")
  private StringProperty Data_Wypozyczenia = new SimpleStringProperty();
  private StringProperty Data_Oddania = new SimpleStringProperty();
  @NotNull(message = "Termin oddania nie moze byc pusty")
  private StringProperty Termin_Oddania = new SimpleStringProperty();
  @NotNull(message = "Musi byc przypisana ksiazka")
  private IntegerProperty id_ksiazki = new SimpleIntegerProperty();
  @NotNull(message = "Musi byc przypisany uzytkownik")
  private IntegerProperty id_uzytkownika = new SimpleIntegerProperty();
  private StringProperty autorName = new SimpleStringProperty();
  private StringProperty userData = new SimpleStringProperty();
  private StringProperty bookTitle = new SimpleStringProperty();

  public Wypozyczenia(int id, String Wyp, String Odd, String Ter, int id_ksiazki,
      int id_uzytkownika) {
    this.id.set(id);
    this.Data_Wypozyczenia.set(Wyp);
    this.Data_Oddania.set(Odd);
    this.Termin_Oddania.set(Ter);
    this.id_ksiazki.set(id_ksiazki);
    this.id_uzytkownika.set(id_uzytkownika);
  }

  public IntegerProperty idProperty() {
    return id;
  }

  public StringProperty data_WypozyczeniaProperty() {
    return Data_Wypozyczenia;
  }

  public StringProperty termin_OddaniaProperty() {
    return Termin_Oddania;
  }

  public StringProperty data_OddaniaProperty() {
    return Data_Oddania;
  }

  public IntegerProperty id_ksiazkiProperty() {
    return id_ksiazki;
  }

  public IntegerProperty id_uzytkownikaProperty() {
    return id_uzytkownika;
  }

  public StringProperty autorNameProperty() {
    return autorName;
  }

  public void setAutorName(String autorName) {
    this.autorName.set(autorName);
  }

  public StringProperty userDataProperty() {
    return userData;
  }

  public void setUserData(String userData) {
    this.userData.set(userData);
  }

  public StringProperty bookTitleProperty() {
    return bookTitle;
  }

  public void setBookTitle(String tytul) {
    this.bookTitle.set(tytul);
  }


  public String getImie() {
    return imie.get();
  }

  public void setImie(String imie) {
    this.imie.set(imie);
  }

  public String getNazwisko() {
    return nazwisko.get();
  }

  public void setNazwisko(String nazwisko) {
    this.nazwisko.set(nazwisko);
  }

  public String getTytul() {
    return tytul.get();
  }

  public void setTytul(String tytul) {
    this.tytul.set(tytul);
  }

  public String getAutor() {
    return autor.get();
  }

  public void setAutor(String autor) {
    this.autor.set(autor);
  }

  public String getDataWypozyczenia() {
    return dataWypozyczenia.get();
  }

  public void setDataWypozyczenia(String data) {
    this.dataWypozyczenia.set(data);
  }

  public String getTerminOddania() {
    return terminOddania.get();
  }

  public void setTerminOddania(String termin) {
    this.terminOddania.set(termin);
  }


  @Override
  public String toString() {
    return "Wypozyczenia{id=" + id + ", Data_Wypozyczenia=" + Data_Wypozyczenia + ", Data_Oddania="
        + Data_Oddania + ", Termin_Oddania=" + Termin_Oddania + ", id_ksiazki=" + id_ksiazki
        + ", id_uzytkownika=" + id_uzytkownika + "}";
  }


}
