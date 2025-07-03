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
 * Komponent odpowiedzialny za restart TOTP.
 */
@Component
public class TotpRestart {

  private static final Logger logger = Logger.getLogger(TotpRestart.class.getName());
  @FXML
  private TextField codeField;

  /**
   * Funkcja obsługująca logikę przycisku.
   * Wysyła zapytanie do serwera i osbługuje od odpowiedź.
   *
   * @param actionEvent Pozwala na zamknięcie okna.
   */
  @FXML
  public void onClick(ActionEvent actionEvent) {
    try {
      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(
              "https://localhost:8443/library/uzytkownicy/2fa/confirm/" + codeField.getText() + "/"
                  + LogUser.getUserEmail()))
          .header("Content-Type", "application/x-www-form-urlencoded")
          .PUT(HttpRequest.BodyPublishers.noBody())
          .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DonePopup.fxml"));
        Parent popupRoot = fxmlLoader.load();

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(popupRoot));
        popupStage.showAndWait();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        popupRoot = fxmlLoader.load();

        popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(popupRoot));
        popupStage.showAndWait();
      } else {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UndonePopup.fxml"));
        Parent popupRoot = fxmlLoader.load();

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
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

