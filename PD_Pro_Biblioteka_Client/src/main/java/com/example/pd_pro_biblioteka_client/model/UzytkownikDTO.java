package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UzytkownikDTO {

    public int id;
    public String Imie;
    public String Nazwisko;
    public String Nazwa_Uzytkownika;
    public String Haslo;
    public String Email;
    public String Data_Urodzenia;
    public boolean Zablokowany;
    public boolean Mfa_Enabled;
    public String Mfa_Secret;


    @Override
    public String toString() {
        return "Uzytkownik{id=" + id + ", Imie='" + Imie + "', Nazwisko='" + Nazwisko + "', Wiek=" + Data_Urodzenia + ", Nazwa_Uzytkownika='" + Nazwa_Uzytkownika + "', Haslo='" + Haslo + "', Email='" + Email + "'}";
    }
}
