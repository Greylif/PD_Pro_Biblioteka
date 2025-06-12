package com.example.pd_pro_biblioteka_client.controller;

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

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class addBorrow {


    @FXML
    private TextField borrowUserID;
    @FXML
    private TextField borrowBookID;
    @FXML
    private DatePicker borrowReturnDate;
    @FXML
    private Button b_add;

    @FXML
    public void button_act(ActionEvent actionEvent) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            LocalDate selectedDate = borrowReturnDate.getValue();
            String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedToday = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // Tworzymy dane formularza
            String form = "dataWypozyczenia=" + URLEncoder.encode(formattedToday, StandardCharsets.UTF_8) +
                    "&terminOddania=" + URLEncoder.encode(formattedDate, StandardCharsets.UTF_8) +
                    "&idKsiazki=" + URLEncoder.encode(borrowBookID.getText(), StandardCharsets.UTF_8)+
                    "&idUzytkownika=" + URLEncoder.encode(borrowUserID.getText(), StandardCharsets.UTF_8);

            System.out.println(form);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/library/wypozyczenia"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .build();

            // Wysyłamy request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Odpowiedź serwera: " + response.statusCode());
            System.out.println("Tresc odpowiedzi: " + response.body());

            if(response.statusCode() == 200) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/penalty_popup.fxml"));
                Parent popupRoot = fxmlLoader.load();

                //jeśli rejestracja jest poprawna
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                popupStage.setScene(new Scene(popupRoot));
                popupStage.showAndWait();

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();}
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/penalty_popup_1.fxml"));
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
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
