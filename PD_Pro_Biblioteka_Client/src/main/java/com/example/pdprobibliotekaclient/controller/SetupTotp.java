package com.example.pdprobibliotekaclient.controller;

import com.example.pdprobibliotekaclient.model.AdminModel;
import com.example.pdprobibliotekaclient.model.LogAdmin;
import com.example.pdprobibliotekaclient.model.LogUser;
import com.example.pdprobibliotekaclient.model.TotpSetupResponse;
import com.example.pdprobibliotekaclient.model.Uzytkownik;
import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SetupTotp {

  private static final Logger logger = Logger.getLogger(SetupTotp.class.getName());
  private static final String USERNAME = "username";
  private static final String BEARER = "Bearer ";
  @FXML
  private Label qrCodeSecret;
  @FXML
  private ImageView qrCodeImage;
  @FXML
  private TextField totpCodeField;
  @FXML
  private Label infoLabel;

  @FXML
  public void initialize() {
    totpConfig();

  }

  private void totpConfig() {
    try {
      Uzytkownik u = LogUser.get();
      AdminModel adm = LogAdmin.get();
      Gson gson = new Gson();

      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();
      Map<String, String> loginData = new HashMap<>();
      String header = null;
      String url = null;

      if (LogUser.getUserIdStr() != null && LogUser.getUserToken() != null) {
        loginData.put(USERNAME, u.getNazwaUzytkownika());
        loginData.put("password", u.getHaslo());
        header = BEARER + LogUser.getUserToken();
        url = "https://localhost:8443/api/auth/setup-totp";
      } else {
        if (LogAdmin.getAdmIdStr() != null && LogAdmin.getAdminToken() != null) {
          loginData.put("password", adm.getHaslo().get());
          loginData.put(USERNAME, adm.getNazwa_Uzytkownika().get());
          header = BEARER + LogAdmin.getAdminToken();
          url = "https://localhost:8443/api/auth/setup-totp/admin";
        }
      }

      String requestBody = gson.toJson(loginData);

      assert url != null;
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
          .header("Content-Type", "application/json")
          .headers("Authorization", header)
          .POST(HttpRequest.BodyPublishers.ofString(requestBody))
          .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      int code = response.statusCode();
      if (code == 200) {
        TotpSetupResponse totpSetupResponse = gson.fromJson(response.body(),
            TotpSetupResponse.class);
        Image qrImage = new Image(totpSetupResponse.getQrCodeUrl());
        qrCodeImage.setImage(qrImage);
        qrCodeSecret.setText(totpSetupResponse.getSecret());
      }


    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  @FXML
  public void onConfirmClicked(ActionEvent actionEvent) {
    try {
      Uzytkownik u = LogUser.get();
      AdminModel adm = LogAdmin.get();
      Gson gson = new Gson();

      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();
      Map<String, Object> loginData = new HashMap<>();
      String header = null;
      String url = null;

      if (LogUser.getUserIdStr() != null && LogUser.getUserToken() != null) {
        loginData.put(USERNAME, u.getNazwaUzytkownika());
        loginData.put("code", Integer.parseInt(totpCodeField.getText()));
        header = BEARER + LogUser.getUserToken();
        url = "https://localhost:8443/api/auth/confirm-totp";
      } else {
        if (LogAdmin.getAdmIdStr() != null && LogAdmin.getAdminToken() != null) {
          loginData.put(USERNAME, adm.getNazwa_Uzytkownika().get());
          loginData.put("code", Integer.parseInt(totpCodeField.getText()));
          header = BEARER + LogAdmin.getAdminToken();
          url = "https://localhost:8443/api/auth/confirm-totp/admin";
        }
      }

      String requestBody = gson.toJson(loginData);
      assert url != null;
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
          .header("Content-Type", "application/json")
          .headers("Authorization", header)
          .POST(HttpRequest.BodyPublishers.ofString(requestBody))
          .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      int code = response.statusCode();
      if (code == 200) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DonePopup.fxml"));
        Parent logRoot = fxmlLoader.load();

        Stage logStage = new Stage();
        logStage.initModality(Modality.APPLICATION_MODAL);
        logStage.setScene(new Scene(logRoot));
        logStage.show();
      } else {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UndonePopup.fxml"));
        Parent logRoot = fxmlLoader.load();

        Stage logStage = new Stage();
        logStage.initModality(Modality.APPLICATION_MODAL);
        logStage.setScene(new Scene(logRoot));
        logStage.show();
      }


    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  @FXML
  public void onCancelClicked(ActionEvent actionEvent) {
    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    stage.close();
  }
}
