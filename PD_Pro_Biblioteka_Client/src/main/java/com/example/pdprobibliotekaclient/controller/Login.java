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

  public void loginact(ActionEvent actionEvent) {
    try {
      String username = userlogin.getText();
      String password = userpass.getText();
      Integer twoFacode = parseTwoFaCode(twofa.getText());

      if (twofa.getText().isEmpty() || twoFacode != null) {
        JsonObject loginResponse = sendLoginRequest(username, password, twoFacode, false);
        if (loginResponse != null && loginResponse.has(TOKEN)) {
          LogUser.setUserToken(loginResponse.get(TOKEN).getAsString());
          Jwtdecoder.decodeToLogUser(loginResponse.get(TOKEN).getAsString());

          List<UzytkownikDto> usersDto = fetchUserDetails(LogUser.getUserIdStr(),
              LogUser.getUserToken());
          if (usersDto != null && !usersDto.isEmpty()) {
            setupLoggedInUser(usersDto.getFirst(), password);
            openClientView(actionEvent);
          } else {
            notworking();
          }
        } else {
          notworking();
        }
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
      String username = userlogin.getText();
      String password = userpass.getText();
      Integer twoFacode = parseTwoFaCode(twofa.getText());

      if (twofa.getText().isEmpty() || twoFacode != null) {
        JsonObject loginResponse = sendLoginRequest(username, password, twoFacode, true);
        if (loginResponse != null && loginResponse.has(TOKEN)) {
          LogAdmin.setAdminToken(loginResponse.get(TOKEN).getAsString());
          Jwtdecoder.decodeToLogAdm(loginResponse.get(TOKEN).getAsString());

          List<AdminDto> adminDtoS = fetchAdminDetails(LogAdmin.getAdmIdStr(),
              LogAdmin.getAdminToken());
          if (adminDtoS != null && !adminDtoS.isEmpty()) {
            setupLoggedInAdmin(adminDtoS.getFirst(), password);
            openAdminView(actionEvent);
          } else {
            notworking();
          }
        } else {
          notworking();
        }
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Token check failed", e);
      Thread.currentThread().interrupt();
    }
  }

  private JsonObject sendLoginRequest(String username, String password,
      Integer twoFacode, boolean isAdmin) throws IOException, InterruptedException {
    Gson gson = new Gson();
    @SuppressWarnings("java:S2095")
    HttpClient client = HttpClient.newHttpClient();
    LoginRequest loginData = new LoginRequest(username, password, twoFacode);
    String requestBody = gson.toJson(loginData);

    String loginUrl = isAdmin ? "https://localhost:8443/api/auth/loginadmin" : "https://localhost:8443/api/auth/login";

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(loginUrl))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
        .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    return response.statusCode() == 200 ? gson.fromJson(response.body(), JsonObject.class) : null;
  }

  private List<UzytkownikDto> fetchUserDetails(String userId, String token)
      throws IOException, InterruptedException {
    String url = "https://localhost:8443/library/uzytkownicy/" + userId;
    return fetchDtoList(url, token, new TypeToken<List<UzytkownikDto>>() {}.getType());
  }

  private List<AdminDto> fetchAdminDetails(String adminId, String token)
      throws IOException, InterruptedException {
    String url = "https://localhost:8443/library/admini/" + adminId;
    return fetchDtoList(url, token,
        new TypeToken<List<AdminDto>>() {}.getType());
  }

  private <T> List<T> fetchDtoList(String url, String token,
      Type type) throws IOException, InterruptedException {
    @SuppressWarnings("java:S2095")
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("Authorization", "Bearer " + token)
        .GET()
        .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    return response.statusCode() == 200 ? new Gson().fromJson(response.body(), type) : null;
  }

  private void setupLoggedInUser(UzytkownikDto dto, String password) {
    Uzytkownik user = new Uzytkownik(dto.id, dto.Imie,
        dto.Nazwisko, dto.Nazwa_Uzytkownika, password,
        dto.Email, dto.Data_Urodzenia, dto.Zablokowany, dto.Mfa_Enabled, dto.Mfa_Secret);
    LogUser.set(user);
  }

  private void setupLoggedInAdmin(AdminDto dto, String password) {
    AdminModel admin = new AdminModel(dto.id, dto.Imie,
        dto.Nazwisko, dto.Nazwa_Uzytkownika, password,
        dto.id_placowki, dto.Mfa_Enabled, dto.Mfa_Secret);
    LogAdmin.set(admin);
  }

  private void openClientView(ActionEvent event) throws IOException {
    openScene(event, "/client.fxml", "Klient");
  }

  private void openAdminView(ActionEvent event) throws IOException {
    openScene(event, "/admin.fxml", "Panel Admina");
  }

  private void openScene(ActionEvent event, String fxmlPath, String title) throws IOException {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.close();

    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
    Parent root = loader.load();

    Stage newStage = new Stage();
    newStage.initModality(Modality.APPLICATION_MODAL);
    newStage.setTitle(title);
    newStage.setScene(new Scene(root));
    newStage.show();

    new SessionMonitor(newStage).start();
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
