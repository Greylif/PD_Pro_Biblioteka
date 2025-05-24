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
    private String imie;
    @Setter
    @Getter
    private String nazwisko;
    @Setter
    @Getter
    private String nazwaUzytkownika;
    @Setter
    @Getter
    private String haslo;
    @Setter
    @Getter
    private Integer idplacowki;
    @Setter
    @Getter
    private Boolean mfaEnabled;
    @Setter
    @Getter
    private String mfaSecret;

    public Admin() {}

    public Admin(Integer id, String imie, String nazwisko, String nazwaUzytkownika, String haslo, Integer idplacowki, Boolean mfaEnabled, String mfaSecret) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nazwaUzytkownika = nazwaUzytkownika;
        this.haslo = haslo;
        this.idplacowki = idplacowki;
        this.mfaEnabled = mfaEnabled;
        this.mfaSecret = mfaSecret;
    }


    @Override
    public String toString() {
        return "Admin{id=" + id + ", Imie='" + imie + "', Nazwisko='" + nazwisko + "', Nazwa_Uzytkownika='" + nazwaUzytkownika + "', Haslo='" + haslo + "', id_placowki=" + idplacowki + "', mfa_enabled=" + mfaEnabled + ", mfa_secret=" + mfaSecret + "}";
    }
}
