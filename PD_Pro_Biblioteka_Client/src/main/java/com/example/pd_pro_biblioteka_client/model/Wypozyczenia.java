package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wypozyczenia {
    @NotNull(message = "Musi posiadac ID")
    private int id;
    @NotNull(message = "Data wypozyczenia nie moze byc pusta")
    private LocalDateTime Data_Wypozyczenia;
    private LocalDateTime Data_Oddania;
    @NotNull(message = "Termin oddania nie moze byc pusty")
    private LocalDateTime Termin_Oddania;
    @NotNull(message = "Musi byc przypisana ksiazka")
    private int id_ksiazki;
    @NotNull(message = "Musi byc przypisany uzytkownik")
    private int id_uzytkownika;

    @Override
    public String toString() {
        return "Wypozyczenia{id=" + id + ", Data_Wypozyczenia=" + Data_Wypozyczenia + ", Data_Oddania=" + Data_Oddania + ", Termin_Oddania=" + Termin_Oddania + ", id_ksiazki=" + id_ksiazki + ", id_uzytkownika=" + id_uzytkownika + "}";
    }
}
