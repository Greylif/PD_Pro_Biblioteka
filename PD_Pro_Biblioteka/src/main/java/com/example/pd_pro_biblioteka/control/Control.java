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
    public String updatePlacowka(@PathVariable int id, @RequestParam String adres) {
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
    public String addWypozyczenie(@RequestParam String dataWypozyczenia,
                                  @RequestParam String dataOddania,
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
                                     @RequestParam String dataWypozyczenia,
                                     @RequestParam String dataOddania,
                                     @RequestParam String terminOddania,
                                     @RequestParam int idKsiazki,
                                     @RequestParam int idUzytkownika) {
        return supabaseService.updateWypozyczenie(id, dataWypozyczenia, dataOddania, terminOddania, idKsiazki, idUzytkownika);
    }

    @GetMapping("/kary")
    public String getKary() {
        return supabaseService.getKary();
    }

    @PostMapping("/kary")
    public String addKara(@RequestParam double kwota,
                          @RequestParam String dataWydaniaKary,
                          @RequestParam String terminZaplaty,
                          @RequestParam boolean czyZaplacono,
                          @RequestParam int idUzytkownika) {
        return supabaseService.addKara(kwota, dataWydaniaKary, terminZaplaty, czyZaplacono, idUzytkownika);
    }

    @DeleteMapping("/kary/{id}")
    public String deleteKara(@PathVariable int id) {
        return supabaseService.deleteKara(id);
    }

    @PutMapping("/kary/{id}")
    public String updateKara(@PathVariable int id,
                             @RequestParam double kwota,
                             @RequestParam String dataWydaniaKary,
                             @RequestParam String terminZaplaty,
                             @RequestParam boolean czyZaplacono,
                             @RequestParam int idUzytkownika) {
        return supabaseService.updateKara(id, kwota, dataWydaniaKary, terminZaplaty, czyZaplacono, idUzytkownika);
    }

    @GetMapping("/ksiazki")
    public String getKsiazki() {
        return supabaseService.getKsiazki();
    }

    @PostMapping("/ksiazki")
    public String addKsiazka(@RequestParam String tytul,
                             @RequestParam String gatunek,
                             @RequestParam String dataWydania,
                             @RequestParam String dodano,
                             @RequestParam int idAutora,
                             @RequestParam int idPlacowki) {
        return supabaseService.addKsiazka(tytul, gatunek, dataWydania, dodano, idAutora, idPlacowki);
    }

    @DeleteMapping("/ksiazki/{id}")
    public String deleteKsiazka(@PathVariable int id) {
        return supabaseService.deleteKsiazka(id);
    }

    @PutMapping("/ksiazki/{id}")
    public String updateKsiazka(@PathVariable int id,
                                @RequestParam String tytul,
                                @RequestParam String gatunek,
                                @RequestParam String dataWydania,
                                @RequestParam String dodano,
                                @RequestParam int idAutora,
                                @RequestParam int idPlacowki) {
        return supabaseService.updateKsiazka(id, tytul, gatunek, dataWydania, dodano, idAutora, idPlacowki);
    }

    @GetMapping("/uzytkownicy")
    public String getUzytkownicy() {
        return supabaseService.getUzytkownicy();
    }

    @PostMapping("/uzytkownicy")
    public String addUzytkownik(@RequestParam String imie,
                                @RequestParam String nazwisko,
                                @RequestParam int wiek) {
        return supabaseService.addUzytkownik(imie, nazwisko, wiek);
    }

    @DeleteMapping("/uzytkownicy/{id}")
    public String deleteUzytkownik(@PathVariable int id) {
        return supabaseService.deleteUzytkownik(id);
    }

    @PutMapping("/uzytkownicy/{id}")
    public String updateUzytkownik(@PathVariable int id,
                                   @RequestParam String imie,
                                   @RequestParam String nazwisko,
                                   @RequestParam int wiek) {
        return supabaseService.updateUzytkownik(id, imie, nazwisko, wiek);
    }

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
                              @RequestParam String imie,
                              @RequestParam String nazwisko,
                              @RequestParam String nazwaUzytkownika,
                              @RequestParam String haslo,
                              @RequestParam int idPlacowki) {
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
                              @RequestParam String imie,
                              @RequestParam String nazwisko,
                              @RequestParam int rokUrodzenia) {
        return supabaseService.updateAutor(id, imie, nazwisko, rokUrodzenia);
    }
}
