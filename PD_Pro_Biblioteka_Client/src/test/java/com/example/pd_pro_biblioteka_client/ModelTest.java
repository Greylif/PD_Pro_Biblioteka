package com.example.pd_pro_biblioteka_client;

import com.example.pd_pro_biblioteka_client.model.Wypozyczenia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private Wypozyczenia wypozyczenie;

    @BeforeEach
    void setUp() {
        wypozyczenie = new Wypozyczenia(1, "2024-01-01", "2024-02-01", "2024-01-15", 100, 200);
    }

    @Test
    void testConstructorAndProperties() {
        assertEquals(1, wypozyczenie.idProperty().get());
        assertEquals("2024-01-01", wypozyczenie.data_WypozyczeniaProperty().get());
        assertEquals("2024-02-01", wypozyczenie.termin_OddaniaProperty().get());
        assertEquals("2024-01-15", wypozyczenie.data_OddaniaProperty().get());
        assertEquals(100, wypozyczenie.id_ksiazkiProperty().get());
        assertEquals(200, wypozyczenie.id_uzytkownikaProperty().get());
    }

    @Test
    void testSettersAndGettersForExtraFields() {
        wypozyczenie.setImie("Jan");
        wypozyczenie.setNazwisko("Kowalski");
        wypozyczenie.setTytul("Pan Tadeusz");
        wypozyczenie.setAutor("Adam Mickiewicz");
        wypozyczenie.setDataWypozyczenia("2024-01-01");
        wypozyczenie.setTerminOddania("2024-02-01");

        assertEquals("Jan", wypozyczenie.getImie());
        assertEquals("Kowalski", wypozyczenie.getNazwisko());
        assertEquals("Pan Tadeusz", wypozyczenie.getTytul());
        assertEquals("Adam Mickiewicz", wypozyczenie.getAutor());
        assertEquals("2024-01-01", wypozyczenie.getDataWypozyczenia());
        assertEquals("2024-02-01", wypozyczenie.getTerminOddania());
    }

    @Test
    void testToStringOutput() {
        String expected = "Wypozyczenia{id=" + wypozyczenie.idProperty() +
                ", Data_Wypozyczenia=" + wypozyczenie.data_WypozyczeniaProperty() +
                ", Data_Oddania=" + wypozyczenie.data_OddaniaProperty() +
                ", Termin_Oddania=" + wypozyczenie.termin_OddaniaProperty() +
                ", id_ksiazki=" + wypozyczenie.id_ksiazkiProperty() +
                ", id_uzytkownika=" + wypozyczenie.id_uzytkownikaProperty() + "}";

        assertEquals(expected, wypozyczenie.toString());
    }
}
