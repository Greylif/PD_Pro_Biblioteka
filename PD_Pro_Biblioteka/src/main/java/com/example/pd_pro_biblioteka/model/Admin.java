package com.example.pd_pro_biblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Admin {
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
    private String Nazwa_Uzytkownika;
    @Setter
    @Getter
    private String Haslo;
    @Setter
    @Getter
    private int id_placowki;

    public Admin() {}

    public Admin(int id, String Imie, String Nazwisko, String Nazwa_Uzytkownika, String Haslo, int id_placowki) {
        this.id = id;
        this.Imie = Imie;
        this.Nazwisko = Nazwisko;
        this.Nazwa_Uzytkownika = Nazwa_Uzytkownika;
        this.Haslo = Haslo;
        this.id_placowki = id_placowki;
    }


    @Override
    public String toString() {
        return "Admin{id=" + id + ", Imie='" + Imie + "', Nazwisko='" + Nazwisko + "', Nazwa_Uzytkownika='" + Nazwa_Uzytkownika + "', Haslo='" + Haslo + "', id_placowki=" + id_placowki + "}";
    }
}
