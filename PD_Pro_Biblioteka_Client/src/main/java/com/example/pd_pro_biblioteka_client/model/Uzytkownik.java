package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Uzytkownik {
    @NotNull(message = "Musi posiadac ID")
    private int id;
    @NotBlank(message = "Imie nie moze byc puste")
    private String Imie;
    @NotBlank(message = "Nazwisko nie moze byc puste")
    private String Nazwisko;
    @NotNull(message = "Wiek nie moze byc pusty")
    private int Wiek;
    @NotBlank(message = "Haslo nie moze byc puste")
    private String Haslo;
    @NotBlank(message = "Nazwa_Uzytkownika nie moze byc pusta")
    private String Nazwa_Uzytkownika;
    @Email(message = "Niepoprawny format e-maila")
    @NotBlank(message = "Email nie moze byc pusty")
    private String Email;

    @Override
    public String toString() {
        return "Uzytkownik{id=" + id + ", Imie='" + Imie + "', Nazwisko='" + Nazwisko + "', Wiek=" + Wiek + ", Nazwa_Uzytkownika='" + Nazwa_Uzytkownika + "', Haslo='" + Haslo + "', Email='" + Email + "'}";
    }
}
