package com.example.pd_pro_biblioteka_client.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class Reminder {

    public TextField user_email;
    public Button remind;

    @FXML
    public void reminder_act(javafx.event.ActionEvent actionEvent) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reminder_popup.fxml"));
            Parent popupRoot = fxmlLoader.load();

            //jeśli rejestracja jest poprawna
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
            popupStage.setTitle("Przypomnij hasło");
            popupStage.setScene(new Scene(popupRoot));
            popupStage.showAndWait();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
