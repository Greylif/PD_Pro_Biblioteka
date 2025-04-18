package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javafx.beans.property.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @NotNull(message = "Musi posiadac ID")
    private IntegerProperty id = new SimpleIntegerProperty();

    @NotBlank(message = "Imie nie moze byc puste")
    private StringProperty Imie = new SimpleStringProperty();

    @NotBlank(message = "Nazwisko nie moze byc puste")
    private StringProperty Nazwisko = new SimpleStringProperty();

    @NotBlank(message = "Nazwa_Uzytkownika nie moze byc pusta")
    private StringProperty Nazwa_Uzytkownika =  new SimpleStringProperty();

    @NotBlank(message = "Haslo nie moze byc puste")
    private StringProperty Haslo = new SimpleStringProperty();

    @NotNull(message = "Musi miec przypisana placowke")
    private IntegerProperty id_placowki = new SimpleIntegerProperty();


    public Admin(int ID, String imie, String nazwisko, String nazwaUzytkownika, String haslo, int idPlacowki) {
        this.id.set(ID);
        this.Imie.set(imie);
        this.Nazwisko.set(nazwisko);
        this.Nazwa_Uzytkownika.set(nazwaUzytkownika);
        this.Haslo.set(haslo);
        this.id_placowki.set(idPlacowki);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty imieProperty() { return Imie; }
    public StringProperty nazwiskoProperty() { return Nazwisko; }
    public StringProperty nazwaProperty() { return Nazwa_Uzytkownika; }
    public StringProperty hasloProperty() { return Haslo; }
    public IntegerProperty id_placowkiProperty() { return id_placowki; }

    @Override
    public String toString() {
        return "Admin{id=" + id + ", Imie='" + Imie + "', Nazwisko='" + Nazwisko + "', Nazwa_Uzytkownika='" + Nazwa_Uzytkownika + "', id_placowki=" + id_placowki + "}";
    }
}
