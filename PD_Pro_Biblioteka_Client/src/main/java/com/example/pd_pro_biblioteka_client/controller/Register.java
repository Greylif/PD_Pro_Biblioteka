package com.example.pd_pro_biblioteka_client.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
public class Register {

    public TextField user_name;
    public TextField user_surname;
    public DatePicker user_date;
    public TextField user_email;
    public TextField user_login;
    public TextField user_password;
    public Button register;

    @FXML
    public void reg_act(javafx.event.ActionEvent actionEvent) {
        System.out.println("Kliknięto REGISTER w GUI");

        try {
            HttpClient client = HttpClient.newHttpClient();

            LocalDate selectedDate = user_date.getValue();
            String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            System.out.println(formattedDate);

            // Tworzymy dane formularza
            String form = "imie=" + URLEncoder.encode(user_name.getText(), StandardCharsets.UTF_8) +
                    "&nazwisko=" + URLEncoder.encode(user_surname.getText(), StandardCharsets.UTF_8) +
                    "&dataUrodzenia=" + URLEncoder.encode(formattedDate, StandardCharsets.UTF_8) +
                    "&nazwaUzytkownika=" + URLEncoder.encode(user_login.getText(), StandardCharsets.UTF_8) +
                    "&haslo=" + URLEncoder.encode(user_password.getText(), StandardCharsets.UTF_8) +
                    "&email=" + URLEncoder.encode(user_email.getText(), StandardCharsets.UTF_8);

            // Tworzymy request POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/library/uzytkownicy"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .build();

            // Wysyłamy request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Odpowiedź serwera: " + response.statusCode());
            System.out.println("Tresc odpowiedzi: " + response.body());

            if(response.statusCode() == 200) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/register_popup.fxml"));
                Parent popupRoot = fxmlLoader.load();

                //jeśli rejestracja jest poprawna
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                popupStage.setTitle("Rejestracja");
                popupStage.setScene(new Scene(popupRoot));
                popupStage.showAndWait();

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();}
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/register_popup_1.fxml"));
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
            e.printStackTrace();
        }
    }
}
