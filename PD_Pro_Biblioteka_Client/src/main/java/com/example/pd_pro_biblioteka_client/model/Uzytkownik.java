package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Uzytkownik {
    @NotNull(message = "Musi posiadac ID")
    private IntegerProperty id = new SimpleIntegerProperty();

    @NotBlank(message = "Imie nie moze byc puste")
    private StringProperty Imie = new SimpleStringProperty();

    @NotBlank(message = "Nazwisko nie moze byc puste")
    private StringProperty Nazwisko = new SimpleStringProperty();

    @NotNull(message = "Wiek nie moze byc pusty")
    private StringProperty Data_urodzenia = new SimpleStringProperty();

    @NotBlank(message = "Haslo nie moze byc puste")
    private StringProperty Haslo = new SimpleStringProperty();

    @NotBlank(message = "Nazwa_Uzytkownika nie moze byc pusta")
    private StringProperty Nazwa_Uzytkownika = new SimpleStringProperty();

    @Email(message = "Niepoprawny format e-maila")
    @NotBlank(message = "Email nie moze byc pusty")
    private StringProperty Email = new SimpleStringProperty();


    private BooleanProperty Zablokowany = new SimpleBooleanProperty();
    private BooleanProperty Mfa_Enabled = new SimpleBooleanProperty();
    private StringProperty Mfa_Secret = new SimpleStringProperty();

    public Uzytkownik(int ID, String imie, String nazwisko, String nazwa_uzytkownika, String haslo, String email, String dataUrodzenia, boolean zablokowany, boolean Mfa_Enabled, String Mfa_Secret ) {
        this.id.set(ID);
        this.Imie.set(imie);
        this.Nazwisko.set(nazwisko);
        this.Nazwa_Uzytkownika.set(nazwa_uzytkownika);
        this.Haslo.set(haslo);
        this.Email.set(email);
        this.Data_urodzenia.set(dataUrodzenia);
        this.Zablokowany.set(zablokowany);
        this.Mfa_Enabled.set(Mfa_Enabled);
        this.Mfa_Secret.set(Mfa_Secret);
    }

    public BooleanProperty ZablokowanyProperty() {return Zablokowany;}
    public IntegerProperty idProperty() {return id;}
    public StringProperty imieProperty() {return Imie;}
    public StringProperty nazwiskoProperty() {return Nazwisko;}
    public StringProperty wiekProperty() {return Data_urodzenia;}
    public StringProperty hasloProperty() {return Haslo;}
    public StringProperty nazwaProperty() {return Nazwa_Uzytkownika;}
    public StringProperty emailProperty() {return Email;}

    public void setImie(String imie) {
        this.Imie.set(imie);
    }
    public void setNazwisko(String nazwisko) {
        this.Nazwisko.set(nazwisko);
    }
    public void setDataUrodzenia(String dataUrodzenia) {
        this.Data_urodzenia.set(dataUrodzenia);
    }
    public void setNazwaUzytkownika(String nazwaUzytkownika) {
        this.Nazwa_Uzytkownika.set(nazwaUzytkownika);
    }
    public void setHaslo(String haslo) {
        this.Haslo.set(haslo);
    }
    public void setEmail(String email) {
        this.Email.set(email);
    }
    public void setZablokowany(boolean zablokowany) {
        this.Zablokowany.set(zablokowany);
    }


    public String getImie() {
        return Imie.get();
    }
    public String getNazwisko() {
        return Nazwisko.get();
    }
    public String getDataUrodzenia() {
        return Data_urodzenia.get();
    }
    public int getId() {
        return id.get();
    }
    public String getNazwaUzytkownika() {
        return Nazwa_Uzytkownika.get();
    }
    public String getHaslo() {
        return Haslo.get();
    }
    public String getEmail() {
        return Email.get();
    }
    public boolean isZablokowany() {
        return Zablokowany.get();
    }
    public boolean isMfaEnabled() {
        return Mfa_Enabled.get();
    }
    public String getMfaSecret() {
        return Mfa_Secret.get();
    }

    @Override
    public String toString() {
        return "Uzytkownik{id=" + id + ", Imie='" + Imie + "', Nazwisko='" + Nazwisko + "', Wiek=" + Data_urodzenia + ", Nazwa_Uzytkownika='" + Nazwa_Uzytkownika + "', Haslo='" + Haslo + "', Email='" + Email + "'}";
    }

}
