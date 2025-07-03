package com.example.pdprobibliotekaclient.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

/**
 * Komponent odpowiedzialny za logikę panelu potwierdzenia usunięcia konta.
 */
@Component
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

  /**
   * Logika gdy zostanie potwierdzone.
   */
  @FXML
  public void onOk() {
    confirmed = true;
    ((Stage) okButton.getScene().getWindow()).close();
  }

  /**
   * Logika gdy nie zostanie potwierdzone.
   */
  @FXML
  public void onCancel() {
    confirmed = false;
    ((Stage) cancelButton.getScene().getWindow()).close();
  }
}
