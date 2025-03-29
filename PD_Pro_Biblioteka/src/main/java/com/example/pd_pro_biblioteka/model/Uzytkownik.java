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
    @Setter
    @Getter
    private String Haslo;
    @Setter
    @Getter
    private String Nazwa_Uzytkownika;
    @Setter
    @Getter
    private String Email;

    public Uzytkownik() {}

    public Uzytkownik(int id, String Imie, String Nazwisko, int Wiek, String Nazwa_Uzytkownika, String Haslo, String Email) {
        this.id = id;
        this.Imie = Imie;
        this.Nazwisko = Nazwisko;
        this.Wiek = Wiek;
        this.Nazwa_Uzytkownika = Nazwa_Uzytkownika;
        this.Haslo = Haslo;
        this.Email = Email;
    }


    @Override
    public String toString() {
        return "Uzytkownik{id=" + id + ", Imie='" + Imie + "', Nazwisko='" + Nazwisko + "', Wiek='" + Wiek + "', Nazwa_Uzytkownika='" + Nazwa_Uzytkownika + "', Haslo='" + Haslo + "', Email='" + Email + "}";
    }

}
