package com.example.pd_pro_biblioteka_client.controller;

import com.example.pd_pro_biblioteka_client.model.*;
import com.example.pd_pro_biblioteka_client.service.JWTdecoder;
import com.example.pd_pro_biblioteka_client.service.SessionMonitor;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Login {

    @FXML private PasswordField user_pass;
    @FXML private TextField user_login;
    @FXML private TextField twoFA;
    private static final Logger logger = Logger.getLogger(Login.class.getName());

    private static final String TOKEN = "token";



    public void login_act(javafx.event.ActionEvent actionEvent) {
        try {
            Gson gson = new Gson();
            @SuppressWarnings("java:S2095")
            HttpClient client = HttpClient.newHttpClient();
            String username = user_login.getText();
            String password = user_pass.getText();
            Integer twoFAcode = null;

            if (!twoFA.getText().isEmpty()) {
                twoFAcode = parseTwoFACode(twoFA.getText());
                if (twoFAcode == null) {
                    return;
                }
            }

            LoginRequest loginData = new LoginRequest(username, password, twoFAcode);
            String requestBody = gson.toJson(loginData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://localhost:8443/api/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int code = response.statusCode();
            response.body();

            if(code == 200) {
                JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

                logUser.setUserToken(jsonObject.get(TOKEN).getAsString());


                if(jsonObject.get(TOKEN) != null) {
                    JWTdecoder.decodeToLogUser(String.valueOf(jsonObject.get(TOKEN)));

                     String url = "https://localhost:8443/library/uzytkownicy/" + logUser.getUserIdStr();

                     HttpRequest requestClient = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                             .header("Authorization", "Bearer " + logUser.getUserToken())
                        .GET()
                        .build();



                     HttpResponse<String> responseClient = client.send(requestClient, HttpResponse.BodyHandlers.ofString());
                     String result = responseClient.body();
                     code = responseClient.statusCode();


                     if(code == 200) {

                         Type listType = new TypeToken<List<UzytkownikDTO>>() {}.getType();

                        List<UzytkownikDTO> usersDto = gson.fromJson(result, listType);

                        if (usersDto != null && !usersDto.isEmpty()) {
                            UzytkownikDTO dto = usersDto.getFirst();

                            // Konwersja DTO -> Uzytkownik
                            Uzytkownik user = new Uzytkownik(
                                    dto.id,
                                    dto.Imie,
                                    dto.Nazwisko,
                                    dto.Nazwa_Uzytkownika,
                                    password,
                                    dto.Email,
                                    dto.Data_Urodzenia,
                                    dto.Zablokowany,
                                    dto.Mfa_Enabled,
                                    dto.Mfa_Secret
                            );
                            logUser.set(user);

                         Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                         stage.close();

                         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.fxml"));
                         Parent regRoot = fxmlLoader.load();

                         Stage regStage = new Stage();
                         regStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                         regStage.setTitle("Klient");
                         regStage.setScene(new Scene(regRoot));
                         regStage.show();

                         SessionMonitor monitor = new SessionMonitor(logUser.getUserIdStr(), regStage);
                         monitor.start();
                     }
                     }

                } else {
                    notworking();
                }

            }
            else {
                notworking();
            }


        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private Integer parseTwoFACode(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    public void notworking() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/undonePopup.fxml"));
        Parent regRoot = fxmlLoader.load();
        Stage regStage = new Stage();
        regStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
        regStage.setScene(new Scene(regRoot));
        regStage.show();
    }

    public void login_act_adm(ActionEvent actionEvent) {
        try {
            Gson gson = new Gson();

            @SuppressWarnings("java:S2095")
            HttpClient client = HttpClient.newHttpClient();

            String username = user_login.getText();
            String password = user_pass.getText();
            Integer twoFAcode = null;

            if (!twoFA.getText().isEmpty()) {
                twoFAcode = parseTwoFACode(twoFA.getText());
                if (twoFAcode == null) {
                    return;
                }
            }

            LoginRequest loginData = new LoginRequest(username, password, twoFAcode);
            String requestBody = gson.toJson(loginData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://localhost:8443/api/auth/loginadmin"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int code = response.statusCode();

            if (code == 200) {
                JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
                logAdmin.setAdminToken(jsonObject.get(TOKEN).getAsString());

                if (jsonObject.get(TOKEN) != null) {
                    JWTdecoder.decodeToLogAdm(String.valueOf(jsonObject.get(TOKEN)));

                    String url = "https://localhost:8443/library/admini/" + logAdmin.getAdmIdStr();

                    HttpRequest requestClient = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .header("Authorization", "Bearer " + logAdmin.getAdminToken())
                            .GET()
                            .build();



                    HttpResponse<String> responseClient = client.send(requestClient, HttpResponse.BodyHandlers.ofString());
                    String result = responseClient.body();
                    code = responseClient.statusCode();


                    if(code == 200) {
                        Type listType = new TypeToken<List<AdminDTO>>() {}.getType();

                        List<AdminDTO> adminDTOS = gson.fromJson(result, listType);

                        if (adminDTOS != null && !adminDTOS.isEmpty()) {
                            AdminDTO dto = adminDTOS.getFirst();

                            // Konwersja DTO -> Uzytkownik
                            AdminModel admin = new AdminModel(
                                    dto.id,
                                    dto.Imie,
                                    dto.Nazwisko,
                                    dto.Nazwa_Uzytkownika,
                                    password,
                                    dto.id_placowki,
                                    dto.Mfa_Enabled,
                                    dto.Mfa_Secret
                            );
                            logAdmin.set(admin);}


                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.close();

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admin.fxml"));
                        Parent regRoot = fxmlLoader.load();

                        Stage regStage = new Stage();
                        regStage.initModality(Modality.APPLICATION_MODAL);
                        regStage.setTitle("Panel Admina");
                        regStage.setScene(new Scene(regRoot));
                        regStage.show();

                        SessionMonitor monitor = new SessionMonitor(logAdmin.getAdmIdStr(), regStage);
                        monitor.start();
                    }


                } else {
                    notworking();
                }

            } else {
                notworking();
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Token check failed", e);
            Thread.currentThread().interrupt();
        }
    }

    @FXML
    public void register_act(javafx.event.ActionEvent actionEvent) {

        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/register.fxml"));
            Parent regRoot = fxmlLoader.load();

            Stage regStage = new Stage();
            regStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
            regStage.setTitle("Rejestracja");
            regStage.setScene(new Scene(regRoot));
            regStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void rem_act(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reminder.fxml"));
            Parent remRoot = fxmlLoader.load();

            Stage remStage = new Stage();
            remStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
            remStage.setTitle("Przypomnij hasło");
            remStage.setScene(new Scene(remRoot));
            remStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
