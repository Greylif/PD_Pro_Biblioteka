package com.example.pd_pro_biblioteka_client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class Loginpop {
    @FXML
    public void closePopup(ActionEvent event) {
        try{
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
