package com.example.pdprobibliotekaclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Klasa pomocnicza przechowująca podstawowe dane administratora.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminSup {

    private int id;
    private String imie;
    private String nazwisko;

}