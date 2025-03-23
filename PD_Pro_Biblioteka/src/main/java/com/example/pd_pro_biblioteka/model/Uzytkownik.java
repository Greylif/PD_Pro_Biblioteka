package com.example.pd_pro_biblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Uzytkownik {
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
    private int Wiek;

    public Uzytkownik() {}

    public Uzytkownik(int id, String Imie, String Nazwisko, int Wiek) {
        this.id = id;
        this.Imie = Imie;
        this.Nazwisko = Nazwisko;
        this.Wiek = Wiek;
    }


    @Override
    public String toString() {
        return "Uzytkownik{id=" + id + ", Imie='" + Imie + "', Nazwisko='" + Nazwisko + "', Wiek=" + Wiek + "}";
    }
}
