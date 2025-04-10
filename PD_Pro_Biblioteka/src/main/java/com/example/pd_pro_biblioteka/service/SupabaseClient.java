package com.example.pd_pro_biblioteka.service;


import com.example.pd_pro_biblioteka.exceptions.AccountValidationException;
import com.example.pd_pro_biblioteka.exceptions.InvalidTransactionException;
import com.example.pd_pro_biblioteka.exceptions.InstanceNotFoundException;
import com.example.pd_pro_biblioteka.exceptions.SupabaseConnectionException;
import com.example.pd_pro_biblioteka.exceptions.JsonFileException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;



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
    private static final String HASLO = "Haslo";
    private static final String NAZWA_UZYTKOWNIKA = "Nazwa_Uzytkownika";
    private static final String ID_AUTORA = "id_autora";
    private static final String ID_PLACOWKI = "id_placowki";

    public SupabaseClient(WebClient.Builder webClientBuilder) {
        try {
            this.webClient = webClientBuilder
                    .baseUrl("https://pcrbtauvyjxsspmfmwia.supabase.co/rest/v1")
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBjcmJ0YXV2eWp4c3NwbWZtd2lhIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc0MjM5MTczNCwiZXhwIjoyMDU3OTY3NzM0fQ.L5av7QMn8OqyF8WhaPo6IJOApwQcqJPCzqLlzJHz6zw")
                    .defaultHeader("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBjcmJ0YXV2eWp4c3NwbWZtd2lhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDIzOTE3MzQsImV4cCI6MjA1Nzk2NzczNH0.xdr4z5_udXpL4sbJpccFQrOPj_7_6w1bIs-FMGcdn1U")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
        }
        catch (Exception e)
        {
            throw new SupabaseConnectionException("Failed to connect to Supabase: ", e);
        }
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

    public String addKsiazka(String tytul, String gatunek, String dataWydania, int idAutora, int idPlacowki) {
        return postData(KSIAZKA, Map.of(
                "Tytul", tytul,
                "Gatunek", gatunek,
                "Data_Wydania", dataWydania,
                ID_AUTORA, idAutora,
                ID_PLACOWKI, idPlacowki
        ));
    }

    public String getUzytkownicy() {
        return fetchData(UZYTKOWNIK, "*");
    }

    public String getUzytkownicyLogin(String login_1, String password) {return fetchDatalogin(UZYTKOWNIK, "*", login_1, password);}

    public String getKsiazkaFiltr(Integer id, String tytul, String gatunek, String dataWydania, String Autor_Imie, String Autor_Nazwisko, Integer idPlacowki)
    {
        String k_statement = "(";
        if(id != null) k_statement += "id.like.*" + id + "*,";
        if(tytul != null)  k_statement += "Tytul.like.*" + tytul + "*,";
        if(gatunek != null) k_statement += "Gatunek.like.*" + gatunek + "*,";
        if(dataWydania != null) k_statement += "Data_Wydania.like.*" + dataWydania + "*,";
        if(idPlacowki != null) k_statement += "id_placowki.like.*" + idPlacowki + "*,";
        k_statement = k_statement.substring(0, k_statement.length() - 1) + ")";

        String a_statement = "(";
        if(Autor_Imie != null) a_statement += "Imie.like.*" + Autor_Imie + "*,";
        if(Autor_Nazwisko != null) a_statement += "Nazwisko.like.*" + Autor_Nazwisko + "*,";
        a_statement = a_statement.substring(0, a_statement.length() - 1) + ")";

        if(k_statement.equals(")")) k_statement = "(id.gt.0)";
        if(a_statement.equals(")")) a_statement = "(id.gt.0)";

        return fetchKsiazkaFiltr(k_statement, a_statement);
    }

    public String addUzytkownik(String imie, String nazwisko, Integer wiek, String Nazwa_Uzytkownika, String Haslo, String Email) {
        return postData(UZYTKOWNIK, Map.of(
                "Imie", imie,
                NAZWISKO, nazwisko,
                "Wiek", wiek,
                NAZWA_UZYTKOWNIKA, Nazwa_Uzytkownika,
                HASLO, Haslo,
                "Email", Email
        ));
    }

    public String getAdmini() {
        return fetchData(ADMIN, "*");
    }

    public String addAdmin(String imie, String nazwisko, String NazwaUzytkownika, String haslo, int idPlacowki) {
        return postData(ADMIN, Map.of(
                "Imie", imie,
                NAZWISKO, nazwisko,
                NAZWA_UZYTKOWNIKA, NazwaUzytkownika,
                HASLO, haslo,
                ID_PLACOWKI, idPlacowki
        ));
    }

    public String getAutorzy() {
        return fetchData(AUTORZY, "*");
    }

    public String addAutor(String imie, String nazwisko, Integer rokUrodzenia) {
        return postData(AUTORZY, Map.of(
                "Imie", imie,
                NAZWISKO, nazwisko,
                "Rok_Urodzenia", rokUrodzenia
        ));
    }

    public String updatePlacowka(int id, String adres) {
        Map<String, Object> data = new HashMap<>();
        if (adres != null) data.put("Adres", adres);
        return updateData(PLACOWKA, id, data);
    }

    public String updateWypozyczenie(int id, String dataWypozyczenia, String dataOddania, String terminOddania, Integer idKsiazki, Integer idUzytkownika) {
        Map<String, Object> data = new HashMap<>();
        if (dataWypozyczenia != null) data.put("Data_Wypozyczenia", dataWypozyczenia);
        if (dataOddania != null) data.put("Data_Oddania", dataOddania);
        if (terminOddania != null) data.put("Termin_Oddania", terminOddania);
        if (idKsiazki != null) data.put("ID_Ksiazki", idKsiazki);
        if (idUzytkownika != null) data.put("ID_Uzytkownika", idUzytkownika);
        return updateData(WYPOZYCZENIA, id, data);
    }

    public String updateKara(int id, Double kwota, String dataWydaniaKary, String terminZaplaty, Boolean czyZaplacono, Integer idUzytkownika) {
        Map<String, Object> data = new HashMap<>();
        if (kwota != null) data.put("Kwota", kwota);
        if (dataWydaniaKary != null) data.put("Data_Wydania_Kary", dataWydaniaKary);
        if (terminZaplaty != null) data.put("Termin_Zaplaty", terminZaplaty);
        if (czyZaplacono != null) data.put("Czy_Zaplacono", czyZaplacono);
        if (idUzytkownika != null) data.put("ID_Uzytkownika", idUzytkownika);
        return updateData("Kary", id, data);
    }

    public String updateKsiazka(int id, String tytul, String gatunek, String dataWydania, String dodano, Integer idAutora, Integer idPlacowki) {
        Map<String, Object> data = new HashMap<>();
        if (tytul != null) data.put("Tytul", tytul);
        if (gatunek != null) data.put("Gatunek", gatunek);
        if (dataWydania != null) data.put("Data_Wydania", dataWydania);
        if (dodano != null) data.put("Dodano", dodano);
        if (idAutora != null) data.put(ID_AUTORA, idAutora);
        if (idPlacowki != null) data.put(ID_PLACOWKI, idPlacowki);
        return updateData(KSIAZKA, id, data);
    }

    public String updateUzytkownik(int id, String imie, String nazwisko, Integer wiek, String nazwaUzytkownika, String haslo, String email) {
        Map<String, Object> data = new HashMap<>();
        if (imie != null) data.put("Imie", imie);
        if (nazwisko != null) data.put(NAZWISKO, nazwisko);
        if (wiek != null) data.put("Wiek", wiek);
        if (nazwaUzytkownika != null) data.put(NAZWA_UZYTKOWNIKA, nazwaUzytkownika);
        if (haslo != null) data.put(HASLO, haslo);
        if (email != null) data.put("Email", email);
        return updateData(UZYTKOWNIK, id, data);
    }

    public String updateAdmin(int id, String imie, String nazwisko, String nazwaUzytkownika, String haslo, Integer idPlacowki) {
        Map<String, Object> data = new HashMap<>();
        if (imie != null) data.put("Imie", imie);
        if (nazwisko != null) data.put(NAZWISKO, nazwisko);
        if (nazwaUzytkownika != null) data.put(NAZWA_UZYTKOWNIKA, nazwaUzytkownika);
        if (haslo != null) data.put(HASLO, haslo);
        if (idPlacowki != null) data.put(ID_PLACOWKI, idPlacowki);
        return updateData(ADMIN, id, data);
    }

    public String updateAutor(int id, String imie, String nazwisko, Integer rokUrodzenia) {
        Map<String, Object> data = new HashMap<>();
        if (imie != null) data.put("Imie", imie);
        if (nazwisko != null) data.put(NAZWISKO, nazwisko);
        if (rokUrodzenia != null) data.put("Rok_Urodzenia", rokUrodzenia);
        return updateData(AUTORZY, id, data);
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
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/" + table)
                            .queryParam("select", columns)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
        catch (Exception e)
    {
        throw new SupabaseConnectionException("Failed to fetch" + table + ": ", e);
    }
    }
/*
    private String fetchData(String table, String columns) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + table)
                        .queryParam("select", columns)
                        .queryParam(NAZWA_UZYTKOWNIKA, "like.*user*")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
*/
    private String fetchKsiazkaFiltr(String k_statement, String a_statement) {
        try {
            String data = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/" + KSIAZKA)
                            .queryParam("and", k_statement)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();


            String autor = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/" + AUTORZY)
                            .queryParam("id", "eq." + data.substring(data.indexOf(ID_AUTORA) + 11, (data.indexOf(ID_PLACOWKI) - 2)))
                            .queryParam("and", a_statement)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        List<Integer> autorIds = extractAutorIds(autor);
        return filterKsiazkiByAutor(data, autorIds);
        } catch (Exception e) {
            throw new SupabaseConnectionException("Failed to fetch filtered data: ", e);
        }

    }

    private List<Integer> extractAutorIds(String autorzyData) {
        List<Integer> autorIds = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(autorzyData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject autor = jsonArray.getJSONObject(i);
                autorIds.add(autor.getInt("id"));
            }
        } catch (Exception e) {
            throw new JsonFileException("Failed work on JSON", e);
        }
        return autorIds;
    }

    private String filterKsiazkiByAutor(String ksiazkiData, List<Integer> autorIds) {
        JSONArray filteredArray = new JSONArray();
        try {
            JSONArray jsonArray = new JSONArray(ksiazkiData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject ksiazka = jsonArray.getJSONObject(i);
                int idAutora = ksiazka.getInt(ID_AUTORA);
                if (autorIds.contains(idAutora)) {
                    filteredArray.put(ksiazka);
                }
            }
        } catch (Exception e) {
            throw new JsonFileException("Failed work on JSON", e);
        }
        return filteredArray.toString();
    }
/*
    private String fetchAnyFiltr(String collumn, String statement) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + collumn)
                        .queryParam("and", statement)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }
*/

    private String fetchDatalogin(String table, String columns, String login_data, String password_data) {
        try {
            String login = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/" + table)
                            .queryParam("select", columns)
                            .queryParam("or", "(Nazwa_Uzytkownika.eq." + login_data + ",Email.eq." + login_data + ")")
                            .queryParam(HASLO, "eq." + password_data)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        if(login == null)
        {
            throw new InstanceNotFoundException(table, ": brak uzytkownika");
        }
        return login;
        } catch (Exception e) {
            throw new SupabaseConnectionException("Failed to fetch user: ", e);
        }
    }


    private String postData(String table, Map<String, Object> requestBody) {
        try {
            return webClient.post()
                    .uri("/" + table)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new SupabaseConnectionException("Failed to post to table " + table + ": ", e);
        }
    }

    private String updateData(String table, int id, Map<String, Object> requestBody) {
        try {
            return webClient.patch()
                    .uri(uriBuilder -> uriBuilder.path("/" + table)
                            .queryParam("id", "eq." + id).build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new SupabaseConnectionException("Failed to update to table " + table + ": ", e);
        }

    }

    private String deleteData(String table, int id) {
        try{
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/" + table)
                .queryParam("id", "eq." + id).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        } catch (Exception e) {
            throw new SupabaseConnectionException("Failed to delete in table " + table + ": ", e);
        }
    }
}
