package com.example.pd_pro_biblioteka_client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegPopup {
    @FXML
    public void closePopup(ActionEvent event) {
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent loginRoot = fxmlLoader.load();

        Stage loginStage = new Stage();
        loginStage.setTitle("Logowanie");
        loginStage.setMinHeight(600);
        loginStage.setMinWidth(600);
        loginStage.setScene(new Scene(loginRoot));

        loginStage.show();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
