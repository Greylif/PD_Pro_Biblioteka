package com.example.pd_pro_biblioteka_client.controller;

import com.example.pd_pro_biblioteka_client.model.logAdmin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

public class Addpenalty {
    @FXML
    private TextField pen_desc;
    @FXML
    private TextField userID;
    @FXML
    private TextField userPen;
    @FXML
    private DatePicker pen_date;
    private static final Logger logger = Logger.getLogger(Addpenalty.class.getName());

    public void button_act(ActionEvent actionEvent) {
        try {
            @SuppressWarnings("java:S2095")
            HttpClient client = HttpClient.newHttpClient();

            LocalDate selectedDate = pen_date.getValue();
            String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedToday = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Tworzymy dane formularza
            String form = "kwota=" + URLEncoder.encode(userPen.getText(), StandardCharsets.UTF_8) +
                    "&dataWydaniaKary=" + URLEncoder.encode(formattedToday, StandardCharsets.UTF_8) +
                    "&terminZaplaty=" + URLEncoder.encode(formattedDate, StandardCharsets.UTF_8) +
                    "&idUzytkownika=" + URLEncoder.encode(userID.getText(), StandardCharsets.UTF_8)+
                    "&opis=" + URLEncoder.encode(pen_desc.getText(), StandardCharsets.UTF_8);

            logger.info(form);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://localhost:8443/library/kary"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Bearer " + logAdmin.getAdminToken())
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .build();

            // Wysyłamy request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/donePopup.fxml"));
                Parent popupRoot = fxmlLoader.load();

                //jeśli rejestracja jest poprawna
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                popupStage.setScene(new Scene(popupRoot));
                popupStage.showAndWait();

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();}
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/undonePopup.fxml"));
                Parent popupRoot = fxmlLoader.load();

                //jeśli rejestracja jest poprawna
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
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
