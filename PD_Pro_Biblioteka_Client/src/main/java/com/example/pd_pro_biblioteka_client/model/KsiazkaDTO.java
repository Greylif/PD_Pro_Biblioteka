package com.example.pd_pro_biblioteka_client.model;

public class KsiazkaDTO {
    public int id;
    public String Tytul;
    public String Gatunek;
    public String Dodano;
    public int id_autora;
    public int id_placowki;
    public boolean Rezerwacja;
    public boolean czy_wypozyczono;
    public int Data_Wydania;

    public KsiazkaDTO(int i, String tytulTestowy, String thriller, String number, String number1, int i1, int i2, boolean b, boolean b1) {
    }
}
