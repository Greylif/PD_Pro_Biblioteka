package com.example.pdprobibliotekaclient.controller;


import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class Register {

  private static final Logger logger = Logger.getLogger(Register.class.getName());
  @FXML
  private TextField username;
  @FXML
  private TextField usersurname;
  @FXML
  private DatePicker userdate;
  @FXML
  private TextField useremail;
  @FXML
  private TextField userlogin;
  @FXML
  private TextField userpassword;

  @FXML
  public void regact(javafx.event.ActionEvent actionEvent) {
    try {
      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();

      LocalDate selectedDate = userdate.getValue();
      String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

      // Tworzymy dane formularza
      String form = "imie=" + URLEncoder.encode(username.getText(), StandardCharsets.UTF_8)
          + "&nazwisko=" + URLEncoder.encode(usersurname.getText(), StandardCharsets.UTF_8)
          + "&dataUrodzenia=" + URLEncoder.encode(formattedDate, StandardCharsets.UTF_8)
          + "&nazwaUzytkownika=" + URLEncoder.encode(userlogin.getText(), StandardCharsets.UTF_8)
          + "&haslo=" + URLEncoder.encode(userpassword.getText(), StandardCharsets.UTF_8)
          + "&email=" + URLEncoder.encode(useremail.getText(), StandardCharsets.UTF_8);

      // Tworzymy request POST
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://localhost:8443/library/uzytkownicy"))
          .header("Content-Type", "application/x-www-form-urlencoded")
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
        popupStage.setTitle("Rejestracja");
        popupStage.setScene(new Scene(popupRoot));
        popupStage.showAndWait();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
      } else {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UndonePopup.fxml"));
        Parent popupRoot = fxmlLoader.load();

        //jeśli rejestracja jest poprawna
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
        popupStage.setTitle("Rejestracja");
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
