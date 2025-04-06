package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ksiazka {
    @NotNull(message = "Musi posiadac ID")
    private int id;
    @NotBlank(message = "Tytul nie moze byc pusty")
    private String Tytul;
    @NotBlank(message = "Gatunek nie moze byc pusty")
    private String Gatunek;
    @NotNull(message = "Data wydania nie moze byc pusta")
    private LocalDate Data_Wydania;
    @NotNull(message = "Data dodania nie moze byc pusta")
    private LocalDateTime Dodano;
    @NotNull(message = "Ksiazka musi miec przypisanego autora")
    private int id_autora;
    @NotNull(message = "Ksiazka musi byc przypisana do placowki")
    private int id_placowki;

    @Override
    public String toString() {
        return "Ksiazka{id=" + id + ", Tytul='" + Tytul + "', Gatunek='" + Gatunek + "', Data_Wydania=" + Data_Wydania + ", Dodano=" + Dodano + ", id_autora=" + id_autora + ", id_placowki=" + id_placowki + '}';
    }
}
