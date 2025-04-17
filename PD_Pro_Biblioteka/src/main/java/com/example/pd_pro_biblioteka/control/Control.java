package com.example.pd_pro_biblioteka.control;

import com.example.pd_pro_biblioteka.service.SupabaseClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping("/wypozyczenia")
    public String addWypozyczenie(@RequestParam(required = false) String dataWypozyczenia,
                                  @RequestParam(required = false) String dataOddania,
                                  @RequestParam String terminOddania,
                                  @RequestParam int idKsiazki,
                                  @RequestParam int idUzytkownika) {
        return supabaseService.addWypozyczenie(dataWypozyczenia, dataOddania, terminOddania, idKsiazki, idUzytkownika);
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
        return supabaseService.updateWypozyczenie(id, dataWypozyczenia, dataOddania, terminOddania, idKsiazki, idUzytkownika);
    }

    @GetMapping("/kary")
    public String getKary() {
        return supabaseService.getKary();
    }

    @PostMapping("/kary")
    public String addKara(@RequestParam double kwota,
                          @RequestParam(required = false) String dataWydaniaKary,
                          @RequestParam String terminZaplaty,
                          @RequestParam(required = false) Boolean czyZaplacono,
                          @RequestParam int idUzytkownika) {
        return supabaseService.addKara(kwota, dataWydaniaKary, terminZaplaty, idUzytkownika);
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
                             @RequestParam(required = false) Integer idUzytkownika) {
        return supabaseService.updateKara(id, kwota, dataWydaniaKary, terminZaplaty, czyZaplacono, idUzytkownika);
    }

    @GetMapping("/ksiazki")
    public String getKsiazki() { return supabaseService.getKsiazki(); }

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
                                @RequestParam(required = false) Integer idPlacowki) {
        return supabaseService.updateKsiazka(id, tytul, gatunek, dataWydania, dodano, idAutora, idPlacowki);
    }

    @GetMapping("/ksiazki/filtr")
    public String getKsiazkiFiltr(
                                @RequestParam(required = false) Integer id,
                                @RequestParam(required = false) String tytul,
                                @RequestParam(required = false) String gatunek,
                                @RequestParam(required = false) String dataWydania,
                                @RequestParam(required = false) String Autor_Imie,
                                @RequestParam(required = false) String Autor_Nazwisko,
                                @RequestParam(required = false) Integer idPlacowki)
    {
        return supabaseService.getKsiazkaFiltr(id, tytul, gatunek, dataWydania, Autor_Imie, Autor_Nazwisko, idPlacowki);
    }

    @GetMapping("/uzytkownicy")
    public String getUzytkownicy() {
        return supabaseService.getUzytkownicy();
    }

    @PostMapping("/uzytkownicy")
    public String addUzytkownik(@RequestParam String imie,
                                @RequestParam String nazwisko,
                                @RequestParam String Data_Urodzenia,
                                @RequestParam String Nazwa_Uzytkownika,
                                @RequestParam String Haslo,
                                @RequestParam String Email) {
        return supabaseService.addUzytkownik(imie, nazwisko, Data_Urodzenia, Nazwa_Uzytkownika, Haslo, Email);
    }

    @DeleteMapping("/uzytkownicy/{id}")
    public String deleteUzytkownik(@PathVariable int id) {
        return supabaseService.deleteUzytkownik(id);
    }

    @PutMapping("/uzytkownicy/{id}")
    public String updateUzytkownik(@PathVariable int id,
                                   @RequestParam(required = false) String imie,
                                   @RequestParam(required = false) String nazwisko,
                                   @RequestParam(required = false) String Data_Urodzenia,
                                   @RequestParam(required = false) String Nazwa_Uzytkownika,
                                   @RequestParam(required = false) String Haslo,
                                   @RequestParam(required = false) String Email,
                                   @RequestParam(required = false) Boolean Zablokowany) {
        return supabaseService.updateUzytkownik(id, imie, nazwisko, Data_Urodzenia, Nazwa_Uzytkownika, Haslo, Email, Zablokowany);
    }

    @GetMapping("/uzytkownicy/{login_1}/{password}")
    public String getUzytkownicyLogin(@PathVariable String login_1, @PathVariable String password) { return supabaseService.getUzytkownicyLogin(login_1, password); }

    @GetMapping("/admini")
    public String getAdmini() {
        return supabaseService.getAdmini();
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
                              @RequestParam(required = false) Integer idPlacowki) {
        return supabaseService.updateAdmin(id, imie, nazwisko, nazwaUzytkownika, haslo, idPlacowki);
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
