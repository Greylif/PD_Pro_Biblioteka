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
public class Autorzy {
    @NotNull(message = "Musi posiadac ID")
    private IntegerProperty id = new SimpleIntegerProperty();

    @NotBlank(message = "Imie nie moze byc puste")
    private StringProperty Imie = new SimpleStringProperty();

    @NotBlank(message = "Nazwisko nie moze byc puste")
    private StringProperty Nazwisko = new SimpleStringProperty();

    @NotNull(message = "Rok urodzenia nie moze byc pusty")
    private IntegerProperty Rok_Urodzenia = new SimpleIntegerProperty();

    public Autorzy(int id, String imie, String nazwisko, int rok_Urodzenia) {
        this.id.set(id);
        this.Imie.set(imie);
        this.Nazwisko.set(nazwisko);
        this.Rok_Urodzenia.set(rok_Urodzenia);
    }

    public IntegerProperty idProperty() {return id;}
    public StringProperty imieProperty() {return Imie;}
    public StringProperty nazwiskoProperty() {return Nazwisko;}
    public IntegerProperty rokUrProperty() {return Rok_Urodzenia;}

    @Override
    public String toString() {
        return "Autorzy{id=" + id + ", Imie='" + Imie + "', Nazwisko='" + Nazwisko + "', Rok_Urodzenia=" + Rok_Urodzenia + "}";
    }
}
