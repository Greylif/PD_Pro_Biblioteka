package com.example.pdprobiblioteka.control;

import com.example.pdprobiblioteka.model.Admin;
import com.example.pdprobiblioteka.model.Ksiazka;
import com.example.pdprobiblioteka.model.Uzytkownik;
import com.example.pdprobiblioteka.service.SupabaseClient;
import java.util.Base64;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Główny kontroler REST dla operacji na danych biblioteki, takich jak książki, wypożyczenia, kary,
 * autorzy, placówki, użytkownicy i administratorzy.
 */
@RestController
@RequestMapping("/library")
public class Control {

  private final SupabaseClient supabaseService;

  /**
   * Konstruktor klasy.
   *
   * @param supabaseService klient do komunikacji z bazą danych Supabase
   */
  public Control(SupabaseClient supabaseService) {
    this.supabaseService = supabaseService;
  }

  /**
   * Zwraca listę wszystkich placówek bibliotecznych.
   *
   * @return lista placówek w formacie JSON
   */

  @GetMapping("/placowki")
  public String getPlacowki() {
    return supabaseService.getPlacowki();
  }


  /**
   * Dodaje nową placówkę do systemu.
   *
   * @param adres adres placówki
   * @return odpowiedź serwera po dodaniu
   */
  @PostMapping("/placowki")
  public String addPlacowka(@RequestParam String adres) {
    return supabaseService.addPlacowka(adres);
  }

  /**
   * Aktualizuje dane istniejącej placówki.
   *
   * @param id    identyfikator placówki
   * @param adres nowy adres placówki (opcjonalny)
   * @return odpowiedź serwera po aktualizacji
   */
  @PutMapping("/placowki/{id}")
  public String updatePlacowka(@PathVariable int id, @RequestParam(required = false) String adres) {
    return supabaseService.updatePlacowka(id, adres);
  }

  /**
   * Usuwa placówkę z systemu.
   *
   * @param id identyfikator placówki
   * @return odpowiedź serwera po usunięciu
   */
  @DeleteMapping("/placowki/{id}")
  public String deletePlacowka(@PathVariable int id) {
    return supabaseService.deletePlacowka(id);
  }

  /**
   * Zwraca listę wszystkich wypożyczeń.
   *
   * @return JSON z danymi wypożyczeń
   */
  @GetMapping("/wypozyczenia")
  public String getWypozyczenia() {
    return supabaseService.getWypozyczenia();
  }

  /**
   * Zwraca szczegóły wypożyczenia o podanym ID wypożyczającego.
   *
   * @param id identyfikator wypożyczenia
   * @return JSON z danymi wypożyczenia
   */
  @GetMapping("/wypozyczenia/{id}")
  public String getWypozyczeniauid(@PathVariable int id) {
    return supabaseService.getWypozyczeniauid(id);
  }

  /**
   * Dodaje nowe wypożyczenie.
   *
   * @param dataWypozyczenia data wypożyczenia (opcjonalna)
   * @param dataOddania      data oddania (opcjonalna)
   * @param terminOddania    termin oddania
   * @param idKsiazki        ID książki
   * @param idUzytkownika    ID użytkownika
   * @return JSON z wynikiem operacji
   */
  @PostMapping("/wypozyczenia")
  public String addWypozyczenie(@RequestParam(required = false) String dataWypozyczenia,
      @RequestParam(required = false) String dataOddania,
      @RequestParam String terminOddania,
      @RequestParam int idKsiazki,
      @RequestParam int idUzytkownika) {
    return supabaseService.addWypozyczenie(dataWypozyczenia, dataOddania, terminOddania, idKsiazki,
        idUzytkownika);
  }

  /**
   * Usuwa wypożyczenie o podanym ID.
   *
   * @param id identyfikator wypożyczenia
   * @return JSON z wynikiem operacji
   */
  @DeleteMapping("/wypozyczenia/{id}")
  public String deleteWypozyczenie(@PathVariable int id) {
    return supabaseService.deleteWypozyczenie(id);
  }

  /**
   * Aktualizuje dane wypożyczenia.
   *
   * @param id               identyfikator wypożyczenia
   * @param dataWypozyczenia data wypożyczenia (opcjonalna)
   * @param dataOddania      data oddania (opcjonalna)
   * @param terminOddania    termin oddania (opcjonalny)
   * @param idKsiazki        ID książki (opcjonalny)
   * @param idUzytkownika    ID użytkownika (opcjonalny)
   * @return JSON z wynikiem operacji
   */
  @PutMapping("/wypozyczenia/{id}")
  public String updateWypozyczenie(@PathVariable int id,
      @RequestParam(required = false) String dataWypozyczenia,
      @RequestParam(required = false) String dataOddania,
      @RequestParam(required = false) String terminOddania,
      @RequestParam(required = false) Integer idKsiazki,
      @RequestParam(required = false) Integer idUzytkownika) {
    return supabaseService.updateWypozyczenie(id, dataWypozyczenia, dataOddania, terminOddania,
        idKsiazki, idUzytkownika);
  }

  /**
   * Zwraca listę kar.
   *
   * @return JSON z danymi kar
   */
  @GetMapping("/kary")
  public String getKary() {
    return supabaseService.getKary();
  }


  /**
   * Zwraca szczegóły kary o podanym ID uzytkownika.
   *
   * @param id identyfikator kary
   * @return JSON z danymi kary
   */
  @GetMapping("/kary/{id}")
  public String getKaryuid(@PathVariable int id) {
    return supabaseService.getKaryuid(id);
  }

  /**
   * Dodaje nową karę.
   *
   * @param kwota           wysokość kary
   * @param dataWydaniaKary data wydania (opcjonalna)
   * @param terminZaplaty   termin zapłaty
   * @param idUzytkownika   ID użytkownika
   * @param opis            opis (opcjonalny)
   * @return JSON z wynikiem operacji
   */
  @PostMapping("/kary")
  public String addKara(@RequestParam double kwota,
      @RequestParam(required = false) String dataWydaniaKary,
      @RequestParam String terminZaplaty,
      @RequestParam int idUzytkownika,
      @RequestParam(required = false) String opis) {
    return supabaseService.addKara(kwota, dataWydaniaKary, terminZaplaty, idUzytkownika, opis);
  }

  /**
   * Usuwa karę o podanym ID.
   *
   * @param id identyfikator kary
   * @return JSON z wynikiem operacji
   */
  @DeleteMapping("/kary/{id}")
  public String deleteKara(@PathVariable int id) {
    return supabaseService.deleteKara(id);
  }

  /**
   * Aktualizuje dane kary.
   *
   * @param id              identyfikator kary
   * @param kwota           wysokość kary (opcjonalna)
   * @param dataWydaniaKary data wydania (opcjonalna)
   * @param terminZaplaty   termin zapłaty (opcjonalny)
   * @param czyZaplacono    czy zapłacono (opcjonalne)
   * @param idUzytkownika   ID użytkownika (opcjonalne)
   * @param opis            opis (opcjonalny)
   * @return JSON z wynikiem operacji
   */
  @PutMapping("/kary/{id}")
  public String updateKara(@PathVariable int id,
      @RequestParam(required = false) Double kwota,
      @RequestParam(required = false) String dataWydaniaKary,
      @RequestParam(required = false) String terminZaplaty,
      @RequestParam(required = false) Boolean czyZaplacono,
      @RequestParam(required = false) Integer idUzytkownika,
      @RequestParam(required = false) String opis) {
    return supabaseService.updateKara(id, kwota, dataWydaniaKary, terminZaplaty, czyZaplacono,
        idUzytkownika, opis);
  }

  /**
   * Zwraca listę książek.
   *
   * @return JSON z danymi książek
   */
  @GetMapping("/ksiazki")
  public String getKsiazki() {
    return supabaseService.getKsiazki();
  }

  /**
   * Dodaje nową książkę.
   *
   * @param tytul       tytuł książki
   * @param gatunek     gatunek
   * @param dataWydania data wydania
   * @param idAutora    ID autora
   * @param idPlacowki  ID placówki
   * @return JSON z wynikiem operacji
   */
  @PostMapping("/ksiazki")
  public String addKsiazka(@RequestParam String tytul,
      @RequestParam String gatunek,
      @RequestParam String dataWydania,
      @RequestParam int idAutora,
      @RequestParam int idPlacowki) {
    return supabaseService.addKsiazka(tytul, gatunek, dataWydania, idAutora, idPlacowki);
  }

  /**
   * Usuwa książkę o podanym ID.
   *
   * @param id identyfikator książki
   * @return JSON z wynikiem operacji
   */
  @DeleteMapping("/ksiazki/{id}")
  public String deleteKsiazka(@PathVariable int id) {
    return supabaseService.deleteKsiazka(id);
  }

  /**
   * Aktualizuje dane książki.
   *
   * @param id              identyfikator książki
   * @param tytul           tytuł (opcjonalny)
   * @param gatunek         gatunek (opcjonalny)
   * @param dataWydania     data wydania (opcjonalna)
   * @param dodano          data dodania (opcjonalna)
   * @param idAutora        ID autora (opcjonalny)
   * @param idPlacowki      ID placówki (opcjonalny)
   * @param rezerwacja      czy zarezerwowano (opcjonalne)
   * @param czywyporzyczono czy wypożyczono (opcjonalne)
   * @return JSON z wynikiem operacji
   */
  @PutMapping("/ksiazki/{id}")
  public String updateKsiazka(@PathVariable int id,
      @RequestParam(required = false) String tytul,
      @RequestParam(required = false) String gatunek,
      @RequestParam(required = false) String dataWydania,
      @RequestParam(required = false) String dodano,
      @RequestParam(required = false) Integer idAutora,
      @RequestParam(required = false) Integer idPlacowki,
      @RequestParam(required = false) Boolean rezerwacja,
      @RequestParam(required = false) Boolean czywyporzyczono) {
    return supabaseService.updateKsiazka(
        new Ksiazka(id, tytul, gatunek, dataWydania, dodano, idAutora, idPlacowki, rezerwacja,
            czywyporzyczono));
  }

  /**
   * Zwraca książki spełniające kryteria filtru.
   *
   * @param id            ID książki (opcjonalne)
   * @param tytul         tytuł (opcjonalny)
   * @param gatunek       gatunek (opcjonalny)
   * @param dataWydania   data wydania (opcjonalna)
   * @param autorImie     imię autora (opcjonalne)
   * @param autorNazwisko nazwisko autora (opcjonalne)
   * @param idPlacowki    ID placówki (opcjonalne)
   * @return JSON z wynikami filtru
   */
  @GetMapping("/ksiazki/filtr")
  public String getKsiazkiFiltr(
      @RequestParam(required = false) Integer id,
      @RequestParam(required = false) String tytul,
      @RequestParam(required = false) String gatunek,
      @RequestParam(required = false) String dataWydania,
      @RequestParam(required = false) String autorImie,
      @RequestParam(required = false) String autorNazwisko,
      @RequestParam(required = false) Integer idPlacowki) {
    return supabaseService.getKsiazkaFiltr(id, tytul, gatunek, dataWydania, autorImie,
        autorNazwisko, idPlacowki);
  }

  /**
   * Zwraca listę użytkowników.
   *
   * @return JSON z danymi użytkowników
   */
  @GetMapping("/uzytkownicy")
  public String getUzytkownicy() {
    return supabaseService.getUzytkownicy();
  }

  /**
   * Dodaje nowego użytkownika.
   *
   * @param imie             imię
   * @param nazwisko         nazwisko
   * @param dataUrodzenia    data urodzenia
   * @param nazwaUzytkownika login
   * @param haslo            hasło
   * @param email            adres e-mail
   * @return JSON z wynikiem operacji
   */
  @PostMapping("/uzytkownicy")
  public String addUzytkownik(@RequestParam String imie,
      @RequestParam String nazwisko,
      @RequestParam String dataUrodzenia,
      @RequestParam String nazwaUzytkownika,
      @RequestParam String haslo,
      @RequestParam String email) {
    return supabaseService.addUzytkownik(imie, nazwisko, dataUrodzenia, nazwaUzytkownika, haslo,
        email);
  }

  /**
   * Usuwa użytkownika o podanym ID.
   *
   * @param id         identyfikator użytkownika
   * @param authHeader otrzymany nagłówek zawierający token autoryzacji
   * @return JSON z wynikiem operacji
   */
  @DeleteMapping("/uzytkownicy/{id}")
  public String deleteUzytkownik(@PathVariable int id,
      @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");

    String[] parts = token.split("\\.");
    if (parts.length == 3) {
      String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

      JSONObject json = new JSONObject(payload);
      if (!(json.getString("userId").equals(String.valueOf(id)))) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied for this user");
      }
    }
    return supabaseService.deleteUzytkownik(id);
  }

  /**
   * Aktualizuje dane użytkownika.
   *
   * @param id               identyfikator użytkownika
   * @param imie             imię (opcjonalne)
   * @param nazwisko         nazwisko (opcjonalne)
   * @param dataUrodzenia    data urodzenia (opcjonalna)
   * @param nazwaUzytkownika login (opcjonalny)
   * @param haslo            hasło (opcjonalne)
   * @param email            e-mail (opcjonalny)
   * @param zablokowany      czy zablokowany (opcjonalny)
   * @param mfaEnabled       MFA czy włączone (opcjonalne)
   * @return JSON z wynikiem operacji
   */
  @PutMapping("/uzytkownicy/{id}")
  public String updateUzytkownik(@PathVariable int id,
      @RequestParam(required = false) String imie,
      @RequestParam(required = false) String nazwisko,
      @RequestParam(required = false) String dataUrodzenia,
      @RequestParam(required = false) String nazwaUzytkownika,
      @RequestParam(required = false) String haslo,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) Boolean zablokowany,
      @RequestParam(required = false) Boolean mfaEnabled,
      @RequestParam(required = false) String mfaSecret,
      @RequestHeader("Authorization") String authHeader) {
    String token = authHeader.replace("Bearer ", "");

    String[] parts = token.split("\\.");
    if (parts.length == 3) {
      String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

      JSONObject json = new JSONObject(payload);
      if (!(json.getString("userId").equals(String.valueOf(id)))) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied for this user");
      }
    }
    return supabaseService.updateUzytkownik(
        new Uzytkownik(id, imie, nazwisko, dataUrodzenia, nazwaUzytkownika, haslo, email,
            zablokowany, mfaEnabled, mfaSecret, "USER"));
  }


  /**
   * Resetuje hasło użytkownika na podstawie e-maila.
   *
   * @param email adres e-mail użytkownika
   * @return JSON z wynikiem operacji
   */
  @PutMapping("/uzytkownicy/passwordreset/{email}")
  public String updatePassUzytkownik(@PathVariable String email) {
    return supabaseService.putResetpasswordbyemail(email);
  }


  /**
   * Logowanie użytkownika.
   *
   * @param login1   login użytkownika
   * @param password hasło
   * @return JSON z danymi użytkownika
   */
  @GetMapping("/uzytkownicy/{login1}/{password}")
  public String getUzytkownicyLogin(@PathVariable String login1, @PathVariable String password) {
    return supabaseService.getUzytkownicyLogin(login1, password);
  }


  /**
   * Logowanie administratora.
   *
   * @param login1   login administratora
   * @param password hasło
   * @return JSON z danymi administratora
   */
  @GetMapping("/admini/{login1}/{password}")
  public String getAdminLogin(@PathVariable String login1, @PathVariable String password) {
    return supabaseService.getAdminLogin(login1, password);
  }

  /**
   * Zwraca listę administratorów.
   *
   * @return JSON z danymi administratorów
   */
  @GetMapping("/admini")
  public String getAdmini() {
    return supabaseService.getAdmini();
  }

  /**
   * Zwraca dane administratora o podanym ID.
   *
   * @param id identyfikator administratora
   * @return JSON z danymi administratora
   */
  @GetMapping("/admini/{id}")
  public String getAdminiaid(@PathVariable int id) {
    return supabaseService.getAdminaid(id);
  }

  /**
   * Dodaje nowego administratora.
   *
   * @param imie             imię
   * @param nazwisko         nazwisko
   * @param nazwaUzytkownika login
   * @param haslo            hasło
   * @param idPlacowki       ID placówki
   * @return JSON z wynikiem operacji
   */
  @PostMapping("/admini")
  public String addAdmin(@RequestParam String imie,
      @RequestParam String nazwisko,
      @RequestParam String nazwaUzytkownika,
      @RequestParam String haslo,
      @RequestParam int idPlacowki) {
    return supabaseService.addAdmin(imie, nazwisko, nazwaUzytkownika, haslo, idPlacowki);
  }

  /**
   * Usuwa administratora o podanym ID.
   *
   * @param id identyfikator administratora
   * @return JSON z wynikiem operacji
   */
  @DeleteMapping("/admini/{id}")
  public String deleteAdmin(@PathVariable int id) {
    return supabaseService.deleteAdmin(id);
  }

  /**
   * Aktualizuje dane administratora.
   *
   * @param id               identyfikator administratora
   * @param imie             imię (opcjonalne)
   * @param nazwisko         nazwisko (opcjonalne)
   * @param nazwaUzytkownika login (opcjonalny)
   * @param haslo            hasło (opcjonalne)
   * @param idPlacowki       ID placówki (opcjonalne)
   * @param mfaEnabled       MFA czy włączone (opcjonalne)
   * @param mfaSecret        sekret MFA (opcjonalny)
   * @return JSON z wynikiem operacji
   */
  @PutMapping("/admini/{id}")
  public String updateAdmin(@PathVariable int id,
      @RequestParam(required = false) String imie,
      @RequestParam(required = false) String nazwisko,
      @RequestParam(required = false) String nazwaUzytkownika,
      @RequestParam(required = false) String haslo,
      @RequestParam(required = false) Integer idPlacowki,
      @RequestParam(required = false) Boolean mfaEnabled,
      @RequestParam(required = false) String mfaSecret) {
    Admin admin = new Admin(id, imie, nazwisko, nazwaUzytkownika, haslo, idPlacowki, mfaEnabled,
        mfaSecret, "Admin");
    return supabaseService.updateAdmin(admin);
  }

  /**
   * Zwraca listę autorów.
   *
   * @return JSON z danymi autorów
   */
  @GetMapping("/autorzy")
  public String getAutorzy() {
    return supabaseService.getAutorzy();
  }

  /**
   * Dodaje nowego autora.
   *
   * @param imie         imię
   * @param nazwisko     nazwisko
   * @param rokUrodzenia rok urodzenia
   * @return JSON z wynikiem operacji
   */
  @PostMapping("/autorzy")
  public String addAutor(@RequestParam String imie,
      @RequestParam String nazwisko,
      @RequestParam int rokUrodzenia) {
    return supabaseService.addAutor(imie, nazwisko, rokUrodzenia);
  }

  /**
   * Usuwa autora o podanym ID.
   *
   * @param id identyfikator autora
   * @return JSON z wynikiem operacji
   */
  @DeleteMapping("/autorzy/{id}")
  public String deleteAutor(@PathVariable int id) {
    return supabaseService.deleteAutor(id);
  }

  /**
   * Aktualizuje dane autora.
   *
   * @param id           identyfikator autora
   * @param imie         imię (opcjonalne)
   * @param nazwisko     nazwisko (opcjonalne)
   * @param rokUrodzenia rok urodzenia (opcjonalny)
   * @return JSON z wynikiem operacji
   */
  @PutMapping("/autorzy/{id}")
  public String updateAutor(@PathVariable int id,
      @RequestParam(required = false) String imie,
      @RequestParam(required = false) String nazwisko,
      @RequestParam(required = false) Integer rokUrodzenia) {
    return supabaseService.updateAutor(id, imie, nazwisko, rokUrodzenia);
  }
}