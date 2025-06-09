package com.example.pdprobiblioteka.service;

import com.example.pdprobiblioteka.exceptions.EmailSendException;
import com.example.pdprobiblioteka.exceptions.InstanceNotFoundException;
import com.example.pdprobiblioteka.exceptions.JsonFileException;
import com.example.pdprobiblioteka.exceptions.SupabaseConnectionException;
import com.example.pdprobiblioteka.model.Admin;
import com.example.pdprobiblioteka.model.Ksiazka;
import com.example.pdprobiblioteka.model.Uzytkownik;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Klasa odpowiedzialna za komunikację z bazą danych Supabase poprzez WebClient. Umożliwia
 * wykonywanie operacji CRUD na różnych encjach takich jak Użytkownicy, Książki, Wypożyczenia, Kary
 * itp. Zawiera również integrację z serwisem EmailService do wysyłania wiadomości e-mail.
 */
@Service
public class SupabaseClient {

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
  private static final String SLADMIN = "/Admin";
  private static final String SLUZYTKOWNIK = "/Uzytkownik";
  private static final Pattern SAFE_TEXT_PATTERN = Pattern.compile(
      "[\\wąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\s@.+\\-:,$/]{1,200}");
  private final WebClient webClient;
  private final EmailService emailService;

  private final PasswordEncoder passwordEncoder;

  /**
   * Tworzy nową instancję klienta Supabase.
   *
   * @param webClientBuilder builder do tworzenia WebClienta z odpowiednimi nagłówkami
   * @param emailService serwis odpowiedzialny za wysyłanie e-maili
   */
  public SupabaseClient(WebClient.Builder webClientBuilder,
      EmailService emailService, PasswordEncoder passwordEncoder) {

    String supabaseUrl = System.getenv("SUPABASE_URL");
    String supabaseKey = System.getenv("SUPABASE_KEY");
    String supabaseKey2 = System.getenv("SUPABASE_KEY2");

    supabaseUrl = supabaseUrl.trim() + "/rest/v1";
    supabaseKey = supabaseKey.trim();
    supabaseKey2 = supabaseKey2.trim();

    this.passwordEncoder = passwordEncoder;
    try {
      this.webClient = webClientBuilder
          .baseUrl(supabaseUrl)
          .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + supabaseKey)
          .defaultHeader("apikey", supabaseKey2)
          .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .build();
      this.emailService = emailService;
    } catch (Exception e) {
      throw new SupabaseConnectionException("Failed to connect to Supabase: ", e);
    }
  }

  /**
   * Pobiera listę placówek z bazy danych.
   *
   * @return dane zawierające identyfikator oraz adres placówki
   */
  public String getPlacowki() {
    return fetchData(PLACOWKA, "id, Adres");
  }

  /**
   * Dodaje nową placówkę do bazy danych.
   *
   * @param adres adres nowej placówki
   * @return odpowiedź z serwera po dodaniu danych
   */
  public String addPlacowka(String adres) {
    return postData(PLACOWKA, Map.of(ADRES, adres));
  }


  /**
   * Pobiera wszystkie wypożyczenia z bazy danych.
   *
   * @return dane zawierające informacje o wypożyczeniach
   */
  public String getWypozyczenia() {
    return fetchData(WYPOZYCZENIA, "*");
  }

  /**
   * Dodaje nowe wypożyczenie książki.
   *
   * @param dataWypozyczenia data wypożyczenia
   * @param dataOddania data oddania (opcjonalna)
   * @param terminOddania planowany termin oddania
   * @param idKsiazki identyfikator książki
   * @param idUzytkownika identyfikator użytkownika
   * @return odpowiedź z serwera po dodaniu danych
   */
  public String addWypozyczenie(String dataWypozyczenia, String dataOddania, String terminOddania,
      int idKsiazki, int idUzytkownika) {
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


  /**
   * Pobiera wszystkie kary z bazy danych.
   *
   * @return dane zawierające informacje o karach
   */
  public String getKary() {
    return fetchData("Kary", "*");
  }

  /**
   * Pobiera kary przypisane do konkretnego użytkownika.
   *
   * @param id identyfikator użytkownika
   * @return dane JSON z karami danego użytkownika
   */
  public String getKaryuid(int id) {
    return fetchDatauid("Kary", ID_UZYTKOWNIKA, Integer.toString(id));
  }

  /**
   * Pobiera wypożyczenia przypisane do konkretnego użytkownika.
   *
   * @param id identyfikator użytkownika
   * @return dane JSON z wypożyczeniami danego użytkownika
   */
  public String getWypozyczeniauid(int id) {
    return fetchDatauid(WYPOZYCZENIA, ID_UZYTKOWNIKA, Integer.toString(id));
  }

  /**
   * Wysyła e-mail do użytkownika z nowym hasłem, jeżeli adres istnieje w bazie.
   *
   * @param email adres e-mail użytkownika
   * @return wynik operacji w formacie JSON lub komunikat błędu
   */
  public String putResetpasswordbyemail(String email) {
    String request = fetchDatauid(UZYTKOWNIK, EMAIL, email);

    if (request == null) {
      return "Error while sending new password, null request";
    }

    try {
      if (!request.equals("[]")) {
        emailService.sendNewPassword(email);
      }
    } catch (EmailSendException e) {
      return "Error while sending new password";
    }

    return request;
  }


  /**
   * Pobiera dane administratora na podstawie identyfikatora.
   *
   * @param id identyfikator administratora
   * @return dane JSON zawierające informacje o administratorze
   */
  public String getAdminaid(int id) {
    return fetchDatauid(ADMIN, "id", Integer.toString(id));
  }

  /**
   * Dodaje nową karę dla użytkownika.
   *
   * @param kwota wysokość kary
   * @param dataWydaniaKary data wystawienia kary
   * @param terminZaplaty termin zapłaty
   * @param idUzytkownika identyfikator użytkownika
   * @param opis opcjonalny opis kary
   * @return odpowiedź z serwera po dodaniu danych
   */
  public String addKara(double kwota, String dataWydaniaKary, String terminZaplaty,
      int idUzytkownika, String opis) {

    Map<String, Object> data = new HashMap<>();

    if (dataWydaniaKary != null) {
      data.put(DATA_WYDANIA_KARY, dataWydaniaKary);
    }

    data.put(KWOTA, kwota);
    data.put(TERMIN_ZAPLATY, terminZaplaty);
    data.put(ID_UZYTKOWNIKA, idUzytkownika);
    if (opis != null) {
      data.put("opis", opis);
    }

    return postData("Kary", data);
  }

  /**
   * Pobiera wszystkie książki z bazy danych.
   *
   * @return dane zawierające informacje o książkach
   */
  public String getKsiazki() {
    return fetchData(KSIAZKA, "*");
  }

  /**
   * Dodaje nową książkę do bazy danych.
   *
   * @param tytul tytuł książki
   * @param gatunek gatunek literacki
   * @param dataWydania data wydania książki
   * @param idAutora identyfikator autora
   * @param idPlacowki identyfikator placówki, do której przypisana jest książka
   * @return odpowiedź z serwera po dodaniu książki
   */
  public String addKsiazka(String tytul, String gatunek, String dataWydania, int idAutora,
      int idPlacowki) {
    return postData(KSIAZKA, Map.of(
        TYTUL, tytul,
        GATUNEK, gatunek,
        DATA_WYDANIA, dataWydania,
        ID_AUTORA, idAutora,
        ID_PLACOWKI, idPlacowki
    ));
  }

  /**
   * Pobiera wszystkich użytkowników.
   *
   * @return dane z użytkownikami
   */
  public String getUzytkownicy() {
    return fetchData(UZYTKOWNIK, "*");
  }

  /**
   * Logowanie użytkownika po nazwie i haśle.
   *
   * @param login1 nazwa użytkownika
   * @param password hasło
   * @return dane JSON użytkownika, jeśli dane logowania są poprawne
   */
  public String getUzytkownicyLogin(String login1, String password) {
    return fetchDatalogin(UZYTKOWNIK, "*", login1, password);
  }


  /**
   * Logowanie administratora po nazwie i haśle.
   *
   * @param login1 login administratora
   * @param password hasło
   * @return dane administratora, jeśli dane logowania są poprawne
   */
  public String getAdminLogin(String login1, String password) {
    return fetchDatalogin(ADMIN, "*", login1, password);
  }

  /**
   * Sprawdzanie bezpieczenstwa przekazanej wartości w kontekście SQL injection wykorzystywana w
   * filtrowaniu książek.
   *
   * @param column kolumna w ktorej znajduja się dane
   * @param value wartość sprawdzanego pola
   * @return kolumna oraz wartość z wykorzystaniem like w celu filtrowania
   */
  private String safeLike(String column, String value) {
    if (value.matches("[a-zA-Z0-9ąćęłńóśźżĄĆĘŁŃÓŚŹŻ\\s.-]{1,100}")) {
      return column + ".like.*" + value + "*";
    }
    throw new IllegalArgumentException("Invalid characters in value: " + value);
  }

  /**
   * Wyszukuje książki według różnych filtrów.
   *
   * @param id identyfikator książki
   * @param tytul tytuł książki
   * @param gatunek gatunek literacki
   * @param dataWydania data wydania
   * @param autorImie imię autora
   * @param autorNazwisko nazwisko autora
   * @param idPlacowki identyfikator placówki
   * @return dane JSON książek pasujących do filtrów
   */
  public String getKsiazkaFiltr(Integer id, String tytul, String gatunek, String dataWydania,
      String autorImie, String autorNazwisko, Integer idPlacowki) {
    StringBuilder kstatement = new StringBuilder("(");
    if (id != null) {
      kstatement.append(safeLike("id", String.valueOf(id))).append(",");
    }
    if (tytul != null) {
      kstatement.append(safeLike(TYTUL, tytul)).append(",");
    }
    if (gatunek != null) {
      kstatement.append(safeLike(GATUNEK, gatunek)).append(",");
    }
    if (dataWydania != null) {
      kstatement.append(safeLike(DATA_WYDANIA, dataWydania)).append(",");
    }
    if (idPlacowki != null) {
      kstatement.append(safeLike(ID_PLACOWKI, String.valueOf(idPlacowki))).append(",");
    }

    if (kstatement.length() > 1) {
      kstatement.setLength(kstatement.length() - 1);
    }
    kstatement.append(")");

    StringBuilder astatement = new StringBuilder("(");
    if (autorImie != null) {
      astatement.append(safeLike("Imie", autorImie)).append(",");
    }
    if (autorNazwisko != null) {
      astatement.append(safeLike(NAZWISKO, autorNazwisko)).append(",");
    }
    if (astatement.length() > 1) {
      astatement.setLength(astatement.length() - 1);
    }
    astatement.append(")");

    if (kstatement.toString().equals("()")) {
      kstatement = new StringBuilder("(id.gt.0)");
    }
    if (astatement.toString().equals("()")) {
      astatement = new StringBuilder("(id.gt.0)");
    }

    return fetchKsiazkaFiltr(kstatement.toString(), astatement.toString());

  }

  /**
   * Dodaje nowego użytkownika do systemu.
   *
   * @param imie Imię użytkownika
   * @param nazwisko Nazwisko użytkownika
   * @param dataUrodzenia Data urodzenia użytkownika
   * @param nazwaUzytkownika Nazwa użytkownika
   * @param haslo Hasło użytkownika
   * @param email Adres e-mail użytkownika
   * @return Odpowiedź z serwera po dodaniu użytkownika
   */
  public String addUzytkownik(String imie, String nazwisko, String dataUrodzenia,
      String nazwaUzytkownika, String haslo, String email) {
    String hashedPassword = passwordEncoder.encode(haslo);
    return postData(UZYTKOWNIK, Map.of(
        "Imie", imie,
        NAZWISKO, nazwisko,
        DATA_URODZENIA, dataUrodzenia,
        NAZWA_UZYTKOWNIKA, nazwaUzytkownika,
        HASLO, hashedPassword,
        EMAIL, email
    ));
  }

  /**
   * Pobiera listę wszystkich administratorów.
   *
   * @return Odpowiedź z serwera zawierająca administratorów
   */
  public String getAdmini() {
    return fetchData(ADMIN, "*");
  }

  /**
   * Dodaje nowego administratora do systemu.
   *
   * @param imie Imię administratora
   * @param nazwisko Nazwisko administratora
   * @param nazwaUzytkownika Nazwa użytkownika
   * @param haslo Hasło administratora
   * @param idPlacowki ID placówki przypisanej do administratora
   * @return Odpowiedź z serwera po dodaniu administratora
   */
  public String addAdmin(String imie, String nazwisko, String nazwaUzytkownika, String haslo,
      int idPlacowki) {
    return postData(ADMIN, Map.of(
        "Imie", imie,
        NAZWISKO, nazwisko,
        NAZWA_UZYTKOWNIKA, nazwaUzytkownika,
        HASLO, haslo,
        ID_PLACOWKI, idPlacowki
    ));
  }

  /**
   * Pobiera listę wszystkich autorów.
   *
   * @return Odpowiedź z serwera zawierająca listę autorów
   */
  public String getAutorzy() {
    return fetchData(AUTORZY, "*");
  }

  /**
   * Dodaje nowego autora do systemu.
   *
   * @param imie Imię autora
   * @param nazwisko Nazwisko autora
   * @param rokUrodzenia Rok urodzenia autora
   * @return Odpowiedź z serwera po dodaniu autora
   */
  public String addAutor(String imie, String nazwisko, Integer rokUrodzenia) {
    return postData(AUTORZY, Map.of(
        "Imie", imie,
        NAZWISKO, nazwisko,
        ROK_URODZENIA, rokUrodzenia
    ));
  }

  /**
   * Aktualizuje dane placówki.
   *
   * @param id ID placówki do zaktualizowania
   * @param adres Nowy adres placówki
   * @return Odpowiedź z serwera po aktualizacji
   */
  public String updatePlacowka(int id, String adres) {
    Map<String, Object> data = new HashMap<>();
    if (adres != null) {
      data.put(ADRES, adres);
    }
    return updateData(PLACOWKA, id, data);
  }

  /**
   * Aktualizuje dane wypożyczenia.
   *
   * @param id ID wypożyczenia
   * @param dataWypozyczenia Data wypożyczenia
   * @param dataOddania Data oddania
   * @param terminOddania Termin oddania
   * @param idKsiazki ID książki
   * @param idUzytkownika ID użytkownika
   * @return Odpowiedź z serwera po aktualizacji
   */
  public String updateWypozyczenie(int id, String dataWypozyczenia, String dataOddania,
      String terminOddania, Integer idKsiazki, Integer idUzytkownika) {
    Map<String, Object> data = new HashMap<>();
    if (dataWypozyczenia != null) {
      data.put(DATA_WYPOZYCZENIA, dataWypozyczenia);
    }
    if (dataOddania != null) {
      data.put(DATA_ODDANIA, dataOddania);
    }
    if (terminOddania != null) {
      data.put(TERMIN_ODDANIA, terminOddania);
    }
    if (idKsiazki != null) {
      data.put(ID_KSIAZKI, idKsiazki);
    }
    if (idUzytkownika != null) {
      data.put(ID_UZYTKOWNIKA, idUzytkownika);
    }
    return updateData(WYPOZYCZENIA, id, data);
  }


  /**
   * Aktualizuje dane kary.
   *
   * @param id ID kary
   * @param kwota Kwota kary
   * @param dataWydaniaKary Data wystawienia kary
   * @param terminZaplaty Termin zapłaty kary
   * @param czyZaplacono Informacja, czy kara została zapłacona
   * @param idUzytkownika ID użytkownika
   * @param opis Opis kary
   * @return Odpowiedź z serwera po aktualizacji
   */
  public String updateKara(int id, Double kwota, String dataWydaniaKary, String terminZaplaty,
      Boolean czyZaplacono, Integer idUzytkownika, String opis) {
    Map<String, Object> data = new HashMap<>();
    if (kwota != null) {
      data.put(KWOTA, kwota);
    }
    if (dataWydaniaKary != null) {
      data.put(DATA_WYDANIA_KARY, dataWydaniaKary);
    }
    if (terminZaplaty != null) {
      data.put(TERMIN_ZAPLATY, terminZaplaty);
    }
    if (czyZaplacono != null) {
      data.put("Czy_Zaplacono", czyZaplacono);
    }
    if (idUzytkownika != null) {
      data.put(ID_UZYTKOWNIKA, idUzytkownika);
    }
    if (opis != null) {
      data.put("opis", opis);
    }
    return updateData("Kary", id, data);
  }

  /**
   * Aktualizuje dane książki.
   *
   * @param ksiazka Obiekt książki zawierający dane do aktualizacji
   * @return Odpowiedź z serwera po aktualizacji książki
   */
  public String updateKsiazka(Ksiazka ksiazka) {
    Map<String, Object> data = new HashMap<>();
    if (ksiazka.getTytul() != null) {
      data.put(TYTUL, ksiazka.getTytul());
    }
    if (ksiazka.getGatunek() != null) {
      data.put(GATUNEK, ksiazka.getGatunek());
    }
    if (ksiazka.getDataWydania() != null) {
      data.put(DATA_WYDANIA, ksiazka.getDataWydania());
    }
    if (ksiazka.getDodano() != null) {
      data.put("Dodano", ksiazka.getDodano());
    }
    if (ksiazka.getIdautora() != null) {
      data.put(ID_AUTORA, ksiazka.getIdautora());
    }
    if (ksiazka.getIdplacowki() != null) {
      data.put(ID_PLACOWKI, ksiazka.getIdplacowki());
    }
    return updateData(KSIAZKA, ksiazka.getId(), data);
  }

  /**
   * Aktualizuje dane użytkownika.
   *
   * @param uzytkownik Obiekt użytkownika zawierający dane do aktualizacji
   * @return Odpowiedź z serwera po aktualizacji użytkownika
   */
  public String updateUzytkownik(Uzytkownik uzytkownik) {
    Map<String, Object> data = new HashMap<>();
    if (uzytkownik.getImie() != null) {
      data.put("Imie", uzytkownik.getImie());
    }
    if (uzytkownik.getNazwisko() != null) {
      data.put(NAZWISKO, uzytkownik.getNazwisko());
    }
    if (uzytkownik.getDataUrodzenia() != null) {
      data.put(DATA_URODZENIA, uzytkownik.getDataUrodzenia());
    }
    if (uzytkownik.getNazwaUzytkownika() != null) {
      data.put(NAZWA_UZYTKOWNIKA, uzytkownik.getNazwaUzytkownika());
    }
    if (uzytkownik.getHaslo() != null) {
      data.put(HASLO, uzytkownik.getHaslo());
    }
    if (uzytkownik.getEmail() != null) {
      data.put(EMAIL, uzytkownik.getEmail());
    }
    if (uzytkownik.getZablokowany() != null) {
      data.put("Zablokowany", uzytkownik.getZablokowany());
    }
    if (uzytkownik.getMfaEnabled() != null) {
      data.put(MFA_ENABLED, uzytkownik.getMfaEnabled());
    }
    if (uzytkownik.getMfaSecret() != null) {
      data.put(MFA_SECRET, uzytkownik.getMfaSecret());
    }
    return updateData(UZYTKOWNIK, uzytkownik.getId(), data);
  }

  /**
   * Aktualizuje dane administratora na podstawie przekazanego admina. Aktualizowane są tylko te
   * pola, które nie są nullem.
   *
   * @param admin obiekt administratora zawierający zaktualizowane dane.
   * @return wynik operacji aktualizacji jako String.
   */

  public String updateAdmin(Admin admin) {
    Map<String, Object> data = new HashMap<>();
    if (admin.getImie() != null) {
      data.put("Imie", admin.getImie());
    }
    if (admin.getNazwisko() != null) {
      data.put(NAZWISKO, admin.getNazwisko());
    }
    if (admin.getNazwaUzytkownika() != null) {
      data.put(NAZWA_UZYTKOWNIKA, admin.getNazwaUzytkownika());
    }
    if (admin.getHaslo() != null) {
      data.put(HASLO, admin.getHaslo());
    }
    if (admin.getIdplacowki() != null) {
      data.put(ID_PLACOWKI, admin.getIdplacowki());
    }
    if (admin.getMfaEnabled() != null) {
      data.put(MFA_ENABLED, admin.getMfaEnabled());
    }
    if (admin.getMfaSecret() != null) {
      data.put(MFA_SECRET, admin.getMfaSecret());
    }
    return updateData(ADMIN, admin.getId(), data);
  }

  /**
   * Aktualizuje dane autora o określonym identyfikatorze. Aktualizowane są tylko pola niebędące
   * nullem.
   *
   * @param id identyfikator autora.
   * @param imie nowe imię autora (opcjonalne).
   * @param nazwisko nowe nazwisko autora (opcjonalne).
   * @param rokUrodzenia nowy rok urodzenia autora (opcjonalny).
   * @return wynik operacji aktualizacji jako String.
   */

  public String updateAutor(int id, String imie, String nazwisko, Integer rokUrodzenia) {
    Map<String, Object> data = new HashMap<>();
    if (imie != null) {
      data.put("Imie", imie);
    }
    if (nazwisko != null) {
      data.put(NAZWISKO, nazwisko);
    }
    if (rokUrodzenia != null) {
      data.put(ROK_URODZENIA, rokUrodzenia);
    }
    return updateData(AUTORZY, id, data);
  }

  /**
   * Usuwa rekord z tabeli Autor na podstawie identyfikatora.
   *
   * @param id identyfikator rekordu do usunięcia.
   * @return wynik operacji usuwania jako String.
   */

  public String deleteAutor(int id) {
    return deleteData(AUTORZY, id);
  }

  /**
   * Usuwa rekord z tabeli Admin na podstawie identyfikatora.
   *
   * @param id identyfikator rekordu do usunięcia.
   * @return wynik operacji usuwania jako String.
   */

  public String deleteAdmin(int id) {
    return deleteData(ADMIN, id);
  }

  /**
   * Usuwa rekord z tabeli Uzytkownik na podstawie identyfikatora.
   *
   * @param id identyfikator rekordu do usunięcia.
   * @return wynik operacji usuwania jako String.
   */
  public String deleteUzytkownik(int id) {
    return deleteData(UZYTKOWNIK, id);
  }

  /**
   * Usuwa rekord z tabeli Ksiazka na podstawie identyfikatora.
   *
   * @param id identyfikator rekordu do usunięcia.
   * @return wynik operacji usuwania jako String.
   */
  public String deleteKsiazka(int id) {
    return deleteData(KSIAZKA, id);
  }

  /**
   * Usuwa rekord z tabeli Kary na podstawie identyfikatora.
   *
   * @param id identyfikator rekordu do usunięcia.
   * @return wynik operacji usuwania jako String.
   */
  public String deleteKara(int id) {
    return deleteData("Kary", id);
  }

  /**
   * Usuwa rekord z tabeli Placowka na podstawie identyfikatora.
   *
   * @param id identyfikator rekordu do usunięcia.
   * @return wynik operacji usuwania jako String.
   */
  public String deletePlacowka(int id) {
    return deleteData(PLACOWKA, id);
  }

  /**
   * Usuwa rekord z tabeli Wypozyczenia na podstawie identyfikatora.
   *
   * @param id identyfikator rekordu do usunięcia.
   * @return wynik operacji usuwania jako String.
   */
  public String deleteWypozyczenie(int id) {
    return deleteData(WYPOZYCZENIA, id);
  }

  /**
   * Zaplanowane zadanie dodające automatyczne kary za przetrzymanie książek. Sprawdza wszystkie
   * wypożyczenia i dodaje karę użytkownikowi, jeśli termin oddania minął, a książka nie została
   * jeszcze zwrócona. Uruchamiane codziennie o 17:00.
   */
  @Scheduled(cron = "0 00 17 * * ?")
  public void scheduledKara() {
    try {
      String wypozyczeniaData = fetchData(WYPOZYCZENIA, "*");
      JSONArray wypozyczeniaArray = new JSONArray(wypozyczeniaData);

      for (int i = 0; i < wypozyczeniaArray.length(); i++) {
        JSONObject obj = wypozyczeniaArray.getJSONObject(i);
        LocalDate termin = LocalDate.parse(obj.getString(TERMIN_ODDANIA));
        Object dataOddania = obj.opt(DATA_ODDANIA);
        int userId = obj.getInt(ID_UZYTKOWNIKA);

        if (termin.isBefore(LocalDate.now()) && "null".equals(dataOddania.toString())) {
          addKara(100, String.valueOf(LocalDate.now()), obj.getString(DATA_WYPOZYCZENIA),
              userId, "Automatyczna kara");
        }
      }

    } catch (Exception e) {
      throw new SupabaseConnectionException("Failed to post kara", e);
    }
  }

  /**
   * Pobiera dane z określonej tabeli i wskazanych kolumn.
   *
   * @param table nazwa tabeli.
   * @param columns kolumny do pobrania
   * @return wynik zapytania w formacie JSON jako String.
   * @throws SupabaseConnectionException w przypadku błędu połączenia.
   */

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
    } catch (Exception e) {
      throw new SupabaseConnectionException("Failed to fetch " + table + ": ", e);
    }
  }

  /**
   * Pobiera dane z tabeli na podstawie określonego filtru, wykorzystywane do pobierania przez klucz
   * obcy.
   *
   * @param table nazwa tabeli.
   * @param filtr nazwa parametru filtru
   * @param id wartość filtru.
   * @return wynik zapytania w formacie JSON jako String.
   */
  private String fetchDatauid(String table, String filtr, String id) {
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
    } catch (Exception e) {
      throw new SupabaseConnectionException("Failed to fetch " + table + " by id: ", e);
    }
  }

  /**
   * Pobiera książki oraz autorów spełniających przekazane warunki i filtruje książki, których
   * autorzy są zgodni z wynikami filtrowania.
   *
   * @param kstatement warunek zapytania dla książek
   * @param astatement warunek zapytania dla autorów.
   * @return przefiltrowana lista książek w formacie JSON jako String.
   */
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

  /**
   * Wydobywa identyfikatory autorów z przekazanego JSON-a.
   *
   * @param autorzyData dane JSON zawierające listę autorów.
   * @return lista identyfikatorów autorów.
   */
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

  /**
   * Filtruje książki na podstawie listy identyfikatorów autorów.
   *
   * @param ksiazkiData dane książek w formacie JSON.
   * @param autorIds lista dopuszczalnych identyfikatorów autorów.
   * @return przefiltrowana lista książek w formacie JSON.
   */
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

  /**
   * Sprawdzanie, czy dane są bezpieczne porównując z patternem.
   *
   * @param input sprawdzane dane
   * @return wartosc boolean oznaczajaca czy tekst jest bezpieczny
   */
  private boolean isSafe(String input) {
    return input != null && SAFE_TEXT_PATTERN.matcher(input).matches();
  }

  /**
   * Pobiera dane użytkownika na podstawie loginu i hasła. Waliduje dane wejściowe pod kątem
   * bezpieczeństwa.
   *
   * @param table nazwa tabeli.
   * @param columns kolumny do pobrania.
   * @param logindata nazwa użytkownika.
   * @param passworddata hasło.
   * @return dane użytkownika w formacie JSON.
   * @throws IllegalArgumentException jeśli dane wejściowe są niebezpieczne.
   * @throws InstanceNotFoundException jeśli użytkownik nie istnieje.
   */
  private String fetchDatalogin(String table, String columns, String logindata,
      String passworddata) {
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

      if (login == null) {
        throw new InstanceNotFoundException(table, ": brak uzytkownika ");
      }
      return login;
    } catch (InstanceNotFoundException | IllegalArgumentException e) {
      throw e;
    } catch (Exception e) {
      throw new SupabaseConnectionException("Failed to fetch user: ", e);
    }
  }

  /**
   * Sprawdzanie, czy obiekt jest bezpieczny porównując go patternem.
   *
   * @param value sprawdzany obiekt
   * @return wartosc boolean oznaczajaca czy tekst jest bezpieczny
   */
  private boolean isSafeValue(Object value) {
    if (value instanceof String string) {
      return SAFE_TEXT_PATTERN.matcher(string).matches();
    }
    return true;
  }

  /**
   * Walidacja bezpieczeństwa ciała requesta.
   *
   * @param requestBody ciała requesta
   * @return zwracane bezpieczne ciało
   */
  private Map<String, Object> validateRequestBody(Map<String, Object> requestBody) {
    Map<String, Object> safeMap = new HashMap<>();
    for (Map.Entry<String, Object> entry : requestBody.entrySet()) {
      Object value = entry.getValue();
      if (isSafeValue(value)) {
        safeMap.put(entry.getKey(), value);
      } else {
        throw new IllegalArgumentException("Unsafe value for field: "
            + entry.getKey() + value);
      }
    }
    return safeMap;
  }

  /**
   * Wysyła dane do wybranej tabeli. Sprawdza bezpieczeństwo danych wejściowych.
   *
   * @param table nazwa tabeli.
   * @param requestBody mapa danych do zapisania.
   * @return wynik operacji jako String.
   */
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
    } catch (Exception e) {
      throw new SupabaseConnectionException("Failed to post to table " + table + ": ", e);
    }
  }

  /**
   * Aktualizuje dane rekordu o określonym identyfikatorze w wybranej tabeli.
   *
   * @param table nazwa tabeli.
   * @param id identyfikator rekordu.
   * @param requestBody mapa pól do aktualizacji.
   * @return wynik operacji jako String.
   */
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

  /**
   * Usuwa rekord z wybranej tabeli na podstawie identyfikatora.
   *
   * @param table nazwa tabeli.
   * @param id identyfikator rekordu do usunięcia.
   * @return wynik operacji usuwania jako String.
   */
  private String deleteData(String table, int id) {
    try {
      return webClient.delete()
          .uri(uriBuilder -> uriBuilder.path("/" + table)
              .queryParam("id", "eq." + id).build())
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (Exception e) {
      throw new SupabaseConnectionException(
          "Failed to delete id: " + id + " in table: " + table + ": ", e);
    }
  }

  /**
   * Pobiera dane użytkownika na podstawie nazwy użytkownika.
   *
   * @param username nazwa użytkownika.
   * @return obiekt Uzytkownik lub null, jeśli nie znaleziono.
   */
  public Uzytkownik getUserByUsername(String username) {
    try {
      String response = webClient.get()
          .uri(uriBuilder -> uriBuilder
              .path(SLUZYTKOWNIK)
              .queryParam(SELECT, "*")
              .queryParam(NAZWA_UZYTKOWNIKA, "eq." + username)
              .build())
          .retrieve()
          .bodyToMono(String.class)
          .block();

      ObjectMapper mapper = new ObjectMapper();
      mapper.setPropertyNamingStrategy(
          PropertyNamingStrategies.UPPER_CAMEL_CASE);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

      JsonNode root = mapper.readTree(response);
      if (!root.isArray() || root.size() == 0) {
        return null;
      }

      JsonNode userNode = root.get(0);

      Uzytkownik user = new Uzytkownik();
      user.setId(userNode.get("id").asInt());
      user.setImie(userNode.get("Imie").asText(null));
      user.setNazwisko(userNode.get(NAZWISKO).asText(null));
      user.setDataUrodzenia(userNode.get(DATA_URODZENIA).asText(null));
      user.setNazwaUzytkownika(userNode.get(NAZWA_UZYTKOWNIKA).asText(null));
      user.setHaslo(userNode.get(HASLO).asText(null));
      user.setEmail(userNode.get(EMAIL).asText(null));
      user.setZablokowany(userNode.get("Zablokowany").asBoolean());
      user.setMfaEnabled(userNode.get(MFA_ENABLED).asBoolean());
      user.setMfaSecret(userNode.get(MFA_SECRET).asText(null));
      user.setRole(userNode.get("role").asText(null));

      return user;

    } catch (Exception e) {
      throw new SupabaseConnectionException("Błąd pobierania użytkownika: ", e);
    }
  }

  /**
   * Pobiera dane administratora na podstawie nazwy użytkownika.
   *
   * @param username nazwa użytkownika.
   * @return obiekt Admin lub null, jeśli nie znaleziono.
   */
  public Admin getAdminByUsername(String username) {
    try {
      String response = webClient.get()
          .uri(uriBuilder -> uriBuilder
              .path(SLADMIN)
              .queryParam(SELECT, "*")
              .queryParam(NAZWA_UZYTKOWNIKA, "eq." + username)
              .build())
          .retrieve()
          .bodyToMono(String.class)
          .block();

      ObjectMapper mapper = new ObjectMapper();
      mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

      JsonNode root = mapper.readTree(response);
      if (!root.isArray() || root.size() == 0) {
        return null;
      }

      JsonNode adminNode = root.get(0);
      Admin admin = new Admin();
      admin.setId(adminNode.get("id").asInt());
      admin.setImie(adminNode.get("Imie").asText(null));
      admin.setNazwisko(adminNode.get(NAZWISKO).asText(null));
      admin.setNazwaUzytkownika(adminNode.get(NAZWA_UZYTKOWNIKA).asText(null));
      admin.setHaslo(adminNode.get(HASLO).asText(null));
      admin.setIdplacowki(adminNode.get(ID_PLACOWKI).asInt());
      admin.setMfaEnabled(adminNode.get(MFA_ENABLED).asBoolean());
      admin.setMfaSecret(adminNode.get(MFA_SECRET).asText(null));
      admin.setRole(adminNode.get("role").asText(null));
      return admin;

    } catch (Exception e) {
      throw new SupabaseConnectionException("Błąd pobierania admina: ", e);
    }
  }


  /**
   * Aktualizacja secretu użytkownika 2FA w bazie danych.
   *
   * @param userId identyfikator użytkownika, u którego aktualizowany jest secret
   * @param secret nowa wartość secretu
   */
  public void updateUserMfaSecret(int userId, String secret) {
    webClient.patch()
        .uri(uriBuilder -> uriBuilder
        .path(SLUZYTKOWNIK)
        .queryParam("id", "eq." + userId).build())
        .bodyValue(Map.of(MFA_SECRET, secret))
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }

  /**
   * Aktualizacja stanu enable 2FA w bazie danych.
   *
   * @param userId identyfikator aktualizowanego użytkownika
   * @param enabled wartość boolean, czy 2FA ma być włączony czy wyłączony
   */
  public void updateUserMfaEnabled(int userId, boolean enabled) {
    webClient.patch()
        .uri(uriBuilder -> uriBuilder
        .path(SLUZYTKOWNIK)
        .queryParam("id", "eq." + userId).build())
        .bodyValue(Map.of(MFA_ENABLED, enabled))
        .retrieve()
        .bodyToMono(Void.class)
        .block();
  }

  /**
   * Aktualizacja secretu administratora 2FA w bazie danych.
   *
   * @param userId identyfikator administratora, u którego aktualizowany jest secret
   * @param secret nowa wartość secretu
   */
  public void updateAdminMfaSecret(int userId, String secret) {
    webClient.patch()
        .uri(uriBuilder -> uriBuilder
            .path(SLADMIN)
            .queryParam("nazwa_uzytkownika", "eq." + userId)
            .build())
        .bodyValue(Map.of(MFA_SECRET, secret))
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  /**
   * Aktualizacja stanu enable 2FA u administratora w bazie danych.
   *
   * @param userId identyfikator aktualizowanego administratora
   * @param enabled wartość boolean, czy 2FA ma być włączony, czy wyłączony
   */
  public void updateAdminMfaEnabled(int userId, boolean enabled) {
    webClient.patch()
        .uri(uriBuilder -> uriBuilder
            .path(SLADMIN)
            .queryParam("nazwa_uzytkownika", "eq." + userId)
            .build())
        .bodyValue(Map.of(MFA_ENABLED, enabled))
        .retrieve()
        .toBodilessEntity()
        .block();
  }

}
