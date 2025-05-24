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
    private String imie;
    @Setter
    @Getter
    private String nazwisko;
    @Setter
    @Getter
    private String dataUrodzenia;
    @Setter
    @Getter
    private String haslo;
    @Setter
    @Getter
    private String nazwaUzytkownika;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private Boolean zablokowany;
    @Setter
    @Getter
    private Boolean mfaEnabled;
    @Setter
    @Getter
    private String mfaSecret;

    public Uzytkownik() {}

    public Uzytkownik(int id, String imie, String nazwisko, String dataUrodzenia, String nazwaUzytkownika, String haslo, String email, Boolean zablokowany, Boolean mfaEnabled, String mfaSecret) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.dataUrodzenia = dataUrodzenia;
        this.nazwaUzytkownika = nazwaUzytkownika;
        this.haslo = haslo;
        this.email = email;
        this.zablokowany = zablokowany;
        this.mfaEnabled = mfaEnabled;
        this.mfaSecret = mfaSecret;
    }


    @Override
    public String toString() {
        return "Uzytkownik{id=" + id + ", Imie='" + imie + "', Nazwisko='" + nazwisko + "', Data_Urodzenia='" + dataUrodzenia + "', Nazwa_Uzytkownika='" + nazwaUzytkownika + "', Haslo='" + haslo + "', Email=" + email + ", Zablokowany=" + zablokowany + "}";
    }

}
