package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Uzytkownik {
    @NotNull(message = "Musi posiadac ID")
    private IntegerProperty id = new SimpleIntegerProperty();

    @NotBlank(message = "Imie nie moze byc puste")
    private StringProperty Imie = new SimpleStringProperty();

    @NotBlank(message = "Nazwisko nie moze byc puste")
    private StringProperty Nazwisko = new SimpleStringProperty();

    @NotNull(message = "Wiek nie moze byc pusty")
    private ObjectProperty<LocalDateTime> Data_urodzenia = new SimpleObjectProperty<>();

    @NotBlank(message = "Haslo nie moze byc puste")
    private StringProperty Haslo = new SimpleStringProperty();

    @NotBlank(message = "Nazwa_Uzytkownika nie moze byc pusta")
    private StringProperty Nazwa_Uzytkownika = new SimpleStringProperty();

    @Email(message = "Niepoprawny format e-maila")
    @NotBlank(message = "Email nie moze byc pusty")
    private StringProperty Email = new SimpleStringProperty();

    public Uzytkownik(int ID, String imie, String nazwisko, LocalDateTime dataUrodzenia, String haslo, String nazwa_uzytkownika, String email ) {
        this.id.set(ID);
        this.Imie.set(imie);
        this.Nazwisko.set(nazwisko);
        this.Data_urodzenia.set(dataUrodzenia);
        this.Haslo.set(haslo);
        this.Nazwa_Uzytkownika.set(nazwa_uzytkownika);
        this.Email.set(email);
    }

    public IntegerProperty idProperty() {return id;}
    public StringProperty imieProperty() {return Imie;}
    public StringProperty nazwiskoProperty() {return Nazwisko;}
    public ObjectProperty<LocalDateTime> wiekProperty() {return Data_urodzenia;}
    public StringProperty hasloProperty() {return Haslo;}
    public StringProperty nazwaProperty() {return Nazwa_Uzytkownika;}
    public StringProperty emailProperty() {return Email;}

    @Override
    public String toString() {
        return "Uzytkownik{id=" + id + ", Imie='" + Imie + "', Nazwisko='" + Nazwisko + "', Wiek=" + Data_urodzenia + ", Nazwa_Uzytkownika='" + Nazwa_Uzytkownika + "', Haslo='" + Haslo + "', Email='" + Email + "'}";
    }
}
