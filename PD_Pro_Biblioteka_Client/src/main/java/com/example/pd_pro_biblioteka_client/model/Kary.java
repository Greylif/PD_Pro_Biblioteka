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
public class Kary {
    @NotNull(message = "Musi posiadac ID")
    private IntegerProperty id = new SimpleIntegerProperty();

    @NotNull(message = "Kwota nie moze byc pusta")
    private DoubleProperty Kwota = new SimpleDoubleProperty();

    @NotNull(message = "Data wydania kary nie moze byc pusta")
    private ObjectProperty<LocalDateTime> Data_Wydania_Kary = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> Termin_Zaplaty = new SimpleObjectProperty<>();
    private BooleanProperty Czy_Zaplacono = new SimpleBooleanProperty();

    @NotNull(message = "Musi byc przypisany uzytkownik")
    private IntegerProperty id_uzytkownika = new SimpleIntegerProperty();

    public Kary(int ID, double kwota, LocalDateTime dataWydaniaKary, LocalDateTime terminZaplaty, Boolean czyZaplacono) {
        this.id.set(ID);
        this.Kwota.set(kwota);
        this.Data_Wydania_Kary.set(dataWydaniaKary);
        this.Termin_Zaplaty.set(terminZaplaty);
        this.Czy_Zaplacono.set(czyZaplacono);
    }

    public IntegerProperty idProperty() {return id;}
    public DoubleProperty KwotaProperty() {return Kwota;}
    public ObjectProperty<LocalDateTime> Data_Wydania_Kary_Property() {return Data_Wydania_Kary;}
    public ObjectProperty<LocalDateTime> Termin_Zaplaty_Property() {return Termin_Zaplaty;}
    public BooleanProperty CzyZaplaconoProperty() {return Czy_Zaplacono;}


    @Override
    public String toString() {
        return "Kary{id=" + id + ", Kwota=" + Kwota + ", Data_Wydania_Kary=" + Data_Wydania_Kary + ", Termin_Zaplaty=" + Termin_Zaplaty + ", Czy_Zaplacono=" + Czy_Zaplacono + ", id_uzytkownika=" + id_uzytkownika + "}";
    }
}
