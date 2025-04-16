package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ksiazka_2 {
    @NotNull(message = "Musi posiadać ID")
    private IntegerProperty id = new SimpleIntegerProperty();

    @NotBlank(message = "Tytuł nie może być pusty")
    private StringProperty tytul = new SimpleStringProperty();

    @NotBlank(message = "Gatunek nie może być pusty")
    private StringProperty gatunek = new SimpleStringProperty();

    @NotNull(message = "Data wydania nie może być pusta")
    private ObjectProperty<LocalDate> dataWydania = new SimpleObjectProperty<>();

    @NotNull(message = "Data dodania nie może być pusta")
    private ObjectProperty<LocalDateTime> dodano = new SimpleObjectProperty<>();

    @NotNull(message = "Książka musi mieć przypisanego autora")
    private IntegerProperty idAutora = new SimpleIntegerProperty();

    @NotNull(message = "Książka musi być przypisana do placówki")
    private IntegerProperty idPlacowki = new SimpleIntegerProperty();

    public Ksiazka_2(int id, String tytul, String gatunek, LocalDate dataWydania, LocalDateTime dodano, int idAutora, int idPlacowki) {
        this.id.set(id);
        this.tytul.set(tytul);
        this.gatunek.set(gatunek);
        this.dataWydania.set(dataWydania);
        this.dodano.set(dodano);
        this.idAutora.set(idAutora);
        this.idPlacowki.set(idPlacowki);
    }

    public void setTytul(String tytul) {
        this.tytul.set(tytul);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty tytulProperty() { return tytul; }
    public StringProperty gatunekProperty() { return gatunek; }
    public ObjectProperty<LocalDate> dataWydaniaProperty() { return dataWydania; }
    public ObjectProperty<LocalDateTime> dodanoProperty() { return dodano; }
    public IntegerProperty idAutoraProperty() { return idAutora; }
    public IntegerProperty idPlacowkiProperty() { return idPlacowki; }

}
