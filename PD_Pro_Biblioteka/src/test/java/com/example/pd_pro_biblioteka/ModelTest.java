package com.example.pd_pro_biblioteka;

import com.example.pd_pro_biblioteka.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModelTest {

    @Nested
    @DisplayName("Testy dla Admin")
    class AdminTests {

        @Test
        @DisplayName("Sprawdzanie tworzenia klasy Admin bez argumentow")
        void testNoArgs() {
            Admin admin = new Admin();
            admin.setId(1);
            admin.setImie("Jan");
            admin.setNazwisko("Kowalski");
            admin.setNazwa_Uzytkownika("jkowalski");
            admin.setHaslo("pass");
            admin.setId_placowki(10);

            assertEquals(1, admin.getId());
            assertEquals("Jan", admin.getImie());
            assertEquals("Kowalski", admin.getNazwisko());
            assertEquals("jkowalski", admin.getNazwa_Uzytkownika());
            assertEquals("pass", admin.getHaslo());
            assertEquals(10, admin.getId_placowki());
        }

        @Test
        @DisplayName("Sprawdzanie tworzenia klasy Admin z wszystkimi argumentami")
        void testAllArgs() {
            Admin admin = new Admin(1, "Jan", "Kowalski", "jkowalski", "pass", 10);

            assertEquals(1, admin.getId());
            assertEquals("Jan", admin.getImie());
            assertEquals("Kowalski", admin.getNazwisko());
            assertEquals("jkowalski", admin.getNazwa_Uzytkownika());
            assertEquals("pass", admin.getHaslo());
            assertEquals(10, admin.getId_placowki());
        }

        @Test
        @DisplayName("Sprawdzanie ToString klasy Admin")
        void testToString() {
            Admin admin = new Admin();
            admin.setId(1);
            admin.setImie("Jan");
            admin.setNazwisko("Kowalski");
            admin.setNazwa_Uzytkownika("jkowalski");
            admin.setHaslo("pass");
            admin.setId_placowki(10);

            String expected = "Admin{id=1, Imie='Jan', Nazwisko='Kowalski', Nazwa_Uzytkownika='jkowalski', Haslo='pass', id_placowki=10}";
            assertEquals(expected, admin.toString());
        }
    }

    @Nested
    @DisplayName("Testy dla Autorzy")
    class AutorzyTests {

        @Test
        @DisplayName("Sprawdzanie tworzenia klasy Autorzy bez argumentow")
        void testNoArgs() {
            Autorzy autor = new Autorzy();
            autor.setId(1);
            autor.setImie("John");
            autor.setNazwisko("Tolkien");
            autor.setRok_Urodzenia(1892);

            assertEquals(1, autor.getId());
            assertEquals("John", autor.getImie());
            assertEquals("Tolkien", autor.getNazwisko());
            assertEquals(1892, autor.getRok_Urodzenia());
        }

        @Test
        @DisplayName("Sprawdzanie tworzenia klasy Autorzy z wszystkimi argumentami")
        void testAllArgs() {
            Autorzy autor = new Autorzy(1,"John", "Tolkien", 1892);

            assertEquals(1, autor.getId());
            assertEquals("John", autor.getImie());
            assertEquals("Tolkien", autor.getNazwisko());
            assertEquals(1892, autor.getRok_Urodzenia());
        }

        @Test
        @DisplayName("Sprawdzanie ToString klasy Autorzy")
        void testToString() {
            Autorzy autor = new Autorzy();
            autor.setId(1);
            autor.setImie("John");
            autor.setNazwisko("Tolkien");
            autor.setRok_Urodzenia(1892);

            String expected = "Autorzy{id=1, Imie='John', Nazwisko='Tolkien', Rok_Urodzenia=1892}";
            assertEquals(expected, autor.toString());
        }
    }

    @Nested
    @DisplayName("Testy dla Kary")
    class KaryTests {

        @Test
        @DisplayName("Sprawdzanie tworzenia klasy Kary bez argumentow")
        void testNoArgs() {
            Kary kara = new Kary();
            kara.setId(1);
            kara.setKwota(50.5);
            kara.setData_Wydania_Kary("2025-04-01");
            kara.setTermin_Zaplaty("2025-04-10");
            kara.setCzy_Zaplacono(true);
            kara.setId_uzytkownika(100);

            assertEquals(1, kara.getId());
            assertEquals(50.5, kara.getKwota());
            assertEquals("2025-04-01", kara.getData_Wydania_Kary());
            assertEquals("2025-04-10", kara.getTermin_Zaplaty());
            assertTrue(kara.isCzy_Zaplacono());
            assertEquals(100, kara.getId_uzytkownika());
        }

        @Test
        @DisplayName("Sprawdzanie tworzenia klasy Kara z wszystkimi argumentami")
        void testAllArgs() {
            Kary kara = new Kary(1,50.5,"2025-04-01","2025-04-10",true,100);

            assertEquals(1, kara.getId());
            assertEquals(50.5, kara.getKwota());
            assertEquals("2025-04-01", kara.getData_Wydania_Kary());
            assertEquals("2025-04-10", kara.getTermin_Zaplaty());
            assertTrue(kara.isCzy_Zaplacono());
            assertEquals(100, kara.getId_uzytkownika());
        }

        @Test
        @DisplayName("Sprawdzanie ToString klasy Kary")
        void testToString() {
            Kary kara = new Kary();
            kara.setId(1);
            kara.setKwota(50.5);
            kara.setData_Wydania_Kary("2025-04-01");
            kara.setTermin_Zaplaty("2025-04-10");
            kara.setCzy_Zaplacono(true);
            kara.setId_uzytkownika(100);

            String expected = "Kary{id=1, Kwota=50.5, Data_Wydania_Kary=2025-04-01, Termin_Zaplaty=2025-04-10, Czy_Zaplacono=true, id_uzytkownika=100}";
            assertEquals(expected, kara.toString());
        }
    }

    @Nested
    @DisplayName("Testy dla Ksiazka")
    class KsiazkaTests {

        @Test
        @DisplayName("Sprawdzanie tworzenia klasy Ksiazka bez argumentow")
        void testNoArgs() {
            Ksiazka ksiazka = new Ksiazka();
            ksiazka.setId(1);
            ksiazka.setTytul("Silmarillion");
            ksiazka.setGatunek("Fantasy");
            ksiazka.setData_Wydania("1977-09-15");
            ksiazka.setDodano("2025-04-10");
            ksiazka.setId_autora(1);
            ksiazka.setId_placowki(2);

            assertEquals(1, ksiazka.getId());
            assertEquals("Silmarillion", ksiazka.getTytul());
            assertEquals("Fantasy", ksiazka.getGatunek());
            assertEquals("1977-09-15", ksiazka.getData_Wydania());
            assertEquals("2025-04-10", ksiazka.getDodano());
            assertEquals(1, ksiazka.getId_autora());
            assertEquals(2, ksiazka.getId_placowki());
        }

        @Test
        @DisplayName("Sprawdzanie tworzenia klasy Ksiazka z wszystkimi argumentami")
        void testAllArgs() {
            Ksiazka ksiazka = new Ksiazka(1,"Silmarillion","Fantasy","1977-09-15","2025-04-10",1,2);

            assertEquals(1, ksiazka.getId());
            assertEquals("Silmarillion", ksiazka.getTytul());
            assertEquals("Fantasy", ksiazka.getGatunek());
            assertEquals("1977-09-15", ksiazka.getData_Wydania());
            assertEquals("2025-04-10", ksiazka.getDodano());
            assertEquals(1, ksiazka.getId_autora());
            assertEquals(2, ksiazka.getId_placowki());
        }

        @Test
        @DisplayName("Sprawdzanie ToString klasy Ksiazka")
        void testToString() {
            Ksiazka ksiazka = new Ksiazka();
            ksiazka.setId(1);
            ksiazka.setTytul("Silmarillion");
            ksiazka.setGatunek("Fantasy");
            ksiazka.setData_Wydania("1977-09-15");
            ksiazka.setDodano("2025-04-10");
            ksiazka.setId_autora(1);
            ksiazka.setId_placowki(2);

            String expected = "Ksiazka{id=1, Tytul='Silmarillion', Gatunek='Fantasy', Data_Wydania=1977-09-15, Dodano=2025-04-10, id_autora=1, id_placowki=2}";
            assertEquals(expected, ksiazka.toString());
        }
    }

    @Nested
    @DisplayName("Testy dla Placowka")
    class PlacowkaTests {

        @Test
        @DisplayName("Sprawdzanie tworzenia klasy Placowka bez argumentow")
        void testNoArgs() {
            Placowka placowka = new Placowka();
            placowka.setId(1);
            placowka.setAdres("Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce");
            assertEquals(1, placowka.getId());
            assertEquals("Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce", placowka.getAdres());
        }

        @Test
        @DisplayName("Sprawdzanie tworzenia klasy Placowka z wszystkimi argumentami")
        void testAllArgs() {
            Placowka placowka = new Placowka(1, "Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce");

            assertEquals(1, placowka.getId());
            assertEquals("Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce", placowka.getAdres());
        }

        @Test
        @DisplayName("Sprawdzanie ToString klasy Placowka")
        void testToString() {
            Placowka placowka = new Placowka();
            placowka.setId(1);
            placowka.setAdres("Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce");

            String expected = "Placowka{id=1, Adres='Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce'}";
            assertEquals(expected, placowka.toString());
        }
    }

    @Nested
    @DisplayName("Testy dla Uzytkownik")
    class UzytkownikTests {

        @Test
        @DisplayName("Sprawdzanie tworzenia klasy Uzytkownik bez argumentow")
        void testNoArgs() {
            Uzytkownik user = new Uzytkownik();
            user.setId(1);
            user.setImie("Jan");
            user.setNazwisko("Kowalski");
            user.setData_Urodzenia("2000-01-01");
            user.setHaslo("pass");
            user.setNazwa_Uzytkownika("jkowalski");
            user.setEmail("s092677@student.tu.kiece.pl");

            assertEquals(1, user.getId());
            assertEquals("Jan", user.getImie());
            assertEquals("Kowalski", user.getNazwisko());
            assertEquals("2000-01-01", user.getData_Urodzenia());
            assertEquals("pass", user.getHaslo());
            assertEquals("jkowalski", user.getNazwa_Uzytkownika());
            assertEquals("s092677@student.tu.kiece.pl", user.getEmail());
        }

        @Test
        @DisplayName("Sprawdzanie tworzenia klasy Uzytkownik z wszystkimi argumentami")
        void testAllArgs() {
            Uzytkownik user = new Uzytkownik(1,"Jan","Kowalski","2000-01-01","jkowalski","pass","s092677@student.tu.kiece.pl", Boolean.FALSE);

            assertEquals(1, user.getId());
            assertEquals("Jan", user.getImie());
            assertEquals("Kowalski", user.getNazwisko());
            assertEquals("2000-01-01", user.getData_Urodzenia());
            assertEquals("jkowalski", user.getNazwa_Uzytkownika());
            assertEquals("pass", user.getHaslo());
            assertEquals("s092677@student.tu.kiece.pl", user.getEmail());
        }

        @Test
        @DisplayName("Sprawdzanie ToString klasy Uzytkownik")
        void testToString() {
            Uzytkownik user = new Uzytkownik();
            user.setId(1);
            user.setImie("Jan");
            user.setNazwisko("Kowalski");
            user.setData_Urodzenia("2000-01-01");
            user.setHaslo("pass");
            user.setNazwa_Uzytkownika("jkowalski");
            user.setEmail("s092677@student.tu.kiece.pl");
            user.setZablokowany(Boolean.FALSE);

            String expected = "Uzytkownik{id=1, Imie='Jan', Nazwisko='Kowalski', Data_Urodzenia='2000-01-01', Nazwa_Uzytkownika='jkowalski', Haslo='pass', Email=s092677@student.tu.kiece.pl, Zablokowany=false}";
            assertEquals(expected, user.toString());
        }
    }


    @Nested
    @DisplayName("Testy dla Wypozyczenia")
    class WypozyczeniaTests {

        @Test
        @DisplayName("Sprawdzanie tworzenia klasy Wypozyczenia bez argumentow")
        void testNoArgs() {
            Wypozyczenia w = new Wypozyczenia();
            w.setId(1);
            w.setData_Wypozyczenia("2025-04-14");
            w.setData_Oddania("2025-04-21");
            w.setTermin_Oddania("2025-04-20");
            w.setId_ksiazki(100);
            w.setId_uzytkownika(200);

            assertEquals(1, w.getId());
            assertEquals("2025-04-14", w.getData_Wypozyczenia());
            assertEquals("2025-04-21", w.getData_Oddania());
            assertEquals("2025-04-20", w.getTermin_Oddania());
            assertEquals(100, w.getId_ksiazki());
            assertEquals(200, w.getId_uzytkownika());
        }

        @Test
        @DisplayName("Sprawdzanie tworzenia klasy Wypozyczenia z wszystkimi argumentami")
        void testAllArgs() {
            Wypozyczenia w = new Wypozyczenia(
                    2, "2025-04-01", "2025-04-10", "2025-04-08", 101, 201
            );

            assertEquals(2, w.getId());
            assertEquals("2025-04-01", w.getData_Wypozyczenia());
            assertEquals("2025-04-10", w.getData_Oddania());
            assertEquals("2025-04-08", w.getTermin_Oddania());
            assertEquals(101, w.getId_ksiazki());
            assertEquals(201, w.getId_uzytkownika());
        }

        @Test
        @DisplayName("Sprawdzanie ToString klasy Wypozyczenia")
        void testToString() {
            Wypozyczenia w = new Wypozyczenia(
                    3, "2025-03-01", "2025-03-10", "2025-03-08", 102, 202
            );
            String expected = "Wypozyczenia{id=3, Data_Wypozyczenia=2025-03-01, Data_Oddania=2025-03-10, Termin_Oddania=2025-03-08, id_ksiazki=102, id_uzytkownika=202}";
            assertEquals(expected, w.toString());
        }
    }

}
