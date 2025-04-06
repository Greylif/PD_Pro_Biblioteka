package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Autorzy {
    @NotNull(message = "Musi posiadac ID")
    private int id;
    @NotBlank(message = "Imie nie moze byc puste")
    private String Imie;
    @NotBlank(message = "Nazwisko nie moze byc puste")
    private String Nazwisko;
    @NotNull(message = "Rok urodzenia nie moze byc pusty")
    private int Rok_Urodzenia;

    @Override
    public String toString() {
        return "Autorzy{id=" + id + ", Imie='" + Imie + "', Nazwisko='" + Nazwisko + "', Rok_Urodzenia=" + Rok_Urodzenia + "}";
    }
}
