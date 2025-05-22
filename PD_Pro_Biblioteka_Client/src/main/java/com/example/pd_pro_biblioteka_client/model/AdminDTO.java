package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AdminDTO {
    public int id;
    public String Imie;
    public String Nazwisko;
    public String Nazwa_Uzytkownika;
    public String Haslo;
    public int id_placowki;
    public boolean Mfa_Enabled;
    public String Mfa_Secret;
}
