package com.example.pd_pro_biblioteka_client.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class WypozyczeniaDTO {

    public int id;
    public String Data_Wypozyczenia;
    public String Data_Oddania;
    public String Termin_Oddania;
    public int id_ksiazki;
    public int id_uzytkownika;

}
