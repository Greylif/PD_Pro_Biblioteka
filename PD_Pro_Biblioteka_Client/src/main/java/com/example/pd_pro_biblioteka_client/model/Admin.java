package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @NotNull(message = "Musi posiadac ID")
    private int id;
    @NotBlank(message = "Imie nie moze byc puste")
    private String Imie;
    @NotBlank(message = "Nazwisko nie moze byc puste")
    private String Nazwisko;
    @NotBlank(message = "Nazwa_Uzytkownika nie moze byc pusta")
    private String Nazwa_Uzytkownika;
    @NotBlank(message = "Haslo nie moze byc puste")
    private String Haslo;
    @NotNull(message = "Musi miec przypisana placowke")
    private int id_placowki;

    @Override
    public String toString() {
        return "Admin{id=" + id + ", Imie='" + Imie + "', Nazwisko='" + Nazwisko + "', Nazwa_Uzytkownika='" + Nazwa_Uzytkownika + "', id_placowki=" + id_placowki + "}";
    }
}
