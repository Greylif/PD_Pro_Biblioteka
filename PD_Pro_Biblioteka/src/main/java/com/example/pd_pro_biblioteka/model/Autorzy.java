package com.example.pd_pro_biblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Autorzy {
    @Setter
    @Getter
    private int id;
    @Setter
    @Getter
    private String Imie;
    @Setter
    @Getter
    private String Nazwisko;
    @Setter
    @Getter
    private int Rok_Urodzenia;

    public Autorzy() {}

    public Autorzy(int id, String Imie, String Nazwisko, int Rok_Urodzenia) {
        this.id = id;
        this.Imie = Imie;
        this.Nazwisko = Nazwisko;
        this.Rok_Urodzenia = Rok_Urodzenia;
    }

    @Override
    public String toString() {
        return "Autorzy{id=" + id + ", Imie='" + Imie + "', Nazwisko='" + Nazwisko + "', Rok_Urodzenia=" + Rok_Urodzenia + "}";
    }
}
