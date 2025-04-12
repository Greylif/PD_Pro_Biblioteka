package com.example.pd_pro_biblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ksiazka {
    @Setter
    @Getter
    private int id;
    @Setter
    @Getter
    private String Tytul;
    @Setter
    @Getter
    private String Gatunek;
    @Setter
    @Getter
    private String Data_Wydania;
    @Setter
    @Getter
    private String Dodano;
    @Setter
    @Getter
    private int id_autora;
    @Setter
    @Getter
    private int id_placowki;

    public Ksiazka() {}

    public Ksiazka(int id, String Tytul, String Gatunek, String Data_Wydania, String Dodano, int id_autora, int id_placowki) {
        this.id = id;
        this.Tytul = Tytul;
        this.Gatunek = Gatunek;
        this.Data_Wydania = Data_Wydania;
        this.Dodano = Dodano;
        this.id_autora = id_autora;
        this.id_placowki = id_placowki;
    }


    @Override
    public String toString() {
        return "Ksiazka{id=" + id + ", Tytul='" + Tytul + '\'' + ", Gatunek='" + Gatunek + '\'' + ", Data_Wydania=" + Data_Wydania + ", Dodano=" + Dodano + ", id_autora=" + id_autora + ", id_placowki=" + id_placowki + '}';
    }
}
