package com.example.pdprobibliotekaclient.controller;

import com.example.pdprobibliotekaclient.model.logAdmin;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminAddAuthor {

  private static final Logger logger = Logger.getLogger(AdminAddAuthor.class.getName());
  @FXML
  private TextField auname;
  @FXML
  private TextField ausurname;
  @FXML
  private TextField auyear;

  @FXML
  public void initialize() {
    UnaryOperator<TextFormatter.Change> filter = change -> {
      String newText = change.getControlNewText();
      if (newText.matches("\\d{0,4}")) {
        return change;
      }
      return null;
    };
    auyear.setTextFormatter(new TextFormatter<>(filter));
  }

  @FXML
  public void addact(ActionEvent actionEvent) {

    try {
      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();

      // Tworzymy dane formularza
      String form = "imie=" + URLEncoder.encode(auname.getText(), StandardCharsets.UTF_8)
          + "&nazwisko=" + URLEncoder.encode(ausurname.getText(), StandardCharsets.UTF_8)
          + "&rokUrodzenia=" + URLEncoder.encode(auyear.getText(), StandardCharsets.UTF_8);

      // Tworzymy request POST
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://localhost:8443/library/autorzy?"))
          .header("Content-Type", "application/x-www-form-urlencoded")
          .header("Authorization", "Bearer " + logAdmin.getAdminToken())
          .POST(HttpRequest.BodyPublishers.ofString(form))
          .build();

      // Wysyłamy request
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DonePopup.fxml"));
        Parent popupRoot = fxmlLoader.load();

        //jeśli rejestracja jest poprawna
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
        popupStage.setScene(new Scene(popupRoot));
        popupStage.showAndWait();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
      } else {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UndonePopup.fxml"));
        Parent popupRoot = fxmlLoader.load();

        //jeśli rejestracja nie jest poprawna
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
        popupStage.setScene(new Scene(popupRoot));
        popupStage.showAndWait();

      }

    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

}
