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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/register_popup.fxml"));
            Parent popupRoot = fxmlLoader.load();

            //jeśli rejestracja jest poprawna
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
            popupStage.setTitle("Rejestracja");
            popupStage.setScene(new Scene(popupRoot));
            popupStage.showAndWait();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
