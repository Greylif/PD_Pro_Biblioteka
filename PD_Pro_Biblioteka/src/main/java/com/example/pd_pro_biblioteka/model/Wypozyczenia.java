package com.example.pd_pro_biblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Wypozyczenia {
    @Setter
    @Getter
    private int id;
    @Setter
    @Getter
    private LocalDateTime Data_Wypozyczenia;
    @Setter
    @Getter
    private LocalDateTime Data_Oddania;
    @Setter
    @Getter
    private LocalDateTime Termin_Oddania;
    @Setter
    @Getter
    private int id_ksiazki;
    @Setter
    @Getter
    private int id_uzytkownika;

    public Wypozyczenia() {}

    public Wypozyczenia(int id, LocalDateTime Data_Wypozyczenia, LocalDateTime Data_Oddania, LocalDateTime Termin_Oddania, int id_ksiazki, int id_uzytkownika) {
        this.id = id;
        this.Data_Wypozyczenia = Data_Wypozyczenia;
        this.Data_Oddania = Data_Oddania;
        this.Termin_Oddania = Termin_Oddania;
        this.id_ksiazki = id_ksiazki;
        this.id_uzytkownika = id_uzytkownika;
    }


    @Override
    public String toString() {
        return "Wypozyczenia{id=" + id + ", Data_Wypozyczenia=" + Data_Wypozyczenia + ", Data_Oddania=" + Data_Oddania + ", Termin_Oddania=" + Termin_Oddania + ", id_ksiazki=" + id_ksiazki + ", id_uzytkownika=" + id_uzytkownika + "}";
    }
}
