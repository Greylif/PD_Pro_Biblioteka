package com.example.pdprobibliotekaclient.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za informowanie użytkownika czy operacja się udała
 */
@Component
public class DonePopup {

  /**
   * Funkcja logiki przycisku
   *
   * @param actionEvent Parametr odpowiedzialny za zamknięcię okna
   */
  @FXML
  public void closePopup(ActionEvent actionEvent) {
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    stage.close();
  }
}
