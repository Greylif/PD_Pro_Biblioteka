package com.example.pd_pro_biblioteka.service;



import com.example.pd_pro_biblioteka.exceptions.InstanceNotFoundException;
import com.example.pd_pro_biblioteka.exceptions.SupabaseConnectionException;
import com.example.pd_pro_biblioteka.exceptions.JsonFileException;
import com.example.pd_pro_biblioteka.model.Admin;
import com.example.pd_pro_biblioteka.model.Ksiazka;
import com.example.pd_pro_biblioteka.model.Uzytkownik;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;


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
    private static final String ID_UZYTKOWNIKA = "id_uzytkownika";
    private static final String SELECT = "select";
    private static final String DATA_WYPOZYCZENIA = "Data_Wypozyczenia";
    private static final String TERMIN_ODDANIA = "Termin_Oddania";
    private static final String MFA_ENABLED = "Mfa_Enabled";
    private static final String DATA_ODDANIA = "Data_Oddania";
    private static final String TERMIN_ZAPLATY = "Termin_Zaplaty";
    private static final String DATA_URODZENIA = "Data_Urodzenia";
    private static final String ADRES = "Adres";
    private static final String KWOTA = "Kwota";
    private static final String MFA_SECRET = "Mfa_Secret";
    private static final String DATA_WYDANIA_KARY = "Data_Wydania_Kary";
    private static final String EMAIL = "Email";
    private static final String TYTUL = "Tytul";
    private static final String DATA_WYDANIA = "Data_Wydania";
    private static final String ROK_URODZENIA = "Rok_Urodzenia";
    private static final String GATUNEK = "Gatunek";
    private static final String ID_KSIAZKI = "id_ksiazki";


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
        return postData(PLACOWKA, Map.of(ADRES, adres));
    }

    public String getWypozyczenia() {
        return fetchData(WYPOZYCZENIA, "*");
    }

    public String addWypozyczenie(String dataWypozyczenia, String dataOddania, String terminOddania, int idKsiazki, int idUzytkownika) {
        Map<String, Object> data = new HashMap<>();

        if (dataWypozyczenia != null) {
            data.put(DATA_WYPOZYCZENIA, dataWypozyczenia);
        }
        if (dataOddania != null) {
            data.put(DATA_ODDANIA, dataOddania);
        }
        data.put(TERMIN_ODDANIA, terminOddania);
        data.put(ID_KSIAZKI, idKsiazki);
        data.put(ID_UZYTKOWNIKA, idUzytkownika);

        return postData(WYPOZYCZENIA, data);
    }

    public String getKary() {
        return fetchData("Kary", "*");
    }

    public String getKaryUID(int id)
    {
        return fetchDataUID("Kary", ID_UZYTKOWNIKA, id);
    }

    public String getWypozyczeniaUID(int id)
    {
        return fetchDataUID(WYPOZYCZENIA, ID_UZYTKOWNIKA, id);
    }

    public String getAdminAID(int id)
    {
        return fetchDataUID(ADMIN, "id", id);
    }

    public String addKara(double kwota, String dataWydaniaKary, String terminZaplaty, int idUzytkownika) {

        Map<String, Object> data = new HashMap<>();

        if (dataWydaniaKary != null) {
            data.put(DATA_WYDANIA_KARY, dataWydaniaKary);
        }

        data.put(KWOTA, kwota);
        data.put(TERMIN_ZAPLATY, terminZaplaty);
        data.put(ID_UZYTKOWNIKA, idUzytkownika);

        return postData("Kary", data);
    }
    public String getKsiazki() { return fetchData(KSIAZKA, "*");
    }

    public String addKsiazka(String tytul, String gatunek, String dataWydania, int idAutora, int idPlacowki) {
        return postData(KSIAZKA, Map.of(
                TYTUL, tytul,
                GATUNEK, gatunek,
                DATA_WYDANIA, dataWydania,
                ID_AUTORA, idAutora,
                ID_PLACOWKI, idPlacowki
        ));
    }

    public String getUzytkownicy() {
        return fetchData(UZYTKOWNIK, "*");
    }

    public String getUzytkownicyLogin(String login1, String password) {return fetchDatalogin(UZYTKOWNIK, "*", login1, password);}

    public String getAdminLogin(String login1, String password) {return fetchDatalogin(ADMIN, "*", login1, password);}

    private String safeLike(String column, String value) {
        if (value.matches("[a-zA-Z0-9ąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\s.-]{1,100}")) {
            return column + ".like.*" + value + "*";
        }
        throw new IllegalArgumentException("Invalid characters in value: " + value);
    }

    public String getKsiazkaFiltr(Integer id, String tytul, String gatunek, String dataWydania, String autorImie, String autorNazwisko, Integer idPlacowki)
    {
            StringBuilder kstatement = new StringBuilder("(");
            if (id != null) kstatement.append(safeLike("id", String.valueOf(id))).append(",");
            if (tytul != null) kstatement.append(safeLike(TYTUL, tytul)).append(",");
            if (gatunek != null) kstatement.append(safeLike(GATUNEK, gatunek)).append(",");
            if (dataWydania != null) kstatement.append(safeLike(DATA_WYDANIA, dataWydania)).append(",");
            if (idPlacowki != null) kstatement.append(safeLike(ID_PLACOWKI, String.valueOf(idPlacowki))).append(",");

            if (kstatement.length() > 1) kstatement.setLength(kstatement.length() - 1);
            kstatement.append(")");

            StringBuilder astatement = new StringBuilder("(");
            if (autorImie != null) astatement.append(safeLike("Imie", autorImie)).append(",");
            if (autorNazwisko != null) astatement.append(safeLike(NAZWISKO, autorNazwisko)).append(",");
            if (astatement.length() > 1) astatement.setLength(astatement.length() - 1);
            astatement.append(")");

            if (kstatement.toString().equals("()")) kstatement = new StringBuilder("(id.gt.0)");
            if (astatement.toString().equals("()")) astatement = new StringBuilder("(id.gt.0)");


        return fetchKsiazkaFiltr(kstatement.toString(), astatement.toString());

    }

    public String addUzytkownik(String imie, String nazwisko, String dataUrodzenia, String nazwaUzytkownika, String haslo, String email) {
        return postData(UZYTKOWNIK, Map.of(
                "Imie", imie,
                NAZWISKO, nazwisko,
                DATA_URODZENIA, dataUrodzenia,
                NAZWA_UZYTKOWNIKA, nazwaUzytkownika,
                HASLO, haslo,
                EMAIL, email
        ));
    }

    public String getAdmini() {
        return fetchData(ADMIN, "*");
    }

    public String addAdmin(String imie, String nazwisko, String nazwaUzytkownika, String haslo, int idPlacowki) {
        return postData(ADMIN, Map.of(
                "Imie", imie,
                NAZWISKO, nazwisko,
                NAZWA_UZYTKOWNIKA, nazwaUzytkownika,
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
                ROK_URODZENIA, rokUrodzenia
        ));
    }

    public String updatePlacowka(int id, String adres) {
        Map<String, Object> data = new HashMap<>();
        if (adres != null) data.put(ADRES, adres);
        return updateData(PLACOWKA, id, data);
    }

    public String updateWypozyczenie(int id, String dataWypozyczenia, String dataOddania, String terminOddania, Integer idKsiazki, Integer idUzytkownika) {
        Map<String, Object> data = new HashMap<>();
        if (dataWypozyczenia != null) data.put(DATA_WYPOZYCZENIA, dataWypozyczenia);
        if (dataOddania != null) data.put(DATA_ODDANIA, dataOddania);
        if (terminOddania != null) data.put(TERMIN_ODDANIA, terminOddania);
        if (idKsiazki != null) data.put(ID_KSIAZKI, idKsiazki);
        if (idUzytkownika != null) data.put(ID_UZYTKOWNIKA, idUzytkownika);
        return updateData(WYPOZYCZENIA, id, data);
    }

    public String updateKara(int id, Double kwota, String dataWydaniaKary, String terminZaplaty, Boolean czyZaplacono, Integer idUzytkownika) {
        Map<String, Object> data = new HashMap<>();
        if (kwota != null) data.put(KWOTA, kwota);
        if (dataWydaniaKary != null) data.put(DATA_WYDANIA_KARY, dataWydaniaKary);
        if (terminZaplaty != null) data.put(TERMIN_ZAPLATY, terminZaplaty);
        if (czyZaplacono != null) data.put("Czy_Zaplacono", czyZaplacono);
        if (idUzytkownika != null) data.put(ID_UZYTKOWNIKA, idUzytkownika);
        return updateData("Kary", id, data);
    }

    public String updateKsiazka(Ksiazka ksiazka) {
        Map<String, Object> data = new HashMap<>();
        if (ksiazka.getTytul() != null) data.put(TYTUL, ksiazka.getTytul());
        if (ksiazka.getGatunek() != null) data.put(GATUNEK, ksiazka.getGatunek());
        if (ksiazka.getDataWydania() != null) data.put(DATA_WYDANIA, ksiazka.getDataWydania());
        if (ksiazka.getDodano() != null) data.put("Dodano", ksiazka.getDodano());
        if (ksiazka.getIdautora() != null) data.put(ID_AUTORA, ksiazka.getIdautora());
        if (ksiazka.getIdplacowki() != null) data.put(ID_PLACOWKI, ksiazka.getIdplacowki());
        return updateData(KSIAZKA, ksiazka.getId(), data);
    }

    public String updateUzytkownik(Uzytkownik uzytkownik) {
        Map<String, Object> data = new HashMap<>();
        if (uzytkownik.getImie() != null) data.put("Imie", uzytkownik.getImie());
        if (uzytkownik.getNazwisko() != null) data.put(NAZWISKO, uzytkownik.getNazwisko());
        if (uzytkownik.getDataUrodzenia() != null) data.put(DATA_URODZENIA, uzytkownik.getDataUrodzenia());
        if (uzytkownik.getNazwaUzytkownika() != null) data.put(NAZWA_UZYTKOWNIKA, uzytkownik.getNazwaUzytkownika());
        if (uzytkownik.getHaslo() != null) data.put(HASLO, uzytkownik.getHaslo());
        if (uzytkownik.getEmail() != null) data.put(EMAIL, uzytkownik.getEmail());
        if (uzytkownik.getZablokowany() != null) data.put("Zablokowany", uzytkownik.getZablokowany());
        if (uzytkownik.getMfaEnabled() != null) data.put(MFA_ENABLED, uzytkownik.getMfaEnabled());
        if (uzytkownik.getMfaSecret() != null) data.put(MFA_SECRET, uzytkownik.getMfaSecret());
        return updateData(UZYTKOWNIK, uzytkownik.getId(), data);
    }

    public String updateAdmin(Admin admin) {
        Map<String, Object> data = new HashMap<>();
        if (admin.getImie() != null) data.put("Imie", admin.getImie());
        if (admin.getNazwisko() != null) data.put(NAZWISKO, admin.getNazwisko());
        if (admin.getNazwaUzytkownika() != null) data.put(NAZWA_UZYTKOWNIKA, admin.getNazwaUzytkownika());
        if (admin.getHaslo() != null) data.put(HASLO, admin.getHaslo());
        if (admin.getIdplacowki() != null) data.put(ID_PLACOWKI, admin.getIdplacowki());
        if (admin.getMfaEnabled() != null) data.put(MFA_ENABLED, admin.getMfaEnabled());
        if (admin.getMfaSecret() != null) data.put(MFA_SECRET, admin.getMfaSecret());
        return updateData(ADMIN, admin.getId(), data);
    }

    public String updateAutor(int id, String imie, String nazwisko, Integer rokUrodzenia) {
        Map<String, Object> data = new HashMap<>();
        if (imie != null) data.put("Imie", imie);
        if (nazwisko != null) data.put(NAZWISKO, nazwisko);
        if (rokUrodzenia != null) data.put(ROK_URODZENIA, rokUrodzenia);
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
                            .queryParam(SELECT, columns)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
        catch (Exception e)
    {
        throw new SupabaseConnectionException("Failed to fetch " + table + ": ", e);
    }
    }

    private String fetchDataUID(String table, String filtr, Integer id) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/" + table)
                            .queryParam(SELECT, "*")
                            .queryParam(filtr, "eq." + id)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
        catch (Exception e)
        {
            throw new SupabaseConnectionException("Failed to fetch " + table + " by id: ", e);
        }
    }

private String fetchKsiazkaFiltr(String kstatement, String astatement) {
    try {
        String data = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + KSIAZKA)
                        .queryParam("and", kstatement)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String autor = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + AUTORZY)
                        .queryParam("id", "gt.0")
                        .queryParam("and", astatement)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        List<Integer> autorIds = extractAutorIds(autor);
        return filterKsiazkiByAutor(data, autorIds);
    } catch (JsonFileException e) {
        throw e;
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
            throw new JsonFileException("Failed work on JSON ", e);
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
            throw new JsonFileException("Failed work on JSON ", e);
        }
        return filteredArray.toString();
    }

    private boolean isSafe(String input) {
        return input != null && input.matches("[\\w@.]{1,100}");
    }

    private String fetchDatalogin(String table, String columns, String logindata, String passworddata) {
        try {
            if (!isSafe(logindata) || !isSafe(passworddata)) {
                throw new IllegalArgumentException("Invalid login or password format.");
            }
            String login = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/" + table)
                            .queryParam(SELECT, columns)
                            .queryParam(NAZWA_UZYTKOWNIKA, "eq." + logindata)
                            .queryParam(HASLO, "eq." + passworddata)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            if(login == null)
            {
                throw new InstanceNotFoundException(table, ": brak uzytkownika ");
            }
        return login;
        } catch (InstanceNotFoundException | IllegalArgumentException e) {
            throw e;
        }  catch (Exception e) {
            throw new SupabaseConnectionException("Failed to fetch user: ", e);
        }
    }

    private static final Pattern SAFE_TEXT_PATTERN = Pattern.compile("[\\wąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\s@.+\\-:,]{1,200}");


    private boolean isSafeValue(Object value) {
        if (value instanceof String string) {
            return SAFE_TEXT_PATTERN.matcher(string).matches();
        }
        return true;
    }

    private Map<String, Object> validateRequestBody(Map<String, Object> requestBody) {
        Map<String, Object> safeMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : requestBody.entrySet()) {
            Object value = entry.getValue();
            if (isSafeValue(value)) {
                safeMap.put(entry.getKey(), value);
            } else {
                throw new IllegalArgumentException("Unsafe value for field: " + entry.getKey());
            }
        }
        return safeMap;
    }


    private String postData(String table, Map<String, Object> requestBody) {
        try {
            Map<String, Object> safeBody = validateRequestBody(requestBody);
            return webClient.post()
                    .uri("/" + table)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(safeBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (IllegalArgumentException e) {
            throw e;
        }
        catch (Exception e) {
            throw new SupabaseConnectionException("Failed to post to table " + table + ": ", e);
        }
    }

    private String updateData(String table, int id, Map<String, Object> requestBody) {
        try {
            Map<String, Object> safeBody = validateRequestBody(requestBody);
            return webClient.patch()
                    .uri(uriBuilder -> uriBuilder.path("/" + table)
                            .queryParam("id", "eq." + id).build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(safeBody)
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
            throw new SupabaseConnectionException("Failed to delete id: " + id + " in table: " + table + ": ", e);
        }
    }

}
