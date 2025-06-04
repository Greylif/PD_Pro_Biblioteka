package com.example.pdprobiblioteka.control;

import com.example.pdprobiblioteka.model.Admin;
import com.example.pdprobiblioteka.model.Ksiazka;
import com.example.pdprobiblioteka.model.Uzytkownik;
import com.example.pdprobiblioteka.service.SupabaseClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library")
public class Control {

  private final SupabaseClient supabaseService;

  public Control(SupabaseClient supabaseService) {
    this.supabaseService = supabaseService;
  }

  @GetMapping("/placowki")
  public String getPlacowki() {
    return supabaseService.getPlacowki();
  }

  @PostMapping("/placowki")
  public String addPlacowka(@RequestParam String adres) {
    return supabaseService.addPlacowka(adres);
  }

  @PutMapping("/placowki/{id}")
  public String updatePlacowka(@PathVariable int id, @RequestParam(required = false) String adres) {
    return supabaseService.updatePlacowka(id, adres);
  }

  @DeleteMapping("/placowki/{id}")
  public String deletePlacowka(@PathVariable int id) {
    return supabaseService.deletePlacowka(id);
  }

  @GetMapping("/wypozyczenia")
  public String getWypozyczenia() {
    return supabaseService.getWypozyczenia();
  }

  @GetMapping("/wypozyczenia/{id}")
  public String getWypozyczeniauid(@PathVariable int id) {
    return supabaseService.getWypozyczeniauid(id);
  }

  @PostMapping("/wypozyczenia")
  public String addWypozyczenie(@RequestParam(required = false) String dataWypozyczenia,
      @RequestParam(required = false) String dataOddania,
      @RequestParam String terminOddania,
      @RequestParam int idKsiazki,
      @RequestParam int idUzytkownika) {
    return supabaseService.addWypozyczenie(dataWypozyczenia, dataOddania, terminOddania, idKsiazki,
        idUzytkownika);
  }

  @DeleteMapping("/wypozyczenia/{id}")
  public String deleteWypozyczenie(@PathVariable int id) {
    return supabaseService.deleteWypozyczenie(id);
  }

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

  @GetMapping("/kary")
  public String getKary() {
    return supabaseService.getKary();
  }

  @GetMapping("/kary/{id}")
  public String getKaryuid(@PathVariable int id) {
    return supabaseService.getKaryuid(id);
  }

  @PostMapping("/kary")
  public String addKara(@RequestParam double kwota,
      @RequestParam(required = false) String dataWydaniaKary,
      @RequestParam String terminZaplaty,
      @RequestParam int idUzytkownika,
      @RequestParam(required = false) String opis) {
    return supabaseService.addKara(kwota, dataWydaniaKary, terminZaplaty, idUzytkownika, opis);
  }

  @DeleteMapping("/kary/{id}")
  public String deleteKara(@PathVariable int id) {
    return supabaseService.deleteKara(id);
  }

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

  @GetMapping("/ksiazki")
  public String getKsiazki() {
    return supabaseService.getKsiazki();
  }

  @PostMapping("/ksiazki")
  public String addKsiazka(@RequestParam String tytul,
      @RequestParam String gatunek,
      @RequestParam String dataWydania,
      @RequestParam int idAutora,
      @RequestParam int idPlacowki) {
    return supabaseService.addKsiazka(tytul, gatunek, dataWydania, idAutora, idPlacowki);
  }

  @DeleteMapping("/ksiazki/{id}")
  public String deleteKsiazka(@PathVariable int id) {
    return supabaseService.deleteKsiazka(id);
  }

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

  @GetMapping("/uzytkownicy")
  public String getUzytkownicy() {
    return supabaseService.getUzytkownicy();
  }


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

  @DeleteMapping("/uzytkownicy/{id}")
  public String deleteUzytkownik(@PathVariable int id) {
    return supabaseService.deleteUzytkownik(id);
  }

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
      @RequestParam(required = false) String mfaSecret) {
    return supabaseService.updateUzytkownik(
        new Uzytkownik(id, imie, nazwisko, dataUrodzenia, nazwaUzytkownika, haslo, email,
            zablokowany, mfaEnabled, mfaSecret, "USER"));
  }

  @PutMapping("/uzytkownicy/passwordreset/{email}")
  public String updatePassUzytkownik(@PathVariable String email) {
    return supabaseService.putResetpasswordbyemail(email);
  }

  @GetMapping("/uzytkownicy/{login1}/{password}")
  public String getUzytkownicyLogin(@PathVariable String login1, @PathVariable String password) {
    return supabaseService.getUzytkownicyLogin(login1, password);
  }

  @GetMapping("/admini/{login1}/{password}")
  public String getAdminLogin(@PathVariable String login1, @PathVariable String password) {
    return supabaseService.getAdminLogin(login1, password);
  }


  @GetMapping("/admini")
  public String getAdmini() {
    return supabaseService.getAdmini();
  }

  @GetMapping("/admini/{id}")
  public String getAdminiaid(@PathVariable int id) {
    return supabaseService.getAdminaid(id);
  }

  @PostMapping("/admini")
  public String addAdmin(@RequestParam String imie,
      @RequestParam String nazwisko,
      @RequestParam String nazwaUzytkownika,
      @RequestParam String haslo,
      @RequestParam int idPlacowki) {
    return supabaseService.addAdmin(imie, nazwisko, nazwaUzytkownika, haslo, idPlacowki);
  }

  @DeleteMapping("/admini/{id}")
  public String deleteAdmin(@PathVariable int id) {
    return supabaseService.deleteAdmin(id);
  }

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

  @GetMapping("/autorzy")
  public String getAutorzy() {
    return supabaseService.getAutorzy();
  }

  @PostMapping("/autorzy")
  public String addAutor(@RequestParam String imie,
      @RequestParam String nazwisko,
      @RequestParam int rokUrodzenia) {
    return supabaseService.addAutor(imie, nazwisko, rokUrodzenia);
  }

  @DeleteMapping("/autorzy/{id}")
  public String deleteAutor(@PathVariable int id) {
    return supabaseService.deleteAutor(id);
  }

  @PutMapping("/autorzy/{id}")
  public String updateAutor(@PathVariable int id,
      @RequestParam(required = false) String imie,
      @RequestParam(required = false) String nazwisko,
      @RequestParam(required = false) Integer rokUrodzenia) {
    return supabaseService.updateAutor(id, imie, nazwisko, rokUrodzenia);
  }
}
