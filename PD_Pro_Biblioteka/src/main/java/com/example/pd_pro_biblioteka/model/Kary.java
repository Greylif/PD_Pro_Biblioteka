package com.example.pd_pro_biblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Kary {
    @Setter
    @Getter
    private int id;
    @Setter
    @Getter
    private double Kwota;
    @Setter
    @Getter
    private String Data_Wydania_Kary;
    @Setter
    @Getter
    private String Termin_Zaplaty;
    @Setter
    @Getter
    private boolean Czy_Zaplacono;
    @Setter
    @Getter
    private int id_uzytkownika;

    public Kary() {}

    public Kary(int id, double Kwota, String Data_Wydania_Kary, String Termin_Zaplaty, boolean Czy_Zaplacono, int id_uzytkownika) {
        this.id = id;
        this.Kwota = Kwota;
        this.Data_Wydania_Kary = Data_Wydania_Kary;
        this.Termin_Zaplaty = Termin_Zaplaty;
        this.Czy_Zaplacono = Czy_Zaplacono;
        this.id_uzytkownika = id_uzytkownika;
    }


    @Override
    public String toString() {
        return "Kary{id=" + id + ", Kwota=" + Kwota + ", Data_Wydania_Kary=" + Data_Wydania_Kary +
                ", Termin_Zaplaty=" + Termin_Zaplaty + ", Czy_Zaplacono=" + Czy_Zaplacono + ", id_uzytkownika=" + id_uzytkownika + "}";
    }
}
