package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kary {
    @NotNull(message = "Musi posiadac ID")
    private int id;
    @NotNull(message = "Kwota nie moze byc pusta")
    private double Kwota;
    @NotNull(message = "Data wydania kary nie moze byc pusta")
    private LocalDateTime Data_Wydania_Kary;
    private LocalDateTime Termin_Zaplaty;
    private boolean Czy_Zaplacono;
    @NotNull(message = "Musi byc przypisany uzytkownik")
    private int id_uzytkownika;

    @Override
    public String toString() {
        return "Kary{id=" + id + ", Kwota=" + Kwota + ", Data_Wydania_Kary=" + Data_Wydania_Kary + ", Termin_Zaplaty=" + Termin_Zaplaty + ", Czy_Zaplacono=" + Czy_Zaplacono + ", id_uzytkownika=" + id_uzytkownika + "}";
    }
}
