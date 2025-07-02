package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import javafx.beans.property.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kary {
    @NotNull(message = "Musi posiadac ID")
    private IntegerProperty id = new SimpleIntegerProperty();

    @NotNull(message = "Kwota nie moze byc pusta")
    private DoubleProperty Kwota = new SimpleDoubleProperty();

    @NotNull(message = "Data wydania kary nie moze byc pusta")
    private StringProperty Data_Wydania_Kary = new SimpleStringProperty();
    private StringProperty Termin_Zaplaty = new SimpleStringProperty();
    private BooleanProperty Czy_Zaplacono = new SimpleBooleanProperty();

    @NotNull(message = "Musi byc przypisany uzytkownik")
    private IntegerProperty id_uzytkownika = new SimpleIntegerProperty();

    private StringProperty Opis = new SimpleStringProperty();


    private StringProperty autorName = new SimpleStringProperty();
    private StringProperty bookTitle = new SimpleStringProperty();


    public Kary(int ID, double kwota, String dataWydaniaKary, String terminZaplaty, Boolean czyZaplacono, int id_uzytkownika, String opis) {
        this.id.set(ID);
        this.Kwota.set(kwota);
        this.Data_Wydania_Kary.set(dataWydaniaKary);
        this.Termin_Zaplaty.set(terminZaplaty);
        this.Czy_Zaplacono.set(czyZaplacono);
        this.id_uzytkownika.set(id_uzytkownika);
        this.Opis.set(opis);
    }

    public IntegerProperty idProperty() {return id;}
    public DoubleProperty KwotaProperty() {return Kwota;}
    public StringProperty Data_Wydania_Kary_Property() {return Data_Wydania_Kary;}
    public StringProperty Termin_Zaplaty_Property() {return Termin_Zaplaty;}
    public BooleanProperty CzyZaplaconoProperty() {return Czy_Zaplacono;}
    public IntegerProperty id_uzytkownikaProperty() {return id_uzytkownika;}
    public StringProperty OpisProperty() {return Opis;}


    @Override
    public String toString() {
        return "Kary{id=" + id + ", Kwota=" + Kwota + ", Data_Wydania_Kary=" + Data_Wydania_Kary + ", Termin_Zaplaty=" + Termin_Zaplaty + ", Czy_Zaplacono=" + Czy_Zaplacono + ", id_uzytkownika=" + id_uzytkownika + "}";
    }

    public void setCzyZaplacono(boolean zaplata) {
        Czy_Zaplacono.set(zaplata);
    }

    public int getId() {
        return id.get();
    }

    public StringProperty autorNameProperty() {return autorName;}
    public String getAutorName() {return autorName.get();}
    public void setAutorName(String autorName) {this.autorName.set(autorName);}

    public StringProperty bookTitleProperty() { return bookTitle; }
    public String getBookTitle() { return bookTitle.get(); }
    public void setBookTitle(String bookTitle) { this.bookTitle.set(bookTitle); }


}
