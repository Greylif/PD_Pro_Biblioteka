package com.example.pd_pro_biblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {
    private int id;
    private String imie;
    private String nazwisko;
    private String nazwaUzytkownika;
    private String haslo;
    private Integer idplacowki;
    private Boolean mfaEnabled;
    private String mfaSecret;

    @Override
    public String toString() {
        return "Admin{id=" + id + ", Imie='" + imie + "', Nazwisko='" + nazwisko + "', Nazwa_Uzytkownika='" + nazwaUzytkownika + "', Haslo='" + haslo + "', id_placowki=" + idplacowki + "', mfa_enabled=" + mfaEnabled + ", mfa_secret=" + mfaSecret + "}";
    }
}
