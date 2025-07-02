package com.example.pdprobibliotekaclient.controller;

import com.example.pdprobibliotekaclient.model.AdminDto;
import com.example.pdprobibliotekaclient.model.AdminModel;
import com.example.pdprobibliotekaclient.model.LogAdmin;
import com.example.pdprobibliotekaclient.model.LogUser;
import com.example.pdprobibliotekaclient.model.LoginRequest;
import com.example.pdprobibliotekaclient.model.Uzytkownik;
import com.example.pdprobibliotekaclient.model.UzytkownikDto;
import com.example.pdprobibliotekaclient.service.Jwtdecoder;
import com.example.pdprobibliotekaclient.service.SessionMonitor;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

@Component
public class Login {

  private static final Logger logger = Logger.getLogger(Login.class.getName());
  private static final String TOKEN = "token";
  @FXML
  private PasswordField userpass;
  @FXML
  private TextField userlogin;
  @FXML
  private TextField twofa;

  public void loginact(javafx.event.ActionEvent actionEvent) {
    try {
      Gson gson = new Gson();
      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();
      String username = userlogin.getText();
      String password = userpass.getText();
      Integer twoFacode = null;

      if (!twofa.getText().isEmpty()) {
        twoFacode = parseTwoFaCode(twofa.getText());
        if (twoFacode == null) {
          return;
        }
      }

      LoginRequest loginData = new LoginRequest(username, password, twoFacode);
      String requestBody = gson.toJson(loginData);

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://localhost:8443/api/auth/login"))
          .header("Content-Type", "application/json")
          .POST(HttpRequest.BodyPublishers.ofString(requestBody))
          .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      int code = response.statusCode();
      response.body();

      if (code == 200) {
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        LogUser.setUserToken(jsonObject.get(TOKEN).getAsString());

        if (jsonObject.get(TOKEN) != null) {
          Jwtdecoder.decodeToLogUser(String.valueOf(jsonObject.get(TOKEN)));

          String url = "https://localhost:8443/library/uzytkownicy/" + LogUser.getUserIdStr();

          HttpRequest requestClient = HttpRequest.newBuilder()
              .uri(URI.create(url))
              .header("Authorization", "Bearer " + LogUser.getUserToken())
              .GET()
              .build();

          HttpResponse<String> responseClient = client.send(requestClient,
              HttpResponse.BodyHandlers.ofString());
          String result = responseClient.body();
          code = responseClient.statusCode();

          if (code == 200) {

            Type listType = new TypeToken<List<UzytkownikDto>>() {
            }.getType();

            List<UzytkownikDto> usersDto = gson.fromJson(result, listType);

            if (usersDto != null && !usersDto.isEmpty()) {
              UzytkownikDto dto = usersDto.getFirst();

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
              LogUser.set(user);

              Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
              stage.close();

              FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.fxml"));
              Parent regRoot = fxmlLoader.load();

              Stage regStage = new Stage();
              regStage.initModality(
                  Modality.APPLICATION_MODAL);
              regStage.setTitle("Klient");
              regStage.setScene(new Scene(regRoot));
              regStage.show();

              SessionMonitor monitor = new SessionMonitor(regStage);
              monitor.start();
            }
          }

        } else {
          notworking();
        }

      } else {
        notworking();
      }


    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }


  private Integer parseTwoFaCode(String input) {
    try {
      return Integer.parseInt(input);
    } catch (NumberFormatException e) {
      return null;
    }
  }


  public void notworking() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UndonePopup.fxml"));
    Parent regRoot = fxmlLoader.load();
    Stage regStage = new Stage();
    regStage.initModality(Modality.APPLICATION_MODAL);
    regStage.setScene(new Scene(regRoot));
    regStage.show();
  }

  public void loginactadm(ActionEvent actionEvent) {
    try {
      Gson gson = new Gson();

      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();

      String username = userlogin.getText();
      String password = userpass.getText();
      Integer twoFacode = null;

      if (!twofa.getText().isEmpty()) {
        twoFacode = parseTwoFaCode(twofa.getText());
        if (twoFacode == null) {
          return;
        }
      }

      LoginRequest loginData = new LoginRequest(username, password, twoFacode);
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
        LogAdmin.setAdminToken(jsonObject.get(TOKEN).getAsString());

        if (jsonObject.get(TOKEN) != null) {
          Jwtdecoder.decodeToLogAdm(String.valueOf(jsonObject.get(TOKEN)));

          String url = "https://localhost:8443/library/admini/" + LogAdmin.getAdmIdStr();

          HttpRequest requestClient = HttpRequest.newBuilder()
              .uri(URI.create(url))
              .header("Authorization", "Bearer " + LogAdmin.getAdminToken())
              .GET()
              .build();

          HttpResponse<String> responseClient = client.send(requestClient,
              HttpResponse.BodyHandlers.ofString());
          String result = responseClient.body();
          code = responseClient.statusCode();

          if (code == 200) {
            Type listType = new TypeToken<List<AdminDto>>() {
            }.getType();

            List<AdminDto> adminDtoS = gson.fromJson(result, listType);

            if (adminDtoS != null && !adminDtoS.isEmpty()) {
              AdminDto dto = adminDtoS.getFirst();

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
              LogAdmin.set(admin);
            }

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admin.fxml"));
            Parent regRoot = fxmlLoader.load();

            Stage regStage = new Stage();
            regStage.initModality(Modality.APPLICATION_MODAL);
            regStage.setTitle("Panel Admina");
            regStage.setScene(new Scene(regRoot));
            regStage.show();

            SessionMonitor monitor = new SessionMonitor(regStage);
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
  public void registeract(javafx.event.ActionEvent actionEvent) {

    try {
      Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      stage.close();

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/register.fxml"));
      Parent regRoot = fxmlLoader.load();

      Stage regStage = new Stage();
      regStage.initModality(Modality.APPLICATION_MODAL);
      regStage.setTitle("Rejestracja");
      regStage.setScene(new Scene(regRoot));
      regStage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void remact(ActionEvent actionEvent) {
    try {
      Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      stage.close();

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reminder.fxml"));
      Parent remRoot = fxmlLoader.load();

      Stage remStage = new Stage();
      remStage.initModality(Modality.APPLICATION_MODAL);
      remStage.setTitle("Przypomnij hasło");
      remStage.setScene(new Scene(remRoot));
      remStage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
