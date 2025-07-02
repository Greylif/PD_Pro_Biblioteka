package com.example.pd_pro_biblioteka_client.controller;

import com.example.pd_pro_biblioteka_client.model.AdminModel;
import com.example.pd_pro_biblioteka_client.model.AutorzyDTO;
import com.example.pd_pro_biblioteka_client.model.Kary;
import com.example.pd_pro_biblioteka_client.model.KaryDTO;
import com.example.pd_pro_biblioteka_client.model.Ksiazka;
import com.example.pd_pro_biblioteka_client.model.KsiazkaDTO;
import com.example.pd_pro_biblioteka_client.model.Uzytkownik;
import com.example.pd_pro_biblioteka_client.model.UzytkownikDTO;
import com.example.pd_pro_biblioteka_client.model.Wypozyczenia;
import com.example.pd_pro_biblioteka_client.model.WypozyczeniaDTO;
import com.example.pd_pro_biblioteka_client.model.logAdmin;
import com.example.pd_pro_biblioteka_client.service.SessionMonitor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Admin {

  private static final Logger logger = Logger.getLogger(Admin.class.getName());
  private static final String AUTHORIZATION = "Authorization";
  private static final String BEARER = "Bearer ";
  private static final String CONTENTTYPE = "Content-Type";
  private static final String APPURL = "application/x-www-form-urlencoded";
  private static final String LOGOWANIE = "Logowanie";
  @FXML
  private Button add_penalty_button;
  @FXML
  private Button addPenaltyButton;
  @FXML
  private Button refr_button;
  @FXML
  private Button logg_button;
  @FXML
  private Button save_button;
  @FXML
  private CheckBox showPassword;
  @FXML
  private TextField textField;
  @FXML
  private TableView<Uzytkownik> userTable;
  @FXML
  private TableColumn<Uzytkownik, String> u_id;
  @FXML
  private TableColumn<Uzytkownik, String> u_name;
  @FXML
  private TableColumn<Uzytkownik, String> u_surname;
  @FXML
  private TableColumn<Uzytkownik, String> u_login;
  @FXML
  private TableColumn<Uzytkownik, String> u_password;
  @FXML
  private TableColumn<Uzytkownik, String> u_year;
  @FXML
  private TableColumn<Uzytkownik, Boolean> u_status;
  @FXML
  private TableColumn<Wypozyczenia, String> b_person;
  @FXML
  private TableColumn<Wypozyczenia, String> b_id;
  @FXML
  private TableColumn<Wypozyczenia, String> b_title;
  @FXML
  private TableColumn<Wypozyczenia, String> b_autor;
  @FXML
  private TableColumn<Wypozyczenia, String> borrow_date;
  @FXML
  private TableColumn<Wypozyczenia, String> return_date;
  @FXML
  private TableView<Wypozyczenia> borrowTable;
  @FXML
  private TableColumn<Ksiazka, String> s_id;
  @FXML
  private TableColumn<Ksiazka, String> s_genre;
  @FXML
  private TableColumn<Ksiazka, String> s_status;
  @FXML
  private TableColumn<Ksiazka, String> s_year;
  @FXML
  private TableColumn<Ksiazka, String> s_autor;
  @FXML
  private TableView<Ksiazka> searachTable;
  @FXML
  private TableColumn<Ksiazka, String> s_title;
  @FXML
  private TextField admin_name;
  @FXML
  private TextField admin_surname;
  @FXML
  private TextField admin_login;
  @FXML
  private PasswordField admin_password;
  @FXML
  private TextField admin_location_id;
  @FXML
  private TextField admin_id;
  @FXML
  private TableView<Kary> penaltyTable;
  @FXML
  private TableColumn<Kary, Integer> p_id;
  @FXML
  private TableColumn<Kary, String> p_desc;
  @FXML
  private TableColumn<Kary, String> p_payment_date;
  @FXML
  private TableColumn<Kary, Double> p_value;
  @FXML
  private TableColumn<Kary, Boolean> p_status;
  @FXML
  private TableColumn<Kary, String> p_userid;
  @FXML
  private TableColumn<Kary, String> p_date;
  private SessionMonitor sessionMonitor;

  @FXML
  public void initialize() {
    fetchAllData();

    //Tab 1 - książki
    s_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
    s_title.setCellValueFactory(cellData -> cellData.getValue().tytulProperty());
    s_autor.setCellValueFactory(cellData -> cellData.getValue().autorNameProperty());
    s_genre.setCellValueFactory(cellData -> cellData.getValue().gatunekProperty());
    s_year.setCellValueFactory(cellData -> cellData.getValue().dataWydaniaProperty().asString());
    s_status.setCellValueFactory(cellData -> cellData.getValue().WypozyczenieProperty().asString());

    //tab 2 - wypożyczenia
    b_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
    b_person.setCellValueFactory(cellData -> cellData.getValue().userDataProperty());
    b_title.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
    b_autor.setCellValueFactory(cellData -> cellData.getValue().autorNameProperty());
    borrow_date.setCellValueFactory(cellData -> cellData.getValue().data_WypozyczeniaProperty());
    return_date.setCellValueFactory(cellData -> cellData.getValue().data_OddaniaProperty());

    //tab 3 - kary
    penaltyTable.setEditable(true);
    p_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
    p_value.setCellValueFactory(cellData -> cellData.getValue().KwotaProperty().asObject());
    p_date.setCellValueFactory(cellData -> cellData.getValue().getData_Wydania_Kary());
    p_payment_date.setCellValueFactory(cellData -> cellData.getValue().getTermin_Zaplaty());
    p_desc.setCellValueFactory(cellData -> cellData.getValue().getOpis());
    p_userid.setCellValueFactory(
        cellData -> cellData.getValue().id_uzytkownikaProperty().asString());

    p_status.setCellValueFactory(cellData -> cellData.getValue().CzyZaplaconoProperty());
    p_status.setCellFactory(ComboBoxTableCell.forTableColumn(true, false));
    p_status.setEditable(true);
    p_status.setOnEditCommit(event -> {
      Kary kary = event.getRowValue();
      kary.setCzyZaplacono(event.getNewValue());
      sendUpdateKary(kary);
    });

    //tab 4 - uzytkownicy
    u_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
    u_login.setCellValueFactory(cellData -> cellData.getValue().nazwaProperty());
    u_password.setCellValueFactory(cellData -> cellData.getValue().hasloProperty());
    u_name.setCellValueFactory(cellData -> cellData.getValue().imieProperty());
    u_surname.setCellValueFactory(cellData -> cellData.getValue().nazwiskoProperty());
    u_year.setCellValueFactory(cellData -> cellData.getValue().wiekProperty());
    u_status.setCellValueFactory(cellData -> cellData.getValue().ZablokowanyProperty().asObject());

    userTable.setEditable(true);

    u_name.setCellFactory(TextFieldTableCell.forTableColumn());
    u_name.setOnEditCommit(event -> {
      Uzytkownik user = event.getRowValue();
      user.setImie(event.getNewValue());
      sendUpdate(user);
    });

    u_surname.setCellFactory(TextFieldTableCell.forTableColumn());
    u_surname.setOnEditCommit(event -> {
      Uzytkownik user = event.getRowValue();
      user.setNazwisko(event.getNewValue());
      sendUpdate(user);
    });

    u_year.setCellFactory(TextFieldTableCell.forTableColumn());
    u_year.setOnEditCommit(event -> {
      Uzytkownik user = event.getRowValue();
      user.setDataUrodzenia(event.getNewValue());
      sendUpdate(user);
    });

    u_login.setCellFactory(TextFieldTableCell.forTableColumn());
    u_login.setOnEditCommit(event -> {
      Uzytkownik user = event.getRowValue();
      user.setNazwaUzytkownika(event.getNewValue());
      sendUpdate(user);
    });

    u_password.setCellFactory(TextFieldTableCell.forTableColumn());
    u_password.setOnEditCommit(event -> {
      Uzytkownik user = event.getRowValue();
      user.setHaslo(event.getNewValue());
      sendUpdate(user);
    });

    u_status.setCellFactory(ComboBoxTableCell.forTableColumn(true, false));
    u_status.setEditable(true);
    u_status.setOnEditCommit(event -> {
      Uzytkownik user = event.getRowValue();
      user.setZablokowany(event.getNewValue());
      sendUpdate(user);
    });

    //tab 5 - dane admina
    AdminModel adm = logAdmin.get();
    admin_name.setText(String.valueOf(adm.getImie().get()));
    admin_surname.setText(String.valueOf(adm.getNazwisko().get()));
    admin_login.setText(String.valueOf(adm.getNazwa_Uzytkownika().get()));
    admin_password.setText(String.valueOf(adm.getHaslo().get()));
    admin_location_id.setText(String.valueOf(adm.getId_placowki().get()));
    admin_id.setText(String.valueOf(adm.getId().get()));
  }


  private void fetchAllData() {
    @SuppressWarnings("java:S2095")
    HttpClient client = HttpClient.newHttpClient();
    Gson gson = new Gson();

    try {
      // Fetch wszystkie dane
      HttpResponse<String> ksiazkiResponse = client.send(
          HttpRequest.newBuilder()
              .uri(URI.create("https://localhost:8443/library/ksiazki"))
              .header(AUTHORIZATION, BEARER + logAdmin.getAdminToken()).GET().build(),
          HttpResponse.BodyHandlers.ofString()
      );
      List<KsiazkaDTO> ksiazkiDTOs = gson.fromJson(ksiazkiResponse.body(),
          new TypeToken<List<KsiazkaDTO>>() {
          }.getType());

      HttpResponse<String> autorzyResponse = client.send(
          HttpRequest.newBuilder().uri(URI.create("https://localhost:8443/library/autorzy"))
              .header(AUTHORIZATION, BEARER + logAdmin.getAdminToken()).GET().build(),
          HttpResponse.BodyHandlers.ofString()
      );
      List<AutorzyDTO> autorzyDTOs = gson.fromJson(autorzyResponse.body(),
          new TypeToken<List<AutorzyDTO>>() {
          }.getType());

      HttpResponse<String> wypoResponse = client.send(
          HttpRequest.newBuilder().uri(URI.create("https://localhost:8443/library/wypozyczenia"))
              .header(AUTHORIZATION, BEARER + logAdmin.getAdminToken()).GET().build(),
          HttpResponse.BodyHandlers.ofString()
      );
      List<WypozyczeniaDTO> wypoDTOs = gson.fromJson(wypoResponse.body(),
          new TypeToken<List<WypozyczeniaDTO>>() {
          }.getType());

      HttpResponse<String> karyResponse = client.send(
          HttpRequest.newBuilder().uri(URI.create("https://localhost:8443/library/kary"))
              .header(AUTHORIZATION, BEARER + logAdmin.getAdminToken()).GET().build(),
          HttpResponse.BodyHandlers.ofString()
      );
      List<KaryDTO> karyDTOs = gson.fromJson(karyResponse.body(), new TypeToken<List<KaryDTO>>() {
      }.getType());

      HttpResponse<String> uzytkownicyResponse = client.send(
          HttpRequest.newBuilder().uri(URI.create("https://localhost:8443/library/uzytkownicy"))
              .header(AUTHORIZATION, BEARER + logAdmin.getAdminToken()).GET().build(),
          HttpResponse.BodyHandlers.ofString()
      );
      List<UzytkownikDTO> uzytkownicyDTOs = gson.fromJson(uzytkownicyResponse.body(),
          new TypeToken<List<UzytkownikDTO>>() {
          }.getType());

      // Mapy pomocnicze
      Map<Integer, KsiazkaDTO> ksiazkaMap = ksiazkiDTOs.stream()
          .collect(Collectors.toMap(k -> k.id, k -> k));

      Map<Integer, AutorzyDTO> autorMap = autorzyDTOs.stream()
          .collect(Collectors.toMap(a -> a.id, a -> a));

      Map<Integer, UzytkownikDTO> uzytkownikMap = uzytkownicyDTOs.stream()
          .collect(Collectors.toMap(u -> u.id, u -> u));

      //Tab - 2
      List<Wypozyczenia> wypozyczeniaList = new ArrayList<>();

      for (WypozyczeniaDTO dto : wypoDTOs) {
        Wypozyczenia wyp = convertDtoToWypozyczenia(dto);

        // Pobierz użytkownika
        UzytkownikDTO user = uzytkownikMap.get(dto.id_uzytkownika);
        if (user != null) {
          wyp.setUserData(user.Imie + " " + user.Nazwisko);
        }

        // Pobierz książkę
        KsiazkaDTO ksiazka = ksiazkaMap.get(dto.id_ksiazki);
        if (ksiazka != null) {
          wyp.setBookTitle(ksiazka.Tytul);

          // Pobierz autora
          AutorzyDTO autor = autorMap.get(ksiazka.id_autora);
          if (autor != null) {
            wyp.setAutorName(autor.Imie + " " + autor.Nazwisko);
          }
        }

        wypozyczeniaList.add(wyp);
      }

      //Tab - 3
      List<Kary> karyList = new ArrayList<>();

      for (KaryDTO dto : karyDTOs) {
        Kary kara = convertDtoToKary(dto);

        // Szukamy wypożyczenia dla danego użytkownika
        Optional<WypozyczeniaDTO> wypoOpt = wypoDTOs.stream()
            .filter(w -> w.id_uzytkownika == dto.id_uzytkownika)
            .findFirst(); // lub findLast() jeśli masz daty

        if (wypoOpt.isPresent()) {
          WypozyczeniaDTO wyp = wypoOpt.get();
          KsiazkaDTO ksiazka = ksiazkaMap.get(wyp.id_ksiazki);
          if (ksiazka != null) {
            kara.setBookTitle(ksiazka.Tytul);

            AutorzyDTO autor = autorMap.get(ksiazka.id_autora);
            if (autor != null) {
              kara.setAutorName(autor.Imie + " " + autor.Nazwisko);
            }
          }
        }

        karyList.add(kara);
      }

      //Tab - 1
      List<Ksiazka> ksiazkaList = new ArrayList<>();

      for (KsiazkaDTO dto : ksiazkiDTOs) {
        Ksiazka ksiazka = convertDtoToKsiazka(dto);

        AutorzyDTO autor = autorMap.get(dto.id_autora);
        if (autor != null) {
          ksiazka.setAutorName(autor.Imie + " " + autor.Nazwisko);
        }

        ksiazkaList.add(ksiazka);
      }

      //Tab - 4
      List<Uzytkownik> uzytkownikList = new ArrayList<>();

      for (UzytkownikDTO dto : uzytkownicyDTOs) {
        Uzytkownik uzytkownik = convertDtoToUzytkownik(dto);

        uzytkownikList.add(uzytkownik);
      }

      // Wyświetlenie w TableView
      Platform.runLater(() -> penaltyTable.setItems(FXCollections.observableArrayList(karyList)));
      Platform.runLater(
          () -> searachTable.setItems(FXCollections.observableArrayList(ksiazkaList)));
      Platform.runLater(
          () -> userTable.setItems(FXCollections.observableArrayList(uzytkownikList)));
      Platform.runLater(
          () -> borrowTable.setItems(FXCollections.observableArrayList(wypozyczeniaList)));

    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  public void sendUpdate(Uzytkownik user) {
    try {
      String url = String.format("https://localhost:8443/library/uzytkownicy/%d",
          user.idProperty().get());

      StringBuilder bodyBuilder = new StringBuilder();

      // Dodawaj tylko te pola, które nie są nullem
      if (user.getImie() != null) {
        bodyBuilder.append("imie=")
            .append(URLEncoder.encode(user.getImie(), StandardCharsets.UTF_8)).append("&");
      }

      if (user.getNazwisko() != null) {
        bodyBuilder.append("nazwisko=")
            .append(URLEncoder.encode(user.getNazwisko(), StandardCharsets.UTF_8)).append("&");
      }

      if (user.getDataUrodzenia() != null) {
        bodyBuilder.append("dataUrodzenia=")
            .append(URLEncoder.encode(user.getDataUrodzenia(), StandardCharsets.UTF_8)).append("&");
      }

      if (user.getNazwaUzytkownika() != null) {
        bodyBuilder.append("nazwaUzytkownika=")
            .append(URLEncoder.encode(user.getNazwaUzytkownika(), StandardCharsets.UTF_8))
            .append("&");
      }

      if (user.getHaslo() != null) {
        bodyBuilder.append("haslo=")
            .append(URLEncoder.encode(user.getHaslo(), StandardCharsets.UTF_8)).append("&");
      }

      if (user.getEmail() != null) {
        bodyBuilder.append("email=")
            .append(URLEncoder.encode(user.getEmail(), StandardCharsets.UTF_8)).append("&");
      }

      // boolean zawsze wysyłamy (zależnie od Twoich potrzeb)
      bodyBuilder.append("zablokowany=").append(user.isZablokowany()).append("&");
      bodyBuilder.append("mfaEnabled=").append(user.isMfaEnabled()).append("&");

      // MFA secret – wysyłamy tylko jeśli nie jest nullem
      if (user.getMfaSecret() != null) {
        bodyBuilder.append("mfaSecret=")
            .append(URLEncoder.encode(user.getMfaSecret(), StandardCharsets.UTF_8)).append("&");
      }

      // Usuń ostatni "&" jeśli istnieje
      if (bodyBuilder.length() > 0 && bodyBuilder.charAt(bodyBuilder.length() - 1) == '&') {
        bodyBuilder.deleteCharAt(bodyBuilder.length() - 1);
      }

      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
          .header(CONTENTTYPE, APPURL)
          .PUT(HttpRequest.BodyPublishers.ofString(bodyBuilder.toString()))
          .build();

      client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
          .thenAccept(response -> {
          });

    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  private void sendUpdateKary(Kary kary) {
    try {
      String url = String.format("https://localhost:8443/library/kary/%d", kary.idProperty().get());

      StringBuilder bodyBuilder = new StringBuilder();

      // Dodawaj tylko te pola, które nie są nullem
      if (kary.getKwota() != null) {
        bodyBuilder.append("kwota=").append(
                URLEncoder.encode(String.valueOf(kary.getKwota().get()), StandardCharsets.UTF_8))
            .append("&");
      }

      if (kary.getData_Wydania_Kary() != null) {
        bodyBuilder.append("dataWydaniaKary=").append(
            URLEncoder.encode(String.valueOf(kary.getData_Wydania_Kary().get()),
                StandardCharsets.UTF_8)).append("&");
      }

      if (kary.getTermin_Zaplaty() != null) {
        bodyBuilder.append("terminZaplaty=").append(
            URLEncoder.encode(String.valueOf(kary.getTermin_Zaplaty().get()),
                StandardCharsets.UTF_8)).append("&");
      }

      bodyBuilder.append("czyZaplacono=").append(kary.getCzy_Zaplacono().get()).append("&");

      if (kary.getId_uzytkownika() != null) {
        bodyBuilder.append("idUzytkownika=").append(
            URLEncoder.encode(String.valueOf(kary.getId_uzytkownika().get()),
                StandardCharsets.UTF_8)).append("&");
      }

      if (kary.getOpis() != null) {
        bodyBuilder.append("opis=")
            .append(URLEncoder.encode(String.valueOf(kary.getOpis().get()), StandardCharsets.UTF_8))
            .append("&");
      }

      // Usuń ostatni "&" jeśli istnieje
      if (!bodyBuilder.isEmpty() && bodyBuilder.charAt(bodyBuilder.length() - 1) == '&') {
        bodyBuilder.deleteCharAt(bodyBuilder.length() - 1);
      }

      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
          .header(CONTENTTYPE, APPURL)
          .header(AUTHORIZATION, BEARER + logAdmin.getAdminToken())
          .PUT(HttpRequest.BodyPublishers.ofString(bodyBuilder.toString()))
          .build();

      client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
          .thenAccept(response -> {
          });

    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  private Uzytkownik convertDtoToUzytkownik(UzytkownikDTO dto) {
    return new Uzytkownik(
        dto.id,
        dto.Imie,
        dto.Nazwisko,
        dto.Nazwa_Uzytkownika,
        dto.Haslo,
        dto.Email,
        dto.Data_Urodzenia,
        dto.Zablokowany,
        dto.Mfa_Enabled,
        dto.Mfa_Secret
    );
  }

  private Kary convertDtoToKary(KaryDTO dtoK) {
    return new Kary(
        dtoK.id,
        dtoK.Kwota,
        dtoK.Data_Wydania_Kary,
        dtoK.Termin_Zaplaty,
        Boolean.valueOf(dtoK.Czy_Zaplacono),
        dtoK.id_uzytkownika,
        dtoK.opis
    );
  }

  private Ksiazka convertDtoToKsiazka(KsiazkaDTO dto) {
    return new Ksiazka(
        dto.id,
        dto.Tytul,
        dto.Gatunek,
        dto.Data_Wydania,
        dto.Dodano,
        dto.id_autora,
        dto.id_placowki,
        dto.Rezerwacja,
        dto.czy_wypozyczono
    );
  }

  private Wypozyczenia convertDtoToWypozyczenia(WypozyczeniaDTO dto) {
    return new Wypozyczenia(
        dto.id,
        dto.Data_Wypozyczenia,
        dto.Data_Oddania,
        dto.Termin_Oddania,
        dto.id_ksiazki,
        dto.id_uzytkownika
    );
  }

  void setPenaltyTable(TableView<Kary> table) {
    this.penaltyTable = table;
  }


  @FXML
  public void borrow(ActionEvent actionEvent) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addBorrow.fxml"));
      Parent logRoot = fxmlLoader.load();

      Stage logStage = new Stage();
      logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
      logStage.setScene(new Scene(logRoot));
      logStage.show();

    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  public void logout(ActionEvent actionEvent) {
    try {
      logAdmin.clearAdmin();
      Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      stage.close();

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
      Parent logRoot = fxmlLoader.load();

      Stage logStage = new Stage();
      logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
      logStage.setTitle(LOGOWANIE);
      logStage.setScene(new Scene(logRoot));
      logStage.show();
      sessionMonitor.stop();

    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  public void add_book() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addbook_modal.fxml"));
      Parent logRoot = fxmlLoader.load();
      Stage logStage = new Stage();
      logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
      logStage.setTitle("Dodawanie książki");
      logStage.setScene(new Scene(logRoot));
      logStage.show();
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  public void save() {
    // Odczytaj dane z pól
    String id = admin_id.getText();
    String imie = admin_name.getText();
    String nazwisko = admin_surname.getText();
    String login = admin_login.getText();
    String haslo = admin_password.getText();
    String locationId = admin_location_id.getText(); // Możesz sparsować na int jeśli trzeba

    // Wywołaj metodę wysyłającą PUT na serwer
    sendAdminUpdate(id, imie, nazwisko, login, haslo, locationId);

  }

  private void sendAdminUpdate(String ID, String imie, String nazwisko, String login, String haslo,
      String locationId) {
    try {
      String url = "https://localhost:8443/library/admini/" + ID;

      String body = String.format(
          "imie=%s&nazwisko=%s&login=%s&haslo=%s&locationId=%s",
          URLEncoder.encode(imie, StandardCharsets.UTF_8),
          URLEncoder.encode(nazwisko, StandardCharsets.UTF_8),
          URLEncoder.encode(login, StandardCharsets.UTF_8),
          URLEncoder.encode(haslo, StandardCharsets.UTF_8),
          URLEncoder.encode(locationId, StandardCharsets.UTF_8)
      );

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
          .header(CONTENTTYPE, APPURL)
          .header(AUTHORIZATION, BEARER + logAdmin.getAdminToken())
          .PUT(HttpRequest.BodyPublishers.ofString(body))
          .build();

      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Odpowiedź serwera: " + response.body());
      }


    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  public void delete_acc(ActionEvent actionEvent) throws IOException, InterruptedException {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Potwierdzenie");
    alert.setHeaderText("Czy jesteś pewny?");
    alert.setContentText("Tej operacji nie można cofnąć.");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {

      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();
      String url = "https://localhost:8443/library/admini/" + logAdmin.getAdmIdStr();

      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
          .header(CONTENTTYPE, APPURL)
          .header(AUTHORIZATION, BEARER + logAdmin.getAdminToken())
          .DELETE()
          .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        logAdmin.clearAdmin();
        logger.info("Użytkownik zatwierdził.");

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent logRoot = fxmlLoader.load();

        Stage logStage = new Stage();
        logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
        logStage.setTitle(LOGOWANIE);
        logStage.setScene(new Scene(logRoot));
        logStage.show();
      }


    } else {
      // Anulowano
      logger.info("Użytkownik anulował.");
    }
  }


  public void refresh() {
    fetchAllData();
  }

  @FXML
  private void togglePasswordVisibility() {
    if (showPassword.isSelected()) {
      textField.setText(admin_password.getText());
      textField.setVisible(true);
      textField.setManaged(true);
      admin_password.setVisible(false);
      admin_password.setManaged(false);
    } else {
      admin_password.setText(textField.getText());
      admin_password.setVisible(true);
      admin_password.setManaged(true);
      textField.setVisible(false);
      textField.setManaged(false);
    }
  }

  public void AddPenalty() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addPenalty.fxml"));
      Parent logRoot = fxmlLoader.load();
      Stage logStage = new Stage();
      logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
      logStage.setTitle("Dodawanie kary");
      logStage.setScene(new Scene(logRoot));
      logStage.show();
    } catch (IOException e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }


  public void on2FA() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/setup_totp.fxml"));
      Parent logRoot = fxmlLoader.load();

      Stage logStage = new Stage();
      logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
      logStage.setTitle(LOGOWANIE);
      logStage.setScene(new Scene(logRoot));
      logStage.show();
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

}
