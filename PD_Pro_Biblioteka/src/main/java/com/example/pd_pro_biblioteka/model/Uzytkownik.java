package com.example.pd_pro_biblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Uzytkownik {

    private int id;
    private String imie;
    private String nazwisko;
    private String dataUrodzenia;
    private String haslo;
    private String nazwaUzytkownika;
    private String email;
    private Boolean zablokowany;
    private Boolean mfaEnabled;
    private String mfaSecret;


    @Override
    public String toString() {
        return "Uzytkownik{id=" + id + ", Imie='" + imie + "', Nazwisko='" + nazwisko + "', Data_Urodzenia='" + dataUrodzenia + "', Nazwa_Uzytkownika='" + nazwaUzytkownika + "', Haslo='" + haslo + "', Email=" + email + ", Zablokowany=" + zablokowany + "}";
    }

}
