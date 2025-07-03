package com.example.pdprobibliotekaclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Klasa pomocnicza reprezentująca podstawowe dane książki.
 * Służy do uproszczenia przekazywania informacji o tytule, gatunku i roku wydania książki.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KsiazkaSup {

    private String tytul;
    private String gatunek;
    private int dataWydania;

}