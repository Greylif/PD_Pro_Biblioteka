package com.example.pdprobibliotekaclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmationPopupController {

  @FXML
  private Button okButton;
  @FXML
  private Button cancelButton;
  @FXML
  private Label labelHeader;
  @FXML
  private Label labelContent;

  private boolean confirmed = false;

  public boolean isConfirmed() {
    return confirmed;
  }

  @FXML
  public void onOk() {
    confirmed = true;
    ((Stage) okButton.getScene().getWindow()).close();
  }

  @FXML
  public void onCancel() {
    confirmed = false;
    ((Stage) cancelButton.getScene().getWindow()).close();
  }
}
