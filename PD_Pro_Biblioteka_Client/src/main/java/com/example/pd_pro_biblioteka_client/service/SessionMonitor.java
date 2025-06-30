package com.example.pd_pro_biblioteka_client.service;


import com.example.pd_pro_biblioteka_client.model.logAdmin;
import com.example.pd_pro_biblioteka_client.model.logUser;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionMonitor {
    private final Stage stage;
    public final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final String NewToken;
    private static final Logger logger = Logger.getLogger(SessionMonitor.class.getName());

    public SessionMonitor(String newToken, Stage stage) {
        this.NewToken = newToken;
        this.stage = stage;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::checkToken, 1,70, TimeUnit.SECONDS);
    }

    public void checkToken() {
        HttpClient client = HttpClient.newHttpClient();

        try {

            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create("https://localhost:8443/library/ksiazki"))
                            .header("Authorization", "Bearer " + logUser.getUserToken())
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            );

            HttpResponse<String> responseAdm = client.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create("https://localhost:8443/library/ksiazki"))
                            .header("Authorization", "Bearer " + logAdmin.getAdminToken())
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofString()
            );


            int statusCode = response.statusCode();
            int adminStatusCode = responseAdm.statusCode();


            if (statusCode == 200) {
                logger.log(Level.INFO, "Klient: 200");
            } else {
                if (adminStatusCode == 200) {
                    logger.log(Level.INFO, "Admin: 200");
                } else {
                    Platform.runLater(() -> {
                        stop(); // zatrzymaj monitor
                        forceLogout(stage);
                        logger.log(Level.INFO, "Wylogowano z powodu tokena");
                    });
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }


    public static void forceLogout(Stage stage) {
        try {
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(SessionMonitor.class.getResource("/login.fxml"));
            Parent root = fxmlLoader.load();

            Stage loginStage = new Stage();
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.setTitle("Logowanie");
            loginStage.setScene(new Scene(root));
            loginStage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        logAdmin.clearAdmin();
        logUser.clearUser();
        scheduler.shutdownNow();
    }
}
