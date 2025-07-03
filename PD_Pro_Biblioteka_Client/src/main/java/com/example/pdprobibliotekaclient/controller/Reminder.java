package com.example.pdprobibliotekaclient.controller;


import com.example.pdprobibliotekaclient.model.LogUser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za logikę okna, które pozwla na restart hasła lub 2FA.
 */
@Component
public class Reminder {

  private static final Logger logger = Logger.getLogger(Reminder.class.getName());
  private static final String PRZYPHASLO = "Przypomnij hasło";
  @FXML
  private TextField useremail;

  /**
   * Funkcja odpowiedzialna za logikę przycisku, wysyła zapytanie do serwera (które restartuje hasło) i odpowiednio reaguje na odpowiedź serwera.
   *
   * @param actionEvent Parametr odpowiedzalny za zamknięcię danego okna
   */
  @FXML
  public void reminderact(javafx.event.ActionEvent actionEvent) {
    try {
      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(
              "https://localhost:8443/library/uzytkownicy/passwordreset/" + useremail.getText()))
          .header("Content-Type", "application/x-www-form-urlencoded")
          .PUT(HttpRequest.BodyPublishers.noBody())
          .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DonePopup.fxml"));
        Parent popupRoot = fxmlLoader.load();

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(PRZYPHASLO);
        popupStage.setScene(new Scene(popupRoot));
        popupStage.showAndWait();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
      } else {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UndonePopup.fxml"));
        Parent popupRoot = fxmlLoader.load();

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(PRZYPHASLO);
        popupStage.setScene(new Scene(popupRoot));
        popupStage.showAndWait();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Funkcja odpowiedzialna za logikę przycisku, wysyła zapytanie do serwera (które restartuje TOTP) i przekierowywuje do okna TotpRestart.fxml.
   *
   * @param actionEvent Parametr odpowiedzalny za zamknięcię danego okna
   */
  public void reminderactTotp(ActionEvent actionEvent) {
    try {
      LogUser.setUserEmail(useremail.getText());

      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://localhost:8443/library/uzytkownicy/2fa/" + useremail.getText()))
          .header("Content-Type", "application/x-www-form-urlencoded")
          .PUT(HttpRequest.BodyPublishers.noBody())
          .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TotpRestart.fxml"));
        Parent popupRoot = fxmlLoader.load();

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(PRZYPHASLO);
        popupStage.setScene(new Scene(popupRoot));
        popupStage.showAndWait();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
      } else {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/unndonePopup.fxml"));
        Parent popupRoot = fxmlLoader.load();

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(PRZYPHASLO);
        popupStage.setScene(new Scene(popupRoot));
        popupStage.showAndWait();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }
}

