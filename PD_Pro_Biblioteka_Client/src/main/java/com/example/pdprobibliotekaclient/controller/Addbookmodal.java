package com.example.pdprobibliotekaclient.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Addbookmodal {

  private static final Logger logger = Logger.getLogger(Addbookmodal.class.getName());

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
      logger.log(Level.SEVERE, e.getMessage());
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
      logger.log(Level.SEVERE, e.getMessage());
    }
  }
}
