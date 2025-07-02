package com.example.pdprobibliotekaclient.controller;

import com.example.pdprobibliotekaclient.model.LogAdmin;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class Addborow {

  private static final Logger logger = Logger.getLogger(Addborow.class.getName());
  @FXML
  private TextField borrowUserid;
  @FXML
  private TextField borrowBookid;
  @FXML
  private DatePicker borrowReturnDate;
  @FXML
  private Button badd;

  @FXML
  public void buttonact(ActionEvent actionEvent) {
    try {
      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();

      LocalDate selectedDate = borrowReturnDate.getValue();
      String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      String formattedToday = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

      String form =
          "dataWypozyczenia=" + URLEncoder.encode(formattedToday, StandardCharsets.UTF_8)
              + "&terminOddania=" + URLEncoder.encode(formattedDate, StandardCharsets.UTF_8)
              + "&idKsiazki=" + URLEncoder.encode(borrowBookid.getText(), StandardCharsets.UTF_8)
              + "&idUzytkownika=" + URLEncoder.encode(borrowUserid.getText(),
              StandardCharsets.UTF_8);

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://localhost:8443/library/wypozyczenia"))
          .header("Content-Type", "application/x-www-form-urlencoded")
          .header("Authorization", "Bearer " + LogAdmin.getAdminToken())
          .POST(HttpRequest.BodyPublishers.ofString(form))
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
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    stage.close();
  }
}
