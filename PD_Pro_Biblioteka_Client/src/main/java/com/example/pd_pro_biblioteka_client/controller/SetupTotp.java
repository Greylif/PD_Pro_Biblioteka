package com.example.pd_pro_biblioteka_client.controller;

import com.example.pd_pro_biblioteka_client.model.*;
import com.google.gson.Gson;
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

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SetupTotp {
    @FXML
    private Label qrCodeSecret;
    @FXML
    private ImageView qrCodeImage;
    @FXML
    private TextField totpCodeField;
    @FXML
    private Label infoLabel;
    private static final Logger logger = Logger.getLogger(SetupTotp.class.getName());


    @FXML
    public void initialize() {
        totpConfig();

    }

    private void totpConfig()
    {
        try {
            Uzytkownik u = logUser.get();
            AdminModel adm = logAdmin.get();
            Gson gson = new Gson();

            @SuppressWarnings("java:S2095")
            HttpClient client = HttpClient.newHttpClient();
            Map<String, String> loginData = new HashMap<>();
            String header = null;
            String URL = null;

            if(logUser.getUserIdStr() != null && logUser.getUserToken() != null){
                loginData.put("username", u.getNazwaUzytkownika());
                loginData.put("password", u.getHaslo());
                header = "Bearer " + logUser.getUserToken();
                URL = "https://localhost:8443/api/auth/setup-totp";
            }
            else {
                if (logAdmin.getAdmIdStr() != null && logAdmin.getAdminToken() != null) {
                    loginData.put("password", adm.getHaslo().get());
                    loginData.put("username", adm.getNazwa_Uzytkownika().get());
                    header = "Bearer " + logAdmin.getAdminToken();
                    URL = "https://localhost:8443/api/auth/setup-totp/admin";
                }
            }

        String requestBody = gson.toJson(loginData);

            assert URL != null;
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/json")
                .headers("Authorization", header)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int code = response.statusCode();
        if (code == 200) {
            TOTPSetupResponse totpSetupResponse = gson.fromJson(response.body(), TOTPSetupResponse.class);
            Image qrImage = new Image(totpSetupResponse.getQrCodeUrl()); // true = background loading
            qrCodeImage.setImage(qrImage);
            qrCodeSecret.setText(totpSetupResponse.getSecret());
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/undonePopup.fxml"));
            Parent logRoot = fxmlLoader.load();
            Stage logStage = new Stage();
            logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
            logStage.setScene(new Scene(logRoot));
            logStage.show();
        }

        }catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    @FXML
    public void onConfirmClicked(ActionEvent actionEvent) {
        try {
            Uzytkownik u = logUser.get();
            AdminModel adm = logAdmin.get();
            Gson gson = new Gson();

            @SuppressWarnings("java:S2095")
            HttpClient client = HttpClient.newHttpClient();
            Map<String, Object> loginData = new HashMap<>();
            String header = null;
            String URL = null;


            if(logUser.getUserIdStr() != null && logUser.getUserToken() != null){
                loginData.put("username", u.getNazwaUzytkownika());
                loginData.put("code", Integer.parseInt(totpCodeField.getText()));
                header = "Bearer " + logUser.getUserToken();
                URL = "https://localhost:8443/api/auth/confirm-totp";
            }
            else {
                if (logAdmin.getAdmIdStr() != null && logAdmin.getAdminToken() != null) {
                    loginData.put("username", adm.getNazwa_Uzytkownika().get());
                    loginData.put("code", Integer.parseInt(totpCodeField.getText()));
                    header = "Bearer " + logAdmin.getAdminToken();
                    URL = "https://localhost:8443/api/auth/confirm-totp/admin";
                }
            }

            String requestBody = gson.toJson(loginData);
            assert URL != null;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("Content-Type", "application/json")
                    .headers("Authorization", header)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int code = response.statusCode();
            if (code == 200) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/donePopup.fxml"));
                Parent logRoot = fxmlLoader.load();

                Stage logStage = new Stage();
                logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                logStage.setScene(new Scene(logRoot));
                logStage.show();
            }
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/undonePopup.fxml"));
                Parent logRoot = fxmlLoader.load();

                Stage logStage = new Stage();
                logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                logStage.setScene(new Scene(logRoot));
                logStage.show();
            }


        }catch (Exception e){
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
