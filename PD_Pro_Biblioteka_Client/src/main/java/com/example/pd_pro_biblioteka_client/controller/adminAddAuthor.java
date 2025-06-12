package com.example.pd_pro_biblioteka_client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.function.UnaryOperator;

public class adminAddAuthor {
    public Button add_button;
    public TextField au_name;
    public TextField au_surname;
    public TextField au_year;

    @FXML
    public void initialize() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,4}")) {
                return change;
            }
            return null;
        };
        au_year.setTextFormatter(new TextFormatter<>(filter));
    }

    @FXML
    public void add_act(ActionEvent actionEvent) {

        try {
            HttpClient client = HttpClient.newHttpClient();

            // Tworzymy dane formularza
            String form = "imie=" + URLEncoder.encode(au_name.getText(), StandardCharsets.UTF_8) +
                    "&nazwisko=" + URLEncoder.encode(au_surname.getText(), StandardCharsets.UTF_8) +
                    "&rokUrodzenia=" + URLEncoder.encode(au_year.getText(), StandardCharsets.UTF_8);

            // Tworzymy request POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/library/autorzy?"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .build();

            // Wysyłamy request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Odpowiedź serwera: " + response.statusCode());
            System.out.println("Tresc odpowiedzi: " + response.body());

            if(response.statusCode() == 200) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/add_authorpop.fxml"));
                Parent popupRoot = fxmlLoader.load();

                //jeśli rejestracja jest poprawna
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                popupStage.setTitle("Dodaj");
                popupStage.setScene(new Scene(popupRoot));
                popupStage.showAndWait();

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();}
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/add_authorpop_1.fxml"));
                Parent popupRoot = fxmlLoader.load();

                //jeśli rejestracja nie jest poprawna
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                popupStage.setTitle("Dodaj");
                popupStage.setScene(new Scene(popupRoot));
                popupStage.showAndWait();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
