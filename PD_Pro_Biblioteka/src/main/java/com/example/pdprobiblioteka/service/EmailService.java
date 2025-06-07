package com.example.pdprobiblioteka.service;

import com.example.pdprobiblioteka.exceptions.EmailSendException;
import com.example.pdprobiblioteka.exceptions.JsonFileException;
import com.example.pdprobiblioteka.exceptions.SupabaseConnectionException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Serwis odpowiedzialny za wysyłanie wiadomości e-mail do użytkowników,
 * w tym przypomnień o zbliżającym się terminie zwrotu książki oraz resetu hasła.
 * Dane są pobierane z bazy danych Supabase.
 */
@Service
public class EmailService {

  private static final String TERMIN_ODDANIA = "Termin_Oddania";
  private static final String EMAIL = "Email";
  private static final String TYTUL = "Tytul";
  private static final String DATA_ODDANIA = "Data_Oddania";
  private static final String CHARS =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_@+:,.ąćęłńóśźżĄĆĘŁŃÓŚŹŻ";
  private final JavaMailSender mailSender;
  private final WebClient webClient;

  /**
   * Tworzy instancję serwisu EmailService i inicjalizuje klienta WebClient
   * z ustawionymi domyślnymi nagłówkami do komunikacji z usługą Supabase.
   *
   * @param mailSender obiekt odpowiedzialny za wysyłanie e-maili (JavaMailSender)
   * @param webClientBuilder budowniczy WebClienta używany do konfiguracji połączenia z Supabase
   * @throws SupabaseConnectionException jeśli inicjalizacja WebClienta nie powiedzie się
   */
  public EmailService(JavaMailSender mailSender,
      WebClient.Builder webClientBuilder) {
    this.mailSender = mailSender;

    String supabaseUrl = System.getenv("SUPABASE_URL");
    String supabaseKey = System.getenv("SUPABASE_KEY");
    String supabaseKey2 = System.getenv("SUPABASE_KEY2");

    supabaseUrl = supabaseUrl.trim() + "/rest/v1";
    supabaseKey = supabaseKey.trim();
    supabaseKey2 = supabaseKey2.trim();

    try {
      this.webClient = webClientBuilder
          .baseUrl(supabaseUrl)
          .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + supabaseKey)
          .defaultHeader("apikey", supabaseKey2)
          .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .build();
    } catch (Exception e) {
      throw new SupabaseConnectionException("Failed to connect to Supabase: ", e);
    }
  }

  /**
   * Wysyła wiadomość e-mail do podanego adresata.
   *
   * @param to      adres e-mail odbiorcy
   * @param subject temat wiadomości
   * @param text    treść wiadomości (HTML dozwolony)
   * @throws MessagingException w przypadku błędu wysyłki e-maila
   */
  public void sendEmail(String to, String subject, String text) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(text, true);

    mailSender.send(message);
  }

  /**
   * Zaplanowane zadanie wysyłające e-maile z przypomnieniem o zwrocie książki.
   * Uruchamiane codziennie o 17:00.
   *
   * @throws MessagingException w przypadku błędu wysyłki e-maila
   */
  @Scheduled(cron = "0 00 17 * * ?")
  public void scheduledEmail() throws MessagingException {
    JSONArray arrayout = fetchWypozyczeniazEmail();
    for (int i = 0; i < arrayout.length(); i++) {
      JSONObject obj = arrayout.getJSONObject(i);
      LocalDate termin = LocalDate.parse(obj.getString(TERMIN_ODDANIA));
      Object dataoddania = obj.opt(DATA_ODDANIA);
      if ((termin.minusDays(3)).isBefore(LocalDate.now())) {
        assert dataoddania != null;
        if ("null".equals(dataoddania.toString())) {

          sendEmail(obj.getString(EMAIL), "Przypomnienie o oddaniu ksiazki",
              "Termin oddania ksiazki o tytule: " + obj.getString(TYTUL) + " mija: "
                  + obj.getString(
                  TERMIN_ODDANIA));
        }
      }
    }
  }

  /**
   * Generuje nowe hasło, wysyła je użytkownikowi e-mailem i aktualizuje je w bazie danych.
   *
   * @param email adres e-mail użytkownika
   * @throws EmailSendException             w przypadku błędu wysyłki wiadomości
   * @throws SupabaseConnectionException    w przypadku błędu połączenia z bazą Supabase
   */
  public void sendNewPassword(String email) {
    SecureRandom random = new SecureRandom();
    StringBuilder password = new StringBuilder();

    for (int i = 0; i < 12; i++) {
      password.append(CHARS.charAt(random.nextInt(CHARS.length())));
    }

    try {
      sendEmail(email, "Library Password", "Your new Password is: " + password);
    } catch (MessagingException e) {
      throw new EmailSendException("Failed to send new password", e);
    }

    Map<String, Object> body = new HashMap<>();
    body.put("Haslo", password.toString());

    try {
      webClient.patch()
          .uri(uriBuilder -> uriBuilder.path("/" + "Uzytkownik")
              .queryParam(EMAIL, "eq." + email).build())
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(body)
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (Exception e) {
      throw new SupabaseConnectionException("Failed to update to table Email" + ": ", e);
    }

  }


  /**
   * Pobiera dane wypożyczeń i łączy je z adresami e-mail oraz tytułami książek.
   *
   * @return tablica JSON zawierająca dane do wysyłki e-maili
   */
  private JSONArray fetchWypozyczeniazEmail() {
    String wypozyczeniaData;

    try {
      wypozyczeniaData = fetchWypozyczeniaData();

      Map<Integer, String> uzytkownicyzEmail = fetchUzytkownicyEmail(wypozyczeniaData);
      Map<Integer, String> ksiazkizTytulami = fetchKsiazkiTytuly(wypozyczeniaData);

      return polaczWypozyczeniazEmail(wypozyczeniaData, uzytkownicyzEmail, ksiazkizTytulami);

    } catch (SupabaseConnectionException e) {
      throw new SupabaseConnectionException("Failed to connect to Supabase: ", e);
    }
  }

  /**
   * Pobiera dane wypożyczeń z Supabase.
   *
   * @return ciąg JSON z danymi wypożyczeń
   * @throws SupabaseConnectionException w przypadku błędu połączenia
   */
  private String fetchWypozyczeniaData() {
    try {
      return webClient.get()
          .uri(uriBuilder -> uriBuilder
              .path("/Wypozyczenia")
              .build())
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (Exception e) {
      throw new SupabaseConnectionException("Failed to fetch wypozyczenia data: ", e);
    }
  }


  /**
   * Pobiera adresy e-mail użytkowników na podstawie danych wypożyczeń.
   *
   * @param wypozyczeniaData dane wypożyczeń
   * @return mapa ID użytkownika → adres e-mail
   * @throws JsonFileException w przypadku błędu przetwarzania JSON
   */
  private Map<Integer, String> fetchUzytkownicyEmail(String wypozyczeniaData) {
    Set<Integer> uzytkownikIds = new HashSet<>();
    Map<Integer, String> mapIdEmail = new HashMap<>();

    try {
      JSONArray jsonArray = new JSONArray(wypozyczeniaData);
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject wypozyczenie = jsonArray.getJSONObject(i);
        uzytkownikIds.add(wypozyczenie.getInt("id_uzytkownika"));
      }

      String uzytkownicyData = fetchUzytkownicyData(uzytkownikIds);

      JSONArray uzytkownicyJson = new JSONArray(uzytkownicyData);
      for (int i = 0; i < uzytkownicyJson.length(); i++) {
        JSONObject user = uzytkownicyJson.getJSONObject(i);
        mapIdEmail.put(user.getInt("id"), user.getString(EMAIL));
      }
      return mapIdEmail;

    } catch (SupabaseConnectionException e) {
      throw e;
    } catch (Exception e) {
      throw new JsonFileException("Failed to process użytkownicy JSON", e);
    }
  }

  /**
   * Pobiera dane użytkowników z Supabase.
   *
   * @param uzytkownikIds zbiór ID użytkowników
   * @return ciąg JSON z danymi użytkowników
   * @throws SupabaseConnectionException w przypadku błędu połączenia
   */
  private String fetchUzytkownicyData(Set<Integer> uzytkownikIds) {
    String idsFilter = "id=in.(" + uzytkownikIds.stream()
        .map(String::valueOf)
        .collect(Collectors.joining(",")) + ")";
    try {
      return webClient.get()
          .uri(uriBuilder -> uriBuilder
              .path("/Uzytkownik")
              .query(idsFilter)
              .build())
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (Exception e) {
      throw new SupabaseConnectionException("Failed to fetch uzytkownicy data: ", e);
    }
  }

  /**
   * Pobiera tytuły książek na podstawie danych wypożyczeń.
   *
   * @param wypozyczeniaData dane wypożyczeń
   * @return mapa ID książki → tytuł
   * @throws JsonFileException w przypadku błędu przetwarzania JSON
   */
  private Map<Integer, String> fetchKsiazkiTytuly(String wypozyczeniaData) {
    Set<Integer> ksiazkaIds = new HashSet<>();
    Map<Integer, String> mapIdTytul = new HashMap<>();

    try {
      JSONArray jsonArray = new JSONArray(wypozyczeniaData);
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject wypozyczenie = jsonArray.getJSONObject(i);
        ksiazkaIds.add(wypozyczenie.getInt("id_ksiazki"));
      }

      String ksiazkiData = fetchKsiazkiData(ksiazkaIds);

      JSONArray ksiazkiJson = new JSONArray(ksiazkiData);
      for (int i = 0; i < ksiazkiJson.length(); i++) {
        JSONObject ksiazka = ksiazkiJson.getJSONObject(i);
        mapIdTytul.put(ksiazka.getInt("id"), ksiazka.getString(TYTUL));
      }
      return mapIdTytul;

    } catch (SupabaseConnectionException e) {
      throw e;
    } catch (Exception e) {
      throw new JsonFileException("Failed to process ksiazki JSON", e);
    }
  }


  /**
   * Pobiera dane książek z Supabase.
   *
   * @param ksiazkaIds zbiór ID książek
   * @return ciąg JSON z danymi książek
   * @throws SupabaseConnectionException w przypadku błędu połączenia
   */
  private String fetchKsiazkiData(Set<Integer> ksiazkaIds) {
    String idsFilter = "id=in.(" + ksiazkaIds.stream()
        .map(String::valueOf)
        .collect(Collectors.joining(",")) + ")";
    try {
      return webClient.get()
          .uri(uriBuilder -> uriBuilder
              .path("/Ksiazka")
              .query(idsFilter)
              .build())
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (Exception e) {
      throw new SupabaseConnectionException("Failed to fetch ksiazki data: ", e);
    }
  }

  /**
   * Łączy dane wypożyczeń z adresami e-mail i tytułami książek.
   *
   * @param wypozyczeniaData  dane wypożyczeń
   * @param uzytkownicyEmails mapa ID użytkownika → e-mail
   * @param ksiazkiTytuly     mapa ID książki → tytuł
   * @return tablica JSON gotowa do wysyłki przypomnień
   */
  private JSONArray polaczWypozyczeniazEmail(String wypozyczeniaData,
      Map<Integer, String> uzytkownicyEmails, Map<Integer, String> ksiazkiTytuly) {
    JSONArray resultArray = new JSONArray();
    JSONArray jsonArray = new JSONArray(wypozyczeniaData);
    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject wypozyczenie = jsonArray.getJSONObject(i);

      int userId = wypozyczenie.getInt("id_uzytkownika");
      int ksiazkaId = wypozyczenie.getInt("id_ksiazki");

      String email = uzytkownicyEmails.get(userId);
      String tytul = ksiazkiTytuly.get(ksiazkaId);

      JSONObject merged = new JSONObject();
      merged.put(EMAIL, email);
      merged.put(TYTUL, tytul);
      merged.put(TERMIN_ODDANIA, wypozyczenie.getString(TERMIN_ODDANIA));
      merged.put(DATA_ODDANIA, wypozyczenie.opt(DATA_ODDANIA));
      resultArray.put(merged);
    }
    return resultArray;
  }

}
