package com.example.pd_pro_biblioteka_client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class LoginController {

    public Button login;
    public PasswordField user_pass;
    public TextField user_login;
    public Button register;
    public Button admin;
    public Button pass_rem;


    @FXML
    public void initialize() {
        login.setOnAction(e ->
                System.out.println("Kliknięto LOGIN w GUI" + user_login.getText() + user_pass.getText()));
    }

    @FXML
    public void register_act(javafx.event.ActionEvent actionEvent) {
        System.out.println("Kliknięto REGISTER w GUI");
    }

    @FXML
    public void rem_act(ActionEvent actionEvent) {
        System.out.println("Kliknięto REMINDER w GUI");
    }

    public void adm_logg(ActionEvent actionEvent) {
        System.out.println("Kliknięto ADMIN w GUI");
    }
}
