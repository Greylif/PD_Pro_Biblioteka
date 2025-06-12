package com.example.pd_pro_biblioteka_client.model;

import com.google.gson.annotations.SerializedName;
import javafx.beans.property.*;

public class Filtr {
    @SerializedName("id")
    private final IntegerProperty id = new SimpleIntegerProperty();
    @SerializedName("Tytul")
    private final StringProperty tytul = new SimpleStringProperty();
    @SerializedName("Gatunek")
    private final StringProperty gatunek = new SimpleStringProperty();
    @SerializedName("id_autora")
    private final IntegerProperty idAutora = new SimpleIntegerProperty();
    @SerializedName("Data_Wydania")
    private final IntegerProperty dataWydania = new SimpleIntegerProperty();
    @SerializedName("Dodano")
    private final StringProperty dodano = new SimpleStringProperty();
    @SerializedName("Rezerwacja")
    private final BooleanProperty rezerwacja = new SimpleBooleanProperty();
    @SerializedName("czy_wypozyczono")
    private final BooleanProperty czyWypozyczono = new SimpleBooleanProperty();
    @SerializedName("id_placowki")
    private final IntegerProperty idPlacowki = new SimpleIntegerProperty();

    private final StringProperty autorName = new SimpleStringProperty();
    public String getAutorName() { return autorName.get(); }
    public void setAutorName(String value) { autorName.set(value); }
    public StringProperty autorDataProperty() { return autorName; }

    public Filtr(boolean rez, boolean wypo, String gat, int idA, int year, int idB, int idP, String dodano, String tyt, String autorName) {
        this.rezerwacja.set(rez);
        this.czyWypozyczono.set(wypo);
        this.gatunek.set(gat);
        this.idAutora.set(idA);
        this.dataWydania.set(year);
        this.id.set(idB);
        this.idPlacowki.set(idP);
        this.dodano.set(dodano);
        this.tytul.set(tyt);
        this.autorName.set(autorName);
    }

    public int getIdPlacowki() { return idPlacowki.get(); }
    public void setIdPlacowki(int value) { idPlacowki.set(value); }
    public IntegerProperty idPlacowkiProperty() { return idPlacowki; }

    public int getId() { return id.get(); }
    public String getTytul() { return tytul.get(); }
    public String getGatunek() { return gatunek.get(); }
    public int getIdAutora() { return idAutora.get(); }
    public int getDataWydania() { return dataWydania.get(); }
    public String getDodano() { return dodano.get(); }
    public Boolean isRezerwacja() { return rezerwacja.get(); }
    public Boolean isCzyWypozyczono() { return czyWypozyczono.get(); }

    public void setId(int id) { this.id.set(id); }
    public void setTytul(String tytul) { this.tytul.set(tytul); }
    public void setGatunek(String gatunek) { this.gatunek.set(gatunek); }
    public void setIdAutora(int idAutora) { this.idAutora.set(idAutora); }
    public void setDataWydania(int dataWydania) { this.dataWydania.set(dataWydania); }
    public void setDodano(String dodano) { this.dodano.set(dodano); }
    public void setRezerwacja(Boolean rezerwacja) { this.rezerwacja.set(rezerwacja); }
    public void setCzyWypozyczono(Boolean czyWypozyczono) { this.czyWypozyczono.set(czyWypozyczono); }

    public IntegerProperty idProperty() { return id; }
    public StringProperty tytulProperty() { return tytul; }
    public StringProperty gatunekProperty() { return gatunek; }
    public IntegerProperty idAutoraProperty() { return idAutora; }
    public IntegerProperty dataWydaniaProperty() { return dataWydania; }
    public StringProperty dodanoProperty() { return dodano; }
    public BooleanProperty rezerwacjaProperty() { return rezerwacja; }
    public BooleanProperty czyWypozyczonoProperty() { return czyWypozyczono; }


    @Override
    public String toString() {
        return "Filtr{" +
                "id=" + id +
                ", tytul='" + tytul + '\'' +
                ", id_autora=" + idAutora +
                ", gatunek='" + gatunek + '\'' +
                ", data_Wydania=" + dataWydania +
                ", czy_wypozyczono=" + czyWypozyczono +
                ", rezerwacja=" + rezerwacja +
                ", id_placowki=" + idPlacowki +
                ", dodano='" + dodano + '\'' +
                '}';
    }
}
