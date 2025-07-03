package com.example.pdprobibliotekaclient.service;


import com.example.pdprobibliotekaclient.model.LogAdmin;
import com.example.pdprobibliotekaclient.model.LogUser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Klasa odpowiedzialna za monitorowanie sesji użytkownika.
 * Sprawdza ważność tokenów JWT użytkownika i administratora
 * poprzez okresowe wysyłanie zapytań HTTP do serwera.
 * W przypadku nieważnego tokena automatycznie wylogowuje użytkownika
 * i wyświetla ekran logowania.
 */
public class SessionMonitor {

  private static final Logger logger = Logger.getLogger(SessionMonitor.class.getName());
  public final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
  private final Stage stage;


  /**
   * Konstruktor tworzący monitor sesji powiązany z konkretnym oknem (Stage).
   *
   * @param stage główne okno aplikacji, które może zostać zamknięte przy wylogowaniu
   */
  public SessionMonitor(Stage stage) {
    this.stage = stage;
  }

  /**
   * Wymusza wylogowanie użytkownika, zamyka obecne okno i wyświetla okno logowania.
   *
   * @param stage obecne okno do zamknięcia
   */
  public static void forceLogout(Stage stage) {
    try {
      stage.close();

      FXMLLoader fxmlLoader = new FXMLLoader(SessionMonitor.class.getResource("/login.fxml"));
      Parent root = fxmlLoader.load();

      Stage loginStage = new Stage();
      loginStage.initModality(Modality.APPLICATION_MODAL);
      loginStage.setTitle("Logowanie");
      loginStage.setScene(new Scene(root));
      loginStage.show();
    } catch (IOException e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }


  /**
   * Uruchamia okresowe sprawdzanie ważności tokenów co 70 sekund (z opóźnieniem 1 sekundy przed pierwszym wywołaniem).
   */
  public void start() {
    scheduler.scheduleAtFixedRate(this::checkToken, 1, 70, TimeUnit.SECONDS);
  }

  /**
   * Sprawdza ważność tokenów JWT użytkownika i administratora poprzez wysłanie zapytań HTTP.
   * Jeśli oba tokeny są nieważne, wywołuje wymuszone wylogowanie.
   */
  public void checkToken() {
    @SuppressWarnings("java:S2095")
    HttpClient client = HttpClient.newHttpClient();

    try {

      HttpResponse<String> response = client.send(
          HttpRequest.newBuilder()
              .uri(URI.create("https://localhost:8443/library/ksiazki"))
              .header("Authorization", "Bearer " + LogUser.getUserToken())
              .GET()
              .build(),
          HttpResponse.BodyHandlers.ofString()
      );

      HttpResponse<String> responseAdm = client.send(
          HttpRequest.newBuilder()
              .uri(URI.create("https://localhost:8443/library/ksiazki"))
              .header("Authorization", "Bearer " + LogAdmin.getAdminToken())
              .GET()
              .build(),
          HttpResponse.BodyHandlers.ofString()
      );

      int statusCode = response.statusCode();
      int adminStatusCode = responseAdm.statusCode();

      if (statusCode == 200) {
        logger.log(Level.INFO, "Klient: 200");
      } else {
        if (adminStatusCode == 200) {
          logger.log(Level.INFO, "Admin: 200");
        } else {
          Platform.runLater(() -> {
            stop();
            forceLogout(stage);
            logger.log(Level.INFO, "Wylogowano z powodu tokena");
          });
        }
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Zatrzymuje monitorowanie sesji, czyści tokeny i wyłącza harmonogram.
   */
  public void stop() {
    LogAdmin.clearAdmin();
    LogUser.clearUser();
    scheduler.shutdownNow();
  }
}
