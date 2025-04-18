package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javafx.beans.property.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wypozyczenia {
    @NotNull(message = "Musi posiadac ID")
    private IntegerProperty id = new SimpleIntegerProperty();

    @NotNull(message = "Data wypozyczenia nie moze byc pusta")
    private ObjectProperty<LocalDateTime> Data_Wypozyczenia = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> Data_Oddania = new SimpleObjectProperty<>();

    @NotNull(message = "Termin oddania nie moze byc pusty")
    private ObjectProperty<LocalDateTime> Termin_Oddania = new SimpleObjectProperty<>();

    @NotNull(message = "Musi byc przypisana ksiazka")
    private IntegerProperty id_ksiazki = new SimpleIntegerProperty();

    @NotNull(message = "Musi byc przypisany uzytkownik")
    private IntegerProperty id_uzytkownika = new SimpleIntegerProperty();

    public Wypozyczenia(int ID, LocalDateTime Data, LocalDateTime Termin, LocalDateTime oddania, int id_ksiazki, int id_uzytkownika) {
        this.id.set(ID);
        this.Data_Wypozyczenia.set(Data);
        this.Data_Oddania.set(oddania);
        this.Termin_Oddania.set(Termin);
        this.id_ksiazki.set(id_ksiazki);
        this.id_uzytkownika.set(id_uzytkownika);
    }

    public IntegerProperty idProperty() {return id;}
    public ObjectProperty<LocalDateTime> data_WypozyczeniaProperty() {return Data_Wypozyczenia;}
    public ObjectProperty<LocalDateTime> data_OddaniaProperty() {return Data_Oddania;}
    public ObjectProperty<LocalDateTime> termin_OddaniaProperty() {return Termin_Oddania;}
    public IntegerProperty id_ksiazkiProperty() {return id_ksiazki;}
    public IntegerProperty id_uzytkownikaProperty() {return id_uzytkownika;}


    @Override
    public String toString() {
        return "Wypozyczenia{id=" + id + ", Data_Wypozyczenia=" + Data_Wypozyczenia + ", Data_Oddania=" + Data_Oddania + ", Termin_Oddania=" + Termin_Oddania + ", id_ksiazki=" + id_ksiazki + ", id_uzytkownika=" + id_uzytkownika + "}";
    }
}
