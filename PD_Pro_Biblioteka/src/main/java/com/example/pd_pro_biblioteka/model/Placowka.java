package com.example.pd_pro_biblioteka.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Placowka {
    @Setter
    @Getter
    private int id;
    @Setter
    @Getter
    private String Adres;

    public Placowka() {}

    public Placowka(int id, String Adres) {
        this.id = id;
        this.Adres = Adres;
    }

    @Override
    public String toString() {
        return "Placowka{id=" + id + ", Adres='" + Adres + "'}";
    }
}
