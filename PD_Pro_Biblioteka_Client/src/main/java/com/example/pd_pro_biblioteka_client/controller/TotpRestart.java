package com.example.pd_pro_biblioteka_client.controller;

import com.example.pd_pro_biblioteka_client.model.logUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TotpRestart {
    @FXML
    private TextField codeField;
    private static final Logger logger = Logger.getLogger(TotpRestart.class.getName());

    public void onClick(ActionEvent actionEvent) {
        try{
            @SuppressWarnings("java:S2095")
            HttpClient client = HttpClient.newHttpClient();
            // Tworzymy request POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/library/uzytkownicy/2fa/confirm/" + codeField.getText() + "/" + logUser.getUserEmail()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .PUT(HttpRequest.BodyPublishers.noBody())
                    .build();

            // Wysyłamy request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/donePopup.fxml"));
                Parent popupRoot = fxmlLoader.load();

                //jeśli rejestracja jest nie poprawna
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                popupStage.setScene(new Scene(popupRoot));
                popupStage.showAndWait();

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();

                fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
                popupRoot = fxmlLoader.load();

                //jeśli rejestracja jest nie poprawna
                popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                popupStage.setScene(new Scene(popupRoot));
                popupStage.showAndWait();
            }
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
    }
}

