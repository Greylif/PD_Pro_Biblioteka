package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotNull;
import javafx.beans.property.*;

import java.time.LocalDateTime;

public class KaryDTO {
    public int id;
    public double Kwota;
    public String Data_Wydania_Kary;
    public String Termin_Zaplaty;
    public String Czy_Zaplacono;
    public int id_uzytkownika;
    public String opis;


}
