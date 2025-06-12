package com.example.pd_pro_biblioteka_client.controller;

import com.example.pd_pro_biblioteka_client.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Login {

    public Button login;
    public PasswordField user_pass;
    public TextField user_login;
    public Button register;
    public Button pass_rem;



//    @FXML
//    public void login_act(javafx.event.ActionEvent actionEvent) {
//        try {
//            Gson gson = new Gson();
//            HttpClient client = HttpClient.newHttpClient();
//            String url = "http://localhost:8080/library/uzytkownicy/" +
//                    URLEncoder.encode(user_login.getText(), StandardCharsets.UTF_8) + "/" +
//                    URLEncoder.encode(user_pass.getText(), StandardCharsets.UTF_8);
//
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(url))
//                    .GET()
//                    .build();
//
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            String result = response.body();
//            System.out.println(result);
//            int code = response.statusCode();
//
//            if(code == 200) {
//                Type listType = new TypeToken<List<UzytkownikDTO>>() {}.getType();
//                List<UzytkownikDTO> usersDto = gson.fromJson(result, listType);
//
//                if (usersDto != null && !usersDto.isEmpty()) {
//                    UzytkownikDTO dto = usersDto.getFirst();
//
//                    // Konwersja DTO -> Uzytkownik
//                    Uzytkownik user = new Uzytkownik(
//                            dto.id,
//                            dto.Imie,
//                            dto.Nazwisko,
//                            dto.Nazwa_Uzytkownika,
//                            dto.Haslo,
//                            dto.Email,
//                            dto.Data_Urodzenia,
//                            dto.Zablokowany,
//                            dto.Mfa_Enabled,
//                            dto.Mfa_Secret
//                    );
//
//                    logUser.set(user);
//
//                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//                    stage.close();
//
//                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.fxml"));
//                    Parent regRoot = fxmlLoader.load();
//
//                    Stage regStage = new Stage();
//                    regStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
//                    regStage.setTitle("Admin");
//                    regStage.setScene(new Scene(regRoot));
//                    regStage.show();
//                } else {
//                    System.out.println("Błędne dane logowania");
//                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginPop.fxml"));
//                    Parent regRoot = fxmlLoader.load();
//                    Stage regStage = new Stage();
//                    regStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
//                    regStage.setTitle("Klient");
//                    regStage.setScene(new Scene(regRoot));
//                    regStage.show();
//                }
//
//            }
//            else {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginPop.fxml"));
//                Parent regRoot = fxmlLoader.load();
//                Stage regStage = new Stage();
//                regStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
//                regStage.setTitle("Klient");
//                regStage.setScene(new Scene(regRoot));
//                regStage.show();
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void login_act(javafx.event.ActionEvent actionEvent) {
        try {
            Gson gson = new Gson();
            HttpClient client = HttpClient.newHttpClient();

            Map<String, String> loginData = new HashMap<>();
            loginData.put("username", user_login.getText());
            loginData.put("password", user_pass.getText());

            String requestBody = gson.toJson(loginData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String result = response.body();
            int code = response.statusCode();

            if(code == 200) {
                //Type listType = new TypeToken<List<UzytkownikDTO>>() {}.getType();
                //List<UzytkownikDTO> usersDto = gson.fromJson(result, listType);
                JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

                logUser.setUserToken(jsonObject.get("token").getAsString());
                System.out.println("Logged in");
                System.out.println(logUser.getUserToken());
                logUser.clearUserToken();
                System.out.println(logUser.getUserToken());

//                if (usersDto != null && !usersDto.isEmpty()) {
//                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//                    stage.close();
//
//                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.fxml"));
//                    Parent regRoot = fxmlLoader.load();
//
//                    Stage regStage = new Stage();
//                    regStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
//                    regStage.setTitle("Admin");
//                    regStage.setScene(new Scene(regRoot));
//                    regStage.show();
//                } else {
//                    System.out.println("Błędne dane logowania");
//                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginPop.fxml"));
//                    Parent regRoot = fxmlLoader.load();
//                    Stage regStage = new Stage();
//                    regStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
//                    regStage.setTitle("Klient");
//                    regStage.setScene(new Scene(regRoot));
//                    regStage.show();
//                }

            }
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginPop.fxml"));
                Parent regRoot = fxmlLoader.load();
                Stage regStage = new Stage();
                regStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                regStage.setTitle("Klient");
                regStage.setScene(new Scene(regRoot));
                regStage.show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login_act_adm(ActionEvent actionEvent) {
        try {Gson gson = new Gson();
            HttpClient client = HttpClient.newHttpClient();

            String url = "http://localhost:8080/library/admini/" +  // Zmodyfikuj ścieżkę jeśli inna
                    URLEncoder.encode(user_login.getText(), StandardCharsets.UTF_8) + "/" +
                    URLEncoder.encode(user_pass.getText(), StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String result = response.body();
            System.out.println(result);
            int code = response.statusCode();

            if (code == 200) {
                Type listType = new TypeToken<List<AdminDTO>>() {}.getType();
                List<AdminDTO> adminDtos = gson.fromJson(result, listType);

                if (adminDtos != null && !adminDtos.isEmpty()) {
                    AdminDTO dto = adminDtos.get(0);

                    // Konwersja DTO -> AdminModel
                    AdminModel admin = new AdminModel(
                            dto.id,
                            dto.Imie,
                            dto.Nazwisko,
                            dto.Nazwa_Uzytkownika,
                            dto.Haslo,
                            dto.id_placowki,
                            dto.Mfa_Enabled,
                            dto.Mfa_Secret
                    );

                    logAdmin.set(admin); // zakładamy, że masz np. klasę logAdmin podobną do logUser

                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.close();

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admin.fxml"));
                    Parent regRoot = fxmlLoader.load();

                    Stage regStage = new Stage();
                    regStage.initModality(Modality.APPLICATION_MODAL);
                    regStage.setTitle("Panel Admina");
                    regStage.setScene(new Scene(regRoot));
                    regStage.show();
                } else {
                    System.out.println("Błędne dane logowania dla admina");
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginPop.fxml"));
                    Parent regRoot = fxmlLoader.load();
                    Stage regStage = new Stage();
                    regStage.initModality(Modality.APPLICATION_MODAL);
                    regStage.setTitle("Błąd logowania");
                    regStage.setScene(new Scene(regRoot));
                    regStage.show();
                }

            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginPop.fxml"));
                Parent regRoot = fxmlLoader.load();
                Stage regStage = new Stage();
                regStage.initModality(Modality.APPLICATION_MODAL);
                regStage.setTitle("Błąd logowania");
                regStage.setScene(new Scene(regRoot));
                regStage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void register_act(javafx.event.ActionEvent actionEvent) {
        System.out.println("Kliknięto REGISTER w GUI");

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
        //System.out.println("Kliknięto REMINDER w GUI");

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
