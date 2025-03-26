package com.example.pd_pro_biblioteka.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;



@Service
public class SupabaseClient {
    private final WebClient webClient;
    private static final String PLACOWKA = "Placowka";
    private static final String AUTORZY = "Autorzy";
    private static final String ADMIN = "Admin";
    private static final String KSIAZKA = "Ksiazka";
    private static final String UZYTKOWNIK = "Uzytkownik";
    private static final String WYPOZYCZENIA = "Wypozyczenia";
    private static final String NAZWISKO = "Nazwisko";
    public SupabaseClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://pcrbtauvyjxsspmfmwia.supabase.co/rest/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBjcmJ0YXV2eWp4c3NwbWZtd2lhIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc0MjM5MTczNCwiZXhwIjoyMDU3OTY3NzM0fQ.L5av7QMn8OqyF8WhaPo6IJOApwQcqJPCzqLlzJHz6zw")
                .defaultHeader("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBjcmJ0YXV2eWp4c3NwbWZtd2lhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDIzOTE3MzQsImV4cCI6MjA1Nzk2NzczNH0.xdr4z5_udXpL4sbJpccFQrOPj_7_6w1bIs-FMGcdn1U")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String getPlacowki() {
        return fetchData(PLACOWKA, "id, Adres");
    }

    public String addPlacowka(String adres) {
        return postData(PLACOWKA, Map.of("Adres", adres));
    }

    public String getWypozyczenia() {
        return fetchData(WYPOZYCZENIA, "*");
    }

    public String addWypozyczenie(String dataWypozyczenia, String dataOddania, String terminOddania, int idKsiazki, int idUzytkownika) {
        return postData(WYPOZYCZENIA, Map.of(
                "Data_Wypozyczenia", dataWypozyczenia,
                "Data_Oddania", dataOddania,
                "Termin_Oddania", terminOddania,
                "id_ksiazki", idKsiazki,
                "id_uzytkownika", idUzytkownika
        ));
    }

    public String getKary() {
        return fetchData("Kary", "*");
    }

    public String addKara(double kwota, String dataWydaniaKary, String terminZaplaty, boolean czyZaplacono, int idUzytkownika) {
        return postData("Kary", Map.of(
                "Kwota", kwota,
                "Data_Wydania_Kary", dataWydaniaKary,
                "Termin_Zaplaty", terminZaplaty,
                "Czy_Zaplacono", czyZaplacono,
                "id_uzytkownika", idUzytkownika
        ));
    }

    public String getKsiazki() {
        return fetchData(KSIAZKA, "*");
    }

    public String addKsiazka(String tytul, String gatunek, String dataWydania, String dodano, int idAutora, int idPlacowki) {
        return postData(KSIAZKA, Map.of(
                "Tytul", tytul,
                "Gatunek", gatunek,
                "Data_Wydania", dataWydania,
                "Dodano", dodano,
                "id_autora", idAutora,
                "id_placowki", idPlacowki
        ));
    }

    public String getUzytkownicy() {
        return fetchData(UZYTKOWNIK, "*");
    }

    public String addUzytkownik(String imie, String nazwisko, int wiek) {
        return postData(UZYTKOWNIK, Map.of(
                "Imie", imie,
                NAZWISKO, nazwisko,
                "Wiek", wiek
        ));
    }

    public String getAdmini() {
        return fetchData(ADMIN, "*");
    }

    public String addAdmin(String imie, String nazwisko, String nazwaUzytkownika, String haslo, int idPlacowki) {
        return postData(ADMIN, Map.of(
                "Imie", imie,
                NAZWISKO, nazwisko,
                "Nazwa_Uzytkownika", nazwaUzytkownika,
                "Haslo", haslo,
                "id_placowki", idPlacowki
        ));
    }

    public String getAutorzy() {
        return fetchData(AUTORZY, "*");
    }

    public String addAutor(String imie, String nazwisko, int rokUrodzenia) {
        return postData(AUTORZY, Map.of(
                "Imie", imie,
                NAZWISKO, nazwisko,
                "Rok_Urodzenia", rokUrodzenia
        ));
    }

    public String updatePlacowka(int id, String adres) {
        return updateData(PLACOWKA, id, Map.of("Adres", adres));
    }

    public String updateWypozyczenie(int id, String dataWypozyczenia, String dataOddania, String terminOddania, int idKsiazki, int idUzytkownika) {
        return updateData(WYPOZYCZENIA, id, Map.of(
                "Data_Wypozyczenia", dataWypozyczenia,
                "Data_Oddania", dataOddania,
                "Termin_Oddania", terminOddania,
                "ID_Ksiazki", idKsiazki,
                "ID_Uzytkownika", idUzytkownika
        ));
    }

    public String updateKara(int id, double kwota, String dataWydaniaKary, String terminZaplaty, boolean czyZaplacono, int idUzytkownika) {
        return updateData("Kary", id, Map.of(
                "Kwota", kwota,
                "Data_Wydania_Kary", dataWydaniaKary,
                "Termin_Zaplaty", terminZaplaty,
                "Czy_Zaplacono", czyZaplacono,
                "ID_Uzytkownika", idUzytkownika
        ));
    }

    public String updateKsiazka(int id, String tytul, String gatunek, String dataWydania, String dodano, int idAutora, int idPlacowki) {
        return updateData(KSIAZKA, id, Map.of(
                "Tytul", tytul,
                "Gatunek", gatunek,
                "Data_Wydania", dataWydania,
                "Dodano", dodano,
                "ID_Autora", idAutora,
                "ID_Placowki", idPlacowki
        ));
    }

    public String updateUzytkownik(int id, String imie, String nazwisko, int wiek) {
        return updateData(UZYTKOWNIK, id, Map.of(
                "Imie", imie,
                NAZWISKO, nazwisko,
                "Wiek", wiek
        ));
    }

    public String updateAdmin(int id, String imie, String nazwisko, String nazwaUzytkownika, String haslo, int idPlacowki) {
        return updateData(ADMIN, id, Map.of(
                "Imie", imie,
                NAZWISKO, nazwisko,
                "Nazwa_Uzytkownika", nazwaUzytkownika,
                "Haslo", haslo,
                "ID_Placowki", idPlacowki
        ));
    }

    public String updateAutor(int id, String imie, String nazwisko, int rokUrodzenia) {
        return updateData(AUTORZY, id, Map.of(
                "Imie", imie,
                NAZWISKO, nazwisko,
                "Rok_Urodzenia", rokUrodzenia
        ));
    }

    public String deleteAutor(int id) {
        return deleteData(AUTORZY, id);
    }

    public String deleteAdmin(int id) {
        return deleteData(ADMIN, id);
    }

    public String deleteUzytkownik(int id) {
        return deleteData(UZYTKOWNIK, id);
    }

    public String deleteKsiazka(int id) {
        return deleteData(KSIAZKA, id);
    }

    public String deleteKara(int id) {
        return deleteData("Kary", id);
    }

    public String deletePlacowka(int id) {
        return deleteData(PLACOWKA, id);
    }

    public String deleteWypozyczenie(int id) {
        return deleteData(WYPOZYCZENIA, id);
    }

    private String fetchData(String table, String columns) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + table)
                        .queryParam("select", columns)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String postData(String table, Map<String, Object> requestBody) {
        return webClient.post()
                .uri("/" + table)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String updateData(String table, int id, Map<String, Object> requestBody) {
        return webClient.patch()
                .uri(uriBuilder -> uriBuilder.path("/" + table).queryParam("id", "eq." + id).build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String deleteData(String table, int id) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/" + table).queryParam("id", "eq." + id).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
