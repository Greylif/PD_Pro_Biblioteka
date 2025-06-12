package com.example.pd_pro_biblioteka_client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
import java.util.function.UnaryOperator;

@Component
public class AdminAddbook {

    public TextField a_title;
    public TextField a_genre;
    public TextField a_relaseDate;
    public Button add_button;
    public TextField a_id_author;
    public TextField a_id_plac;

    @FXML
    public void initialize() {
        // Dodanie filtra do pola tekstowego
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,4}")) {
                return change;
            }
            return null;
        };
        a_relaseDate.setTextFormatter(new TextFormatter<>(filter));
        a_id_plac.setTextFormatter(new TextFormatter<>(filter));
        a_id_author.setTextFormatter(new TextFormatter<>(filter));
    }

    @FXML
    public void add_act(ActionEvent actionEvent) {

        try {
            HttpClient client = HttpClient.newHttpClient();

            // Tworzymy dane formularza
            String form = "tytul=" + URLEncoder.encode(a_title.getText(), StandardCharsets.UTF_8) +
                    "&gatunek=" + URLEncoder.encode(a_genre.getText(), StandardCharsets.UTF_8) +
                    "&dataWydania=" + URLEncoder.encode(a_relaseDate.getText(), StandardCharsets.UTF_8) +
                    "&idAutora=" + URLEncoder.encode(a_id_author.getText(), StandardCharsets.UTF_8)+
                    "&idPlacowki=" + URLEncoder.encode(a_id_plac.getText(), StandardCharsets.UTF_8);

            // Tworzymy request POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/library/ksiazki?"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(form))
                    .build();

            // Wysyłamy request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Odpowiedź serwera: " + response.statusCode());
            System.out.println("Tresc odpowiedzi: " + response.body());

            if(response.statusCode() == 200) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/add_bookpop.fxml"));
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/add_bookpop_1.fxml"));
                Parent popupRoot = fxmlLoader.load();

                //jeśli rejestracja jest poprawna
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
