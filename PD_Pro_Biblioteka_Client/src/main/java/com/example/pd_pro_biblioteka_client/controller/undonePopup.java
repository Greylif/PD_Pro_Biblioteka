package com.example.pd_pro_biblioteka_client.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class undonePopup {

  public void closePopup(ActionEvent actionEvent) {
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    stage.close();
  }
}
