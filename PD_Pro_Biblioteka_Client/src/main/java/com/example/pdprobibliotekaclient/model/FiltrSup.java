package com.example.pdprobibliotekaclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Klasa pomocnicza przechowująca podstawowe informacje o filtrze książki.
 * Zawiera informacje o rezerwacji, wypożyczeniu, gatunku oraz id autora.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FiltrSup {

    private boolean rez;
    private boolean wypo;
    private String gat;
    private int idA;

}