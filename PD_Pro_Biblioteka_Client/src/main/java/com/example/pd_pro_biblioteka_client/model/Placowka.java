package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javafx.beans.property.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Placowka {
    @NotNull(message = "Musi posiadac ID")
    private IntegerProperty id = new SimpleIntegerProperty();

    @NotBlank(message = "Adres nie moze byc pusty")
    private StringProperty Adres = new SimpleStringProperty();

    public Placowka(int pID, String adr) {
        this.id.set(pID);
        this.Adres.set(adr);
    }

    public IntegerProperty idProperty() {return id;}
    public StringProperty adresProperty() {return Adres;}

    @Override
    public String toString() {
        return "Placowka{id=" + id + ", Adres='" + Adres + "'}";
    }
}
