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
    private String Data_Urodzenia;
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

    public Uzytkownik(int id, String Imie, String Nazwisko, String Data_Urodzenia, String Nazwa_Uzytkownika, String Haslo, String Email) {
        this.id = id;
        this.Imie = Imie;
        this.Nazwisko = Nazwisko;
        this.Data_Urodzenia = Data_Urodzenia;
        this.Nazwa_Uzytkownika = Nazwa_Uzytkownika;
        this.Haslo = Haslo;
        this.Email = Email;
    }


    @Override
    public String toString() {
        return "Uzytkownik{id=" + id + ", Imie='" + Imie + "', Nazwisko='" + Nazwisko + "', Data_Urodzenia='" + Data_Urodzenia + "', Nazwa_Uzytkownika='" + Nazwa_Uzytkownika + "', Haslo='" + Haslo + "', Email=" + Email + "}";
    }

}
