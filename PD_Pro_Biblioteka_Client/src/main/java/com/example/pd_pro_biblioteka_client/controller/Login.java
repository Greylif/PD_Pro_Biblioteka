package com.example.pd_pro_biblioteka_client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class Login {

    public Button login;
    public PasswordField user_pass;
    public TextField user_login;
    public Button register;
    public Button pass_rem;


    @FXML
    public void initialize() {
        login.setOnAction(e ->
                System.out.println("Kliknięto LOGIN w GUI" + user_login.getText() + user_pass.getText()));
    }

    @FXML
    public void register_act(javafx.event.ActionEvent actionEvent) {
        System.out.println("Kliknięto REGISTER w GUI");

        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/register.fxml"));
            Parent regRoot = fxmlLoader.load();

            Stage regStage = new Stage();
            regStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
            regStage.setTitle("Rejestracja");
            regStage.setScene(new Scene(regRoot));
            regStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void rem_act(ActionEvent actionEvent) {
        System.out.println("Kliknięto REMINDER w GUI");

        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reminder.fxml"));
            Parent remRoot = fxmlLoader.load();

            Stage remStage = new Stage();
            remStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
            remStage.setTitle("Przypomnij hasło");
            remStage.setScene(new Scene(remRoot));
            remStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
