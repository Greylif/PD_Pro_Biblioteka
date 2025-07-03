package com.example.pdprobibliotekaclient.controller;

import com.example.pdprobibliotekaclient.model.AdminModel;
import com.example.pdprobibliotekaclient.model.AutorzyDto;
import com.example.pdprobibliotekaclient.model.Kary;
import com.example.pdprobibliotekaclient.model.KaryDto;
import com.example.pdprobibliotekaclient.model.Ksiazka;
import com.example.pdprobibliotekaclient.model.KsiazkaDto;
import com.example.pdprobibliotekaclient.model.KsiazkaSup;
import com.example.pdprobibliotekaclient.model.LogAdmin;
import com.example.pdprobibliotekaclient.model.Uzytkownik;
import com.example.pdprobibliotekaclient.model.UzytkownikDto;
import com.example.pdprobibliotekaclient.model.Wypozyczenia;
import com.example.pdprobibliotekaclient.model.WypozyczeniaDto;
import com.example.pdprobibliotekaclient.service.SessionMonitor;
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
import java.util.function.Function;
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
  private Button addpenaltybutton2;
  @FXML
  private Button addPenaltyButton;
  @FXML
  private Button refrbutton;
  @FXML
  private Button loggbutton;
  @FXML
  private Button savebutton;
  @FXML
  private CheckBox showPassword;
  @FXML
  private TextField textField;
  @FXML
  private TableView<Uzytkownik> userTable;
  @FXML
  private TableColumn<Uzytkownik, String> uid;
  @FXML
  private TableColumn<Uzytkownik, String> uname;
  @FXML
  private TableColumn<Uzytkownik, String> usurname;
  @FXML
  private TableColumn<Uzytkownik, String> ulogin;
  @FXML
  private TableColumn<Uzytkownik, String> upassword;
  @FXML
  private TableColumn<Uzytkownik, String> uyear;
  @FXML
  private TableColumn<Uzytkownik, Boolean> ustatus;
  @FXML
  private TableColumn<Wypozyczenia, String> bperson;
  @FXML
  private TableColumn<Wypozyczenia, String> bid;
  @FXML
  private TableColumn<Wypozyczenia, String> btitle;
  @FXML
  private TableColumn<Wypozyczenia, String> bautor;
  @FXML
  private TableColumn<Wypozyczenia, String> borrowdate;
  @FXML
  private TableColumn<Wypozyczenia, String> returndate;
  @FXML
  private TableView<Wypozyczenia> borrowTable;
  @FXML
  private TableColumn<Ksiazka, String> sid;
  @FXML
  private TableColumn<Ksiazka, String> sgenre;
  @FXML
  private TableColumn<Ksiazka, String> sstatus;
  @FXML
  private TableColumn<Ksiazka, String> syear;
  @FXML
  private TableColumn<Ksiazka, String> sautor;
  @FXML
  private TableView<Ksiazka> searachTable;
  @FXML
  private TableColumn<Ksiazka, String> stitle;
  @FXML
  private TextField adminname;
  @FXML
  private TextField adminsurname;
  @FXML
  private TextField adminlogin;
  @FXML
  private PasswordField adminpassword;
  @FXML
  private TextField adminlocationid;
  @FXML
  private TextField adminid;
  @FXML
  private TableView<Kary> penaltyTable;
  @FXML
  private TableColumn<Kary, Integer> pid;
  @FXML
  private TableColumn<Kary, String> pdesc;
  @FXML
  private TableColumn<Kary, String> ppaymentdate;
  @FXML
  private TableColumn<Kary, Double> pvalue;
  @FXML
  private TableColumn<Kary, Boolean> pstatus;
  @FXML
  private TableColumn<Kary, String> puserid;
  @FXML
  private TableColumn<Kary, String> pdate;
  private SessionMonitor sessionMonitor;

  @FXML
  public void initialize() {
    fetchAllData();

    sid.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
    stitle.setCellValueFactory(cellData -> cellData.getValue().tytulProperty());
    sautor.setCellValueFactory(cellData -> cellData.getValue().autorNameProperty());
    sgenre.setCellValueFactory(cellData -> cellData.getValue().gatunekProperty());
    syear.setCellValueFactory(cellData -> cellData.getValue().dataWydaniaProperty().asString());
    sstatus.setCellValueFactory(cellData -> cellData.getValue().WypozyczenieProperty().asString());

    bid.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
    bperson.setCellValueFactory(cellData -> cellData.getValue().userDataProperty());
    btitle.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
    bautor.setCellValueFactory(cellData -> cellData.getValue().autorNameProperty());
    borrowdate.setCellValueFactory(cellData -> cellData.getValue().data_WypozyczeniaProperty());
    returndate.setCellValueFactory(cellData -> cellData.getValue().data_OddaniaProperty());

    penaltyTable.setEditable(true);
    pid.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
    pvalue.setCellValueFactory(cellData -> cellData.getValue().KwotaProperty().asObject());
    pdate.setCellValueFactory(cellData -> cellData.getValue().getData_Wydania_Kary());
    ppaymentdate.setCellValueFactory(cellData -> cellData.getValue().getTermin_Zaplaty());
    pdesc.setCellValueFactory(cellData -> cellData.getValue().getOpis());
    puserid.setCellValueFactory(
            cellData -> cellData.getValue().id_uzytkownikaProperty().asString());

    pstatus.setCellValueFactory(cellData -> cellData.getValue().CzyZaplaconoProperty());
    pstatus.setCellFactory(ComboBoxTableCell.forTableColumn(true, false));
    pstatus.setEditable(true);
    pstatus.setOnEditCommit(event -> {
      Kary kary = event.getRowValue();
      kary.setCzyZaplacono(event.getNewValue());
      sendUpdateKary(kary);
    });

    uid.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
    ulogin.setCellValueFactory(cellData -> cellData.getValue().nazwaProperty());
    upassword.setCellValueFactory(cellData -> cellData.getValue().hasloProperty());
    uname.setCellValueFactory(cellData -> cellData.getValue().imieProperty());
    usurname.setCellValueFactory(cellData -> cellData.getValue().nazwiskoProperty());
    uyear.setCellValueFactory(cellData -> cellData.getValue().wiekProperty());
    ustatus.setCellValueFactory(cellData -> cellData.getValue().ZablokowanyProperty().asObject());

    userTable.setEditable(true);

    uname.setCellFactory(TextFieldTableCell.forTableColumn());
    uname.setOnEditCommit(event -> {
      Uzytkownik user = event.getRowValue();
      user.setImie(event.getNewValue());
      sendUpdate(user);
    });

    usurname.setCellFactory(TextFieldTableCell.forTableColumn());
    usurname.setOnEditCommit(event -> {
      Uzytkownik user = event.getRowValue();
      user.setNazwisko(event.getNewValue());
      sendUpdate(user);
    });

    uyear.setCellFactory(TextFieldTableCell.forTableColumn());
    uyear.setOnEditCommit(event -> {
      Uzytkownik user = event.getRowValue();
      user.setDataUrodzenia(event.getNewValue());
      sendUpdate(user);
    });

    ulogin.setCellFactory(TextFieldTableCell.forTableColumn());
    ulogin.setOnEditCommit(event -> {
      Uzytkownik user = event.getRowValue();
      user.setNazwaUzytkownika(event.getNewValue());
      sendUpdate(user);
    });

    upassword.setCellFactory(TextFieldTableCell.forTableColumn());
    upassword.setOnEditCommit(event -> {
      Uzytkownik user = event.getRowValue();
      user.setHaslo(event.getNewValue());
      sendUpdate(user);
    });

    ustatus.setCellFactory(ComboBoxTableCell.forTableColumn(true, false));
    ustatus.setEditable(true);
    ustatus.setOnEditCommit(event -> {
      Uzytkownik user = event.getRowValue();
      user.setZablokowany(event.getNewValue());
      sendUpdate(user);
    });

    AdminModel adm = LogAdmin.get();
    adminname.setText(String.valueOf(adm.getImie().get()));
    adminsurname.setText(String.valueOf(adm.getNazwisko().get()));
    adminlogin.setText(String.valueOf(adm.getNazwa_Uzytkownika().get()));
    adminpassword.setText(String.valueOf(adm.getHaslo().get()));
    adminlocationid.setText(String.valueOf(adm.getId_placowki().get()));
    adminid.setText(String.valueOf(adm.getId().get()));
  }


  private void fetchAllData() {
    @SuppressWarnings("java:S2095")
    HttpClient client = HttpClient.newHttpClient();
    Gson gson = new Gson();

    try {
      List<KsiazkaDto> ksiazkiDtos = fetchData(client, gson, "ksiazki",
              new TypeToken<List<KsiazkaDto>>() {
              });
      List<AutorzyDto> autorzyDtos = fetchData(client, gson, "autorzy",
              new TypeToken<List<AutorzyDto>>() {
              });
      List<WypozyczeniaDto> wypoDtos = fetchData(client, gson, "wypozyczenia",
              new TypeToken<List<WypozyczeniaDto>>() {
              });
      List<KaryDto> karyDtos = fetchData(client, gson, "kary",
              new TypeToken<List<KaryDto>>() {
              });
      List<UzytkownikDto> uzytkownicyDtos = fetchData(client, gson, "uzytkownicy",
              new TypeToken<List<UzytkownikDto>>() {
              });

      Map<Integer, KsiazkaDto> ksiazkaMap = getDtoMap(ksiazkiDtos, k -> k.id);
      Map<Integer, AutorzyDto> autorMap = getDtoMap(autorzyDtos, a -> a.id);
      Map<Integer, UzytkownikDto> uzytkownikMap = getDtoMap(uzytkownicyDtos, u -> u.id);

      List<Wypozyczenia> wypozyczeniaList = convertWypozyczenia(wypoDtos,
              ksiazkaMap, autorMap, uzytkownikMap);
      List<Kary> karyList = convertKary(karyDtos, wypoDtos, ksiazkaMap, autorMap);
      List<Ksiazka> ksiazkaList = convertKsiazki(ksiazkiDtos, autorMap);
      List<Uzytkownik> uzytkownikList = convertUzytkownicy(uzytkownicyDtos);

      updateTables(karyList, ksiazkaList, uzytkownikList, wypozyczeniaList);

    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  private <T> List<T> fetchData(HttpClient client, Gson gson, String endpoint,
                                TypeToken<List<T>> token) throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://localhost:8443/library/" + endpoint))
            .header(AUTHORIZATION, BEARER + LogAdmin.getAdminToken())
            .GET().build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    return gson.fromJson(response.body(), token.getType());
  }

  private <K, V> Map<K, V> getDtoMap(List<V> list, Function<V, K> keyMapper) {
    return list.stream().collect(Collectors.toMap(keyMapper, Function.identity()));
  }

  private List<Wypozyczenia> convertWypozyczenia(List<WypozyczeniaDto> dtos,
                                                 Map<Integer, KsiazkaDto> ksiazkaMap,
                                                 Map<Integer, AutorzyDto> autorMap,
                                                 Map<Integer, UzytkownikDto> uzytkownikMap) {
    List<Wypozyczenia> list = new ArrayList<>();
    for (WypozyczeniaDto dto : dtos) {
      Wypozyczenia wyp = convertDtoToWypozyczenia(dto);

      Optional.ofNullable(uzytkownikMap.get(dto.id_uzytkownika))
              .ifPresent(user -> wyp.setUserData(user.Imie + " " + user.Nazwisko));

      KsiazkaDto ksiazka = ksiazkaMap.get(dto.id_ksiazki);
      if (ksiazka != null) {
        wyp.setBookTitle(ksiazka.Tytul);
        Optional.ofNullable(autorMap.get(ksiazka.id_autora))
                .ifPresent(autor -> wyp.setAutorName(autor.Imie + " " + autor.Nazwisko));
      }

      list.add(wyp);
    }
    return list;
  }

  private List<Kary> convertKary(List<KaryDto> dtos,
                                 List<WypozyczeniaDto> wypoDtos,
                                 Map<Integer, KsiazkaDto> ksiazkaMap,
                                 Map<Integer, AutorzyDto> autorMap) {
    List<Kary> list = new ArrayList<>();
    for (KaryDto dto : dtos) {
      Kary kara = convertDtoToKary(dto);

      wypoDtos.stream()
              .filter(w -> w.id_uzytkownika == dto.id_uzytkownika)
              .findFirst()
              .ifPresent(wyp -> {
                KsiazkaDto ksiazka = ksiazkaMap.get(wyp.id_ksiazki);
                if (ksiazka != null) {
                  kara.setBookTitle(ksiazka.Tytul);
                  Optional.ofNullable(autorMap.get(ksiazka.id_autora))
                          .ifPresent(autor -> kara.setAutorName(autor.Imie + " " + autor.Nazwisko));
                }
              });

      list.add(kara);
    }
    return list;
  }

  private List<Ksiazka> convertKsiazki(List<KsiazkaDto> dtos, Map<Integer, AutorzyDto> autorMap) {
    List<Ksiazka> list = new ArrayList<>();
    for (KsiazkaDto dto : dtos) {
      Ksiazka ksiazka = convertDtoToKsiazka(dto);
      Optional.ofNullable(autorMap.get(dto.id_autora))
              .ifPresent(autor -> ksiazka.setAutorName(autor.Imie + " " + autor.Nazwisko));
      list.add(ksiazka);
    }
    return list;
  }

  private List<Uzytkownik> convertUzytkownicy(List<UzytkownikDto> dtos) {
    return dtos.stream().map(this::convertDtoToUzytkownik).toList();
  }

  private void updateTables(List<Kary> karyList, List<Ksiazka> ksiazkaList,
                            List<Uzytkownik> uzytkownikList, List<Wypozyczenia> wypozyczeniaList) {
    Platform.runLater(() -> {
      penaltyTable.setItems(FXCollections.observableArrayList(karyList));
      searachTable.setItems(FXCollections.observableArrayList(ksiazkaList));
      userTable.setItems(FXCollections.observableArrayList(uzytkownikList));
      borrowTable.setItems(FXCollections.observableArrayList(wypozyczeniaList));
    });
  }


  public void sendUpdate(Uzytkownik user) {
    try {

      StringBuilder bodyBuilder = new StringBuilder();

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

      bodyBuilder.append("zablokowany=").append(user.isZablokowany()).append("&");
      bodyBuilder.append("mfaEnabled=").append(user.isMfaEnabled()).append("&");

      if (user.getMfaSecret() != null) {
        bodyBuilder.append("mfaSecret=")
                .append(URLEncoder.encode(user.getMfaSecret(), StandardCharsets.UTF_8)).append("&");
      }

      if (bodyBuilder.length() > 0 && bodyBuilder.charAt(bodyBuilder.length() - 1) == '&') {
        bodyBuilder.deleteCharAt(bodyBuilder.length() - 1);
      }

      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();

      String url = String.format("https://localhost:8443/library/uzytkownicy/%d",
              user.idProperty().get());

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

      StringBuilder bodyBuilder = new StringBuilder();

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

      if (!bodyBuilder.isEmpty() && bodyBuilder.charAt(bodyBuilder.length() - 1) == '&') {
        bodyBuilder.deleteCharAt(bodyBuilder.length() - 1);
      }

      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();

      String url = String.format("https://localhost:8443/library/kary/%d", kary.idProperty().get());

      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(url))
              .header(CONTENTTYPE, APPURL)
              .header(AUTHORIZATION, BEARER + LogAdmin.getAdminToken())
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

  private Uzytkownik convertDtoToUzytkownik(UzytkownikDto dto) {
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

  private Kary convertDtoToKary(KaryDto dtoK) {
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

  private Ksiazka convertDtoToKsiazka(KsiazkaDto dto) {
    KsiazkaSup ksup = new KsiazkaSup(dto.Tytul, dto.Gatunek, dto.Data_Wydania);
    return new Ksiazka(
            dto.id,
            ksup,
            dto.Dodano,
            dto.id_autora,
            dto.id_placowki,
            dto.Rezerwacja,
            dto.czy_wypozyczono
    );
  }

  private Wypozyczenia convertDtoToWypozyczenia(WypozyczeniaDto dto) {
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
      logStage.initModality(Modality.APPLICATION_MODAL);
      logStage.setScene(new Scene(logRoot));
      logStage.show();

    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  public void logout(ActionEvent actionEvent) {
    try {
      LogAdmin.clearAdmin();
      Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      stage.close();

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
      Parent logRoot = fxmlLoader.load();

      Stage logStage = new Stage();
      logStage.initModality(Modality.APPLICATION_MODAL);
      logStage.setTitle(LOGOWANIE);
      logStage.setScene(new Scene(logRoot));
      logStage.show();

      if (sessionMonitor != null) {
        sessionMonitor.stop();
      }

    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  public void addbook() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addbook_modal.fxml"));
      Parent logRoot = fxmlLoader.load();
      Stage logStage = new Stage();
      logStage.initModality(Modality.APPLICATION_MODAL);
      logStage.setTitle("Dodawanie książki");
      logStage.setScene(new Scene(logRoot));
      logStage.show();
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  public void save() {
    String id = adminid.getText();
    String imie = adminname.getText();
    String nazwisko = adminsurname.getText();
    String login = adminlogin.getText();
    String haslo = adminpassword.getText();
    String locationId = adminlocationid.getText();

    sendAdminUpdate(id, imie, nazwisko, login, haslo, locationId);

  }

  private void sendAdminUpdate(String id, String imie, String nazwisko, String login, String haslo,
                               String locationId) {
    try {
      String url = "https://localhost:8443/library/admini/" + id;

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
              .header(AUTHORIZATION, BEARER + LogAdmin.getAdminToken())
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

  public void deleteacc(ActionEvent actionEvent) throws IOException, InterruptedException {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Potwierdzenie");
    alert.setHeaderText("Czy jesteś pewny?");
    alert.setContentText("Tej operacji nie można cofnąć.");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {

      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();
      String url = "https://localhost:8443/library/admini/" + LogAdmin.getAdmIdStr();

      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(url))
              .header(CONTENTTYPE, APPURL)
              .header(AUTHORIZATION, BEARER + LogAdmin.getAdminToken())
              .DELETE()
              .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        LogAdmin.clearAdmin();
        logger.info("Użytkownik zatwierdził.");

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent logRoot = fxmlLoader.load();

        Stage logStage = new Stage();
        logStage.initModality(Modality.APPLICATION_MODAL);
        logStage.setTitle(LOGOWANIE);
        logStage.setScene(new Scene(logRoot));
        logStage.show();
      }


    } else {
      logger.info("Użytkownik anulował.");
    }
  }


  public void refresh() {
    fetchAllData();
  }

  @FXML
  private void togglePasswordVisibility() {
    if (showPassword.isSelected()) {
      textField.setText(adminpassword.getText());
      textField.setVisible(true);
      textField.setManaged(true);
      adminpassword.setVisible(false);
      adminpassword.setManaged(false);
    } else {
      adminpassword.setText(textField.getText());
      adminpassword.setVisible(true);
      adminpassword.setManaged(true);
      textField.setVisible(false);
      textField.setManaged(false);
    }
  }

  public void addPenalty() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addPenalty.fxml"));
      Parent logRoot = fxmlLoader.load();
      Stage logStage = new Stage();
      logStage.initModality(Modality.APPLICATION_MODAL);
      logStage.setTitle("Dodawanie kary");
      logStage.setScene(new Scene(logRoot));
      logStage.show();
    } catch (IOException e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }


  public void on2fa() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/setup_totp.fxml"));
      Parent logRoot = fxmlLoader.load();

      Stage logStage = new Stage();
      logStage.initModality(Modality.APPLICATION_MODAL);
      logStage.setTitle(LOGOWANIE);
      logStage.setScene(new Scene(logRoot));
      logStage.show();
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }

}
