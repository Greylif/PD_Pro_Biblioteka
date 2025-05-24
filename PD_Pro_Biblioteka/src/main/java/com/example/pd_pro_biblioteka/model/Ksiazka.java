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
    private String tytul;
    @Setter
    @Getter
    private String gatunek;
    @Setter
    @Getter
    private String dataWydania;
    @Setter
    @Getter
    private String dodano;
    @Setter
    @Getter
    private Integer idautora;
    @Setter
    @Getter
    private Integer idplacowki;
    @Setter
    @Getter
    private Boolean rezerwacja;
    @Setter
    @Getter
    private Boolean czywyporzyczono;

    public Ksiazka() {}

    public Ksiazka(int id, String tytul, String gatunek, String dataWydania, String dodano, Integer idautora, Integer idplacowki, Boolean rezerwacja, Boolean czywyporzyczono) {
        this.id = id;
        this.tytul = tytul;
        this.gatunek = gatunek;
        this.dataWydania = dataWydania;
        this.dodano = dodano;
        this.idautora = idautora;
        this.idplacowki = idplacowki;
        this.rezerwacja = rezerwacja;
        this.czywyporzyczono = czywyporzyczono;
    }


    @Override
    public String toString() {
        return "Ksiazka{id=" + id + ", Tytul='" + tytul + '\'' + ", Gatunek='" + gatunek + '\'' + ", Data_Wydania=" + dataWydania + ", Dodano=" + dodano + ", id_autora=" + idautora + ", rezerwacja=" + rezerwacja + ", id_placowki=" + idplacowki + ", czywyporzyczono=" + czywyporzyczono + "}";
    }
}
