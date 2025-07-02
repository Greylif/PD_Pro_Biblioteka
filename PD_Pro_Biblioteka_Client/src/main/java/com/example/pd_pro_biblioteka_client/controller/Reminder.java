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
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Reminder {

    @FXML private TextField user_email;
    private static final Logger logger = Logger.getLogger(Reminder.class.getName());

    private static final String PRZYPHASLO = "Przypomnij hasło";


    @FXML
    public void reminder_act(javafx.event.ActionEvent actionEvent) {

        try {
            @SuppressWarnings("java:S2095")
            HttpClient client = HttpClient.newHttpClient();
            // Tworzymy request POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://localhost:8443/library/uzytkownicy/passwordreset/" + user_email.getText()))
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
                popupStage.setTitle(PRZYPHASLO);
                popupStage.setScene(new Scene(popupRoot));
                popupStage.showAndWait();

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            }
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/undonePopup.fxml"));
                Parent popupRoot = fxmlLoader.load();

                //jeśli rejestracja jest poprawna
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                popupStage.setTitle(PRZYPHASLO);
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

    public void reminder_actTOTP(ActionEvent actionEvent) {
        try{
            logUser.setUserEmail(user_email.getText());

            @SuppressWarnings("java:S2095")
            HttpClient client = HttpClient.newHttpClient();
            // Tworzymy request POST
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost:8443/library/uzytkownicy/2fa/" + user_email.getText()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

            // Wysyłamy request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TotpRestart.fxml"));
                Parent popupRoot = fxmlLoader.load();

                //jeśli rejestracja jest nie poprawna
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                popupStage.setTitle(PRZYPHASLO);
                popupStage.setScene(new Scene(popupRoot));
                popupStage.showAndWait();

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
            }
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/unndonePopup.fxml"));
                Parent popupRoot = fxmlLoader.load();

                //jeśli rejestracja jest poprawna
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                popupStage.setTitle(PRZYPHASLO);
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

