package com.example.pd_pro_biblioteka_client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class addbook_modal {
    public void onDodajAutora(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admin_addauthor.fxml"));
            Parent logRoot = fxmlLoader.load();
            Stage logStage = new Stage();
            logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
            logStage.setTitle("Dodawanie książki");
            logStage.setScene(new Scene(logRoot));
            logStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDodajKsiazke(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admin_addbook.fxml"));
            Parent logRoot = fxmlLoader.load();
            Stage logStage = new Stage();
            logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
            logStage.setTitle("Dodawanie książki");
            logStage.setScene(new Scene(logRoot));
            logStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
