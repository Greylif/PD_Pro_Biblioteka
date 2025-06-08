package com.example.pdprobiblioteka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.pdprobiblioteka.model.Admin;
import com.example.pdprobiblioteka.model.AuthRequest;
import com.example.pdprobiblioteka.model.AuthResponse;
import com.example.pdprobiblioteka.model.Autorzy;
import com.example.pdprobiblioteka.model.Kary;
import com.example.pdprobiblioteka.model.Ksiazka;
import com.example.pdprobiblioteka.model.Placowka;
import com.example.pdprobiblioteka.model.Uzytkownik;
import com.example.pdprobiblioteka.model.Wypozyczenia;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Testy Klasy modelów.
 */
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
      admin.setNazwaUzytkownika("jkowalski");
      admin.setHaslo("pass");
      admin.setIdplacowki(10);

      assertEquals(1, admin.getId());
      assertEquals("Jan", admin.getImie());
      assertEquals("Kowalski", admin.getNazwisko());
      assertEquals("jkowalski", admin.getNazwaUzytkownika());
      assertEquals("pass", admin.getHaslo());
      assertEquals(10, admin.getIdplacowki());
    }

    @Test
    @DisplayName("Sprawdzanie tworzenia klasy Admin z wszystkimi argumentami")
    void testAllArgs() {
      Admin admin = new Admin(1, "Jan", "Kowalski", "jkowalski", "pass", 10, false,
          "secret", "ADMIN");

      assertEquals(1, admin.getId());
      assertEquals("Jan", admin.getImie());
      assertEquals("Kowalski", admin.getNazwisko());
      assertEquals("jkowalski", admin.getNazwaUzytkownika());
      assertEquals("pass", admin.getHaslo());
      assertEquals(10, admin.getIdplacowki());
      assertEquals(false, admin.getMfaEnabled());
      assertEquals("secret", admin.getMfaSecret());
    }

    @Test
    @DisplayName("Sprawdzanie ToString klasy Admin")
    void testToString() {
      Admin admin = new Admin();
      admin.setId(1);
      admin.setImie("Jan");
      admin.setNazwisko("Kowalski");
      admin.setNazwaUzytkownika("jkowalski");
      admin.setHaslo("pass");
      admin.setIdplacowki(10);
      admin.setMfaEnabled(false);
      admin.setMfaSecret("secret");

      String expected = "Admin{id=1, Imie='Jan', Nazwisko='Kowalski', "
          + "Nazwa_Uzytkownika='jkowalski', "
          + "Haslo='pass', id_placowki=10', mfa_enabled=false, mfa_secret=secret"
          + ", role='null}";
      assertEquals(expected, admin.toString());
    }
  }

  @Nested
  @DisplayName("Testy dla Autorzy")
  class AutorzyTests {

    @Test
    @DisplayName("Sprawdzanie tworzenia klasy Autorzy z wszystkimi argumentami oraz toString")
    void testAllArgs() {
      Autorzy autor = new Autorzy(1, "John", "Tolkien", 1892);

      assertEquals(1, autor.getId());
      assertEquals("John", autor.getImie());
      assertEquals("Tolkien", autor.getNazwisko());
      assertEquals(1892, autor.getRokUrodzenia());

      String expected = "Autorzy{id=1, Imie='John', Nazwisko='Tolkien', Rok_Urodzenia=1892}";
      assertEquals(expected, autor.toString());
    }
  }


  @Nested
  @DisplayName("Testy dla Kary")
  class KaryTests {

    @Test
    @DisplayName("Sprawdzanie tworzenia klasy Kara z wszystkimi argumentami oraz toString")
    void testAllArgs() {
      Kary kara = new Kary(1, 50.5, "2025-04-01", "2025-04-10", true, 100, "opis");

      assertEquals(1, kara.getId());
      assertEquals(50.5, kara.getKwota());
      assertEquals("2025-04-01", kara.getDataWydaniaKary());
      assertEquals("2025-04-10", kara.getTerminZaplaty());
      assertTrue(kara.getCzyZaplacono());
      assertEquals(100, kara.getIduzytkownika());
      String expected = "Kary{id=1, Kwota=50.5, Data_Wydania_Kary=2025-04-01, "
          + "Termin_Zaplaty=2025-04-10, "
          + "Czy_Zaplacono=true, id_uzytkownika=100"
          + ", opis=opis}";
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
      ksiazka.setDataWydania("1977-09-15");
      ksiazka.setDodano("2025-04-10");
      ksiazka.setIdautora(1);
      ksiazka.setIdplacowki(2);
      ksiazka.setRezerwacja(false);
      ksiazka.setCzywyporzyczono(false);

      assertEquals(1, ksiazka.getId());
      assertEquals("Silmarillion", ksiazka.getTytul());
      assertEquals("Fantasy", ksiazka.getGatunek());
      assertEquals("1977-09-15", ksiazka.getDataWydania());
      assertEquals("2025-04-10", ksiazka.getDodano());
      assertEquals(1, ksiazka.getIdautora());
      assertEquals(2, ksiazka.getIdplacowki());
      assertEquals(false, ksiazka.getRezerwacja());
      assertEquals(false, ksiazka.getCzywyporzyczono());
    }

    @Test
    @DisplayName("Sprawdzanie tworzenia klasy Ksiazka z wszystkimi argumentami")
    void testAllArgs() {
      Ksiazka ksiazka = new Ksiazka(1, "Silmarillion", "Fantasy", "1977-09-15", "2025-04-10", 1,
          2,
          false, false);

      assertEquals(1, ksiazka.getId());
      assertEquals("Silmarillion", ksiazka.getTytul());
      assertEquals("Fantasy", ksiazka.getGatunek());
      assertEquals("1977-09-15", ksiazka.getDataWydania());
      assertEquals("2025-04-10", ksiazka.getDodano());
      assertEquals(1, ksiazka.getIdautora());
      assertEquals(2, ksiazka.getIdplacowki());
      assertEquals(false, ksiazka.getRezerwacja());
      assertEquals(false, ksiazka.getCzywyporzyczono());
    }

    @Test
    @DisplayName("Sprawdzanie ToString klasy Ksiazka")
    void testToString() {
      Ksiazka ksiazka = new Ksiazka();
      ksiazka.setId(1);
      ksiazka.setTytul("Silmarillion");
      ksiazka.setGatunek("Fantasy");
      ksiazka.setDataWydania("1977-09-15");
      ksiazka.setDodano("2025-04-10");
      ksiazka.setIdautora(1);
      ksiazka.setIdplacowki(2);

      String expected = "Ksiazka{id=1, Tytul='Silmarillion', Gatunek='Fantasy', "
          + "Data_Wydania=1977-09-15, Dodano=2025-04-10, "
          + "id_autora=1, rezerwacja=null, id_placowki=2, czywyporzyczono=null}";
      assertEquals(expected, ksiazka.toString());
    }
  }

  @Nested
  @DisplayName("Testy dla Placowka")
  class PlacowkaTests {


    @Test
    @DisplayName("Sprawdzanie tworzenia klasy Placowka z wszystkimi argumentami i toString")
    void testAllArgs() {
      Placowka placowka = new Placowka(1,
          "Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce");

      assertEquals(1, placowka.getId());
      assertEquals("Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce", placowka.getAdres());
      String expected = "Placowka{id=1, Adres='Aleja Tysiąclecia Państwa Polskiego 7, "
          + "28-340 Kielce'}";
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
      user.setDataUrodzenia("2000-01-01");
      user.setHaslo("pass");
      user.setNazwaUzytkownika("jkowalski");
      user.setEmail("s092677@student.tu.kiece.pl");

      assertEquals(1, user.getId());
      assertEquals("Jan", user.getImie());
      assertEquals("Kowalski", user.getNazwisko());
      assertEquals("2000-01-01", user.getDataUrodzenia());
      assertEquals("pass", user.getHaslo());
      assertEquals("jkowalski", user.getNazwaUzytkownika());
      assertEquals("s092677@student.tu.kiece.pl", user.getEmail());
    }

    @Test
    @DisplayName("Sprawdzanie tworzenia klasy Uzytkownik z wszystkimi argumentami")
    void testAllArgs() {
      Uzytkownik user = new Uzytkownik(1, "Jan", "Kowalski", "2000-01-01", "pass", "jkowalski",
          "s092677@student.tu.kiece.pl", Boolean.FALSE, Boolean.FALSE, "secret", "USER");

      assertEquals(1, user.getId());
      assertEquals("Jan", user.getImie());
      assertEquals("Kowalski", user.getNazwisko());
      assertEquals("2000-01-01", user.getDataUrodzenia());
      assertEquals("pass", user.getHaslo());
      assertEquals("jkowalski", user.getNazwaUzytkownika());
      assertEquals("s092677@student.tu.kiece.pl", user.getEmail());
    }

    @Test
    @DisplayName("Sprawdzanie ToString klasy Uzytkownik")
    void testToString() {
      Uzytkownik user = new Uzytkownik();
      user.setId(1);
      user.setImie("Jan");
      user.setNazwisko("Kowalski");
      user.setDataUrodzenia("2000-01-01");
      user.setHaslo("pass");
      user.setNazwaUzytkownika("jkowalski");
      user.setEmail("s092677@student.tu.kiece.pl");
      user.setZablokowany(Boolean.FALSE);

      String expected = "Uzytkownik{id=1, Imie='Jan', Nazwisko='Kowalski', "
          + "Data_Urodzenia='2000-01-01', "
          + "Nazwa_Uzytkownika='jkowalski', Haslo='pass', "
          + "Email=s092677@student.tu.kiece.pl, Zablokowany=false, "
          + "Mfa_enabled=null, Mfa_secret=null, Role=null}";
      assertEquals(expected, user.toString());
    }
  }


  @Nested
  @DisplayName("Testy dla Wypozyczenia")
  class WypozyczeniaTests {


    @Test
    @DisplayName("Sprawdzanie tworzenia klasy Wypozyczenia z wszystkimi argumentami")
    void testAllArgs() {
      Wypozyczenia w = new Wypozyczenia(
          2, "2025-04-01", "2025-04-10", "2025-04-08", 101, 201
      );

      assertEquals(2, w.getId());
      assertEquals("2025-04-01", w.getDataWypozyczenia());
      assertEquals("2025-04-10", w.getDataOddania());
      assertEquals("2025-04-08", w.getTerminOddania());
      assertEquals(101, w.getIdksiazki());
      assertEquals(201, w.getIduzytkownika());
    }

    @Test
    @DisplayName("Sprawdzanie ToString klasy Wypozyczenia")
    void testToString() {
      Wypozyczenia w = new Wypozyczenia(
          3, "2025-03-01", "2025-03-10", "2025-03-08", 102, 202
      );
      String expected = "Wypozyczenia{id=3, Data_Wypozyczenia=2025-03-01, "
          + "Data_Oddania=2025-03-10, "
          + "Termin_Oddania=2025-03-08, id_ksiazki=102, id_uzytkownika=202}";
      assertEquals(expected, w.toString());
    }
  }

  @Nested
  @DisplayName("Testy dla AuthRequest")
  class AuthRequestTests {

    @Test
    @DisplayName("Sprawdzanie tworzenia klasy AuthRequest z wszystkimi argumentami")
    void testAllArgs() {
      AuthRequest authrequest = new AuthRequest();
      authrequest.setUsername("username");
      authrequest.setPassword("password");

      assertEquals("username", authrequest.getUsername());
      assertEquals("password", authrequest.getPassword());

    }
  }

  @Nested
  @DisplayName("Testy dla AuthResponse")
  class AuthResponseTests {

    @Test
    @DisplayName("Sprawdzanie tworzenia klasy AuthResponse z wszystkimi argumentami")
    void testAllArgs() {
      AuthResponse authresponse = new AuthResponse("token");

      assertEquals("token", authresponse.getToken());

    }
  }
}
