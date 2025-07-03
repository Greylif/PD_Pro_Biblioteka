package com.example.pdprobibliotekaclient.controller;


import com.example.pdprobibliotekaclient.model.AutorzyDto;
import com.example.pdprobibliotekaclient.model.Filtr;
import com.example.pdprobibliotekaclient.model.FiltrDto;
import com.example.pdprobibliotekaclient.model.FiltrSup;
import com.example.pdprobibliotekaclient.model.Kary;
import com.example.pdprobibliotekaclient.model.KaryDto;
import com.example.pdprobibliotekaclient.model.Ksiazka;
import com.example.pdprobibliotekaclient.model.KsiazkaDto;
import com.example.pdprobibliotekaclient.model.KsiazkaSup;
import com.example.pdprobibliotekaclient.model.LogUser;
import com.example.pdprobibliotekaclient.model.Uzytkownik;
import com.example.pdprobibliotekaclient.model.Wypozyczenia;
import com.example.pdprobibliotekaclient.model.WypozyczeniaDto;
import com.example.pdprobibliotekaclient.service.SessionMonitor;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Komponent główny odpowiadający za logikę głównego panelu dla klienta.
 */
@Slf4j
@Component
public class Client {

  public static final Button borrowbutton = null;
  private static final Logger logger = Logger.getLogger(Client.class.getName());
  private static final String AUTHORIZATION = "Authorization";
  private static final String BEARER = "Bearer ";
  private static final String CONTENTTYPE = "Content-Type";
  private static final String APPURL = "application/x-www-form-urlencoded";
  @FXML
  private TableView<Filtr> fitrTable;
  @FXML
  private TableColumn<Filtr, String> fidbook;
  @FXML
  private TableColumn<Filtr, String> ftitle;
  @FXML
  private TableColumn<Filtr, String> fidautor;
  @FXML
  private TableColumn<Filtr, String> fautor;
  @FXML
  private TableColumn<Filtr, String> fgenre;
  @FXML
  private TableColumn<Filtr, String> fyear;
  @FXML
  private TableColumn<Filtr, String> fidplace;
  @FXML
  private ComboBox<String> fcombo1;
  @FXML
  private TextField fsearch1;
  @FXML
  private ComboBox<String> fcombo2;
  @FXML
  private TextField fsearch2;
  @FXML
  private ComboBox<String> fcombo3;
  @FXML
  private TextField fsearch3;
  @FXML
  private ComboBox<String> fcombo4;
  @FXML
  private TextField fsearch4;
  @FXML
  private CheckBox showPassword;
  @FXML
  private TextField textField;
  @FXML
  private TableView<Wypozyczenia> borrowTable;
  @FXML
  private TabPane tabPane;
  @FXML
  private TableColumn<Wypozyczenia, String> btitle;
  @FXML
  private TableColumn<Wypozyczenia, String> bautor;
  @FXML
  private TableColumn<Wypozyczenia, String> borrowdate;
  @FXML
  private TableColumn<Wypozyczenia, String> returndate;
  @FXML
  private TableColumn<Wypozyczenia, String> bperson;
  @FXML
  private TableColumn<Wypozyczenia, String> bid;
  @FXML
  private TableColumn<Ksiazka, String> placename;
  @FXML
  private TableView<Kary> penaltyTable;
  @FXML
  private TableColumn<Kary, String> pid;
  @FXML
  private TableColumn<Ksiazka, String> ptitle;
  @FXML
  private TableColumn<Ksiazka, String> pautor;
  @FXML
  private TableColumn<Kary, String> pvalue;
  @FXML
  private TableColumn<Kary, String> preturndate;
  @FXML
  private TableColumn<Wypozyczenia, String> pplacename;
  @FXML
  private TableColumn<Kary, String> pstatus;
  @FXML
  private TableColumn<Kary, String> puserid;
  @FXML
  private TableColumn<Kary, String> pdesc;
  @FXML
  private TableColumn<Kary, String> pdate;
  @FXML
  private TableColumn<Kary, String> ppaymentdate;
  @FXML
  private TextField username;
  @FXML
  private TextField usersurname;
  @FXML
  private TextField userdate;
  @FXML
  private TextField useremail;
  @FXML
  private TextField userlogin;
  @FXML
  private PasswordField userpassword;
  @FXML
  private TextField userId;
  @FXML
  private TableColumn<Ksiazka, String> stitle;
  @FXML
  private TableColumn<Ksiazka, String> sautor;
  @FXML
  private TableColumn<Ksiazka, Boolean> sborrow;
  @FXML
  private TableColumn<Ksiazka, String> sId;
  @FXML
  private TableColumn<Ksiazka, String> sIdautor;
  @FXML
  private TableColumn<Ksiazka, String> sgenre;
  @FXML
  private TableView<Ksiazka> serachTable;
  private SessionMonitor sessionMonitor;


  /**
   * Funkcja inicjalizująca, pobiera dane z serwera, ustawia dane do tabel oraz kart.
   */
  @FXML
  public void initialize() {
    fetchData();

    sId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
    sIdautor.setCellValueFactory(cellData -> cellData.getValue().idAutoraProperty().asString());
    stitle.setCellValueFactory(cellData -> cellData.getValue().tytulProperty());
    sautor.setCellValueFactory(cellData -> cellData.getValue().autorNameProperty());
    sgenre.setCellValueFactory(cellData -> cellData.getValue().gatunekProperty());
    sborrow.setEditable(false);
    sborrow.setCellValueFactory(cellData -> cellData.getValue().WypozyczenieProperty());
    sborrow.setCellFactory(CheckBoxTableCell.forTableColumn(sborrow));

    fcombo1.getItems().addAll("tytul", "autorImie", "idPlacowki");
    fcombo2.getItems().addAll("gatunek", "autorNazwisko", "dataWydania");

    fidbook.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
    ftitle.setCellValueFactory(cellData -> cellData.getValue().tytulProperty());
    fidautor.setCellValueFactory(cellData -> cellData.getValue().idAutoraProperty().asString());
    fautor.setCellValueFactory(cellData -> cellData.getValue().autorDataProperty());
    fgenre.setCellValueFactory(cellData -> cellData.getValue().gatunekProperty());
    fyear.setCellValueFactory(cellData -> cellData.getValue().dataWydaniaProperty().asString());
    fidplace.setCellValueFactory(cellData -> cellData.getValue().idPlacowkiProperty().asString());

    bid.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
    btitle.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
    bautor.setCellValueFactory(cellData -> cellData.getValue().autorNameProperty());
    borrowdate.setCellValueFactory(cellData -> cellData.getValue().data_WypozyczeniaProperty());
    returndate.setCellValueFactory(cellData -> cellData.getValue().termin_OddaniaProperty());

    pid.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
    puserid.setCellValueFactory(
            cellData -> cellData.getValue().id_uzytkownikaProperty().asString());
    pdesc.setCellValueFactory(cellData -> cellData.getValue().OpisProperty());
    pdate.setCellValueFactory(cellData -> cellData.getValue().Data_Wydania_Kary_Property());
    ppaymentdate.setCellValueFactory(cellData -> cellData.getValue().Termin_Zaplaty_Property());
    pvalue.setCellValueFactory(cellData -> cellData.getValue().KwotaProperty().asString());
    pstatus.setCellValueFactory(cellData -> cellData.getValue().getCzy_Zaplacono().asString());

    Uzytkownik u = LogUser.get();
    userId.setText(String.valueOf(u.getId()));
    username.setText(u.getImie());
    usersurname.setText(u.getNazwisko());
    useremail.setText(u.getEmail());
    userlogin.setText(String.valueOf(u.getNazwa_Uzytkownika().get()));
    userpassword.setText(u.getHaslo());
    userdate.setText(String.valueOf(u.getData_urodzenia().get()));
  }

  /**
   * Funkcja odpowiedzalna za logikę przycisku wylogowania się, przekierowywuje do okna login.fxml,
   * czyści dane zalogowanego oraz wyłącza monitor sesji.
   */
  public void logout(ActionEvent actionEvent) {
    try {
      LogUser.clearUser();
      Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      stage.close();

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
      Parent logRoot = fxmlLoader.load();

      Stage logStage = new Stage();
      logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
      logStage.setScene(new Scene(logRoot));
      logStage.show();

      if (sessionMonitor != null) {
        sessionMonitor.stop();
      }

    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }


  /**
   * Funkcja odpowiedzalna za zapis nowych danych użytkownika.
   * Przekierowywuje do funkcji, która wykonuje zapytanie do serwera.
   */
  public void save() {
    String id = userId.getText();
    String imie = username.getText();
    String nazwisko = usersurname.getText();
    String login = userlogin.getText();
    String haslo = userpassword.getText();

    sendUserUpdate(id, imie, nazwisko, login, haslo);
  }

  /**
   * Funkcja przygotowywuje wiadomość do serwera. Zapytanie zmienia dane zalogowanego użytkownika na serwerze.
   *
   * @param id          ID użytkownika.
   * @param imie        Imię użytkownika.
   * @param nazwisko    Nazwisko użytkownika.
   * @param login       Login użytkownika.
   * @param haslo       Hasło użytkownika.
   */
  private void sendUserUpdate(String id, String imie, String nazwisko, String login, String haslo) {
    try {
      String url = "https://localhost:8443/library/uzytkownicy/" + id + "?";

      String body = String.format(
              "imie=%s&nazwisko=%s&nazwaUzytkownika=%s&haslo=%s",
              URLEncoder.encode(imie, StandardCharsets.UTF_8),
              URLEncoder.encode(nazwisko, StandardCharsets.UTF_8),
              URLEncoder.encode(login, StandardCharsets.UTF_8),
              URLEncoder.encode(haslo, StandardCharsets.UTF_8)
      );

      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(url))
              .header(CONTENTTYPE, APPURL)
              .header(AUTHORIZATION, BEARER + LogUser.getUserToken())
              .PUT(BodyPublishers.ofString(body))
              .build();

      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();
      client.send(request, BodyHandlers.ofString());

    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Funkcja obsługująca logikę przycisku usuwania konta. Po zatwierdzeniu tworzone jest zapytanie, które usuwa konto i przekierowywuje do panelu logowania
   *
   * @param actionEvent             Parametr odpowiedzialny za zamykanie okna.
   * @throws IOException            W przypadku problemu z clientemHTTP.
   * @throws InterruptedException   W przypadku problemu z clientemHTTP.
   */
  public void deleteacc(ActionEvent actionEvent) throws IOException, InterruptedException {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Potwierdzenie");
    alert.setHeaderText("Czy jesteś pewny?");
    alert.setContentText("Tej operacji nie można cofnąć.");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {

      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();
      String url = "https://localhost:8443/library/uzytkownicy/" + LogUser.getUserIdStr();

      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(url))
              .header(CONTENTTYPE, APPURL)
              .header(AUTHORIZATION, BEARER + LogUser.getUserToken())
              .DELETE()
              .build();

      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        LogUser.clearUser();
        logger.info("Użytkownik zatwierdził.");

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent logRoot = fxmlLoader.load();

        Stage logStage = new Stage();
        logStage.initModality(Modality.APPLICATION_MODAL);
        logStage.setScene(new Scene(logRoot));
        logStage.show();
      }


    } else {
      logger.info("nie usunelo");
    }
  }

  /**
   * Funkcja odpowiedzalna za widoczność hasła w zakładce ustawień.
   */
  @FXML
  private void togglePasswordVisibility() {
    if (showPassword.isSelected()) {
      textField.setText(userpassword.getText());
      textField.setVisible(true);
      textField.setManaged(true);
      userpassword.setVisible(false);
      userpassword.setManaged(false);
    } else {
      userpassword.setText(textField.getText());
      userpassword.setVisible(true);
      userpassword.setManaged(true);
      textField.setVisible(false);
      textField.setManaged(false);
    }
  }

  /**
   * Funkcja wywołująca pobieranie danych z serwera i integracja danych do map i list.
   * Integruje ona 2 inne funkcje.
   */
  private void fetchData() {
    Uzytkownik u = LogUser.get();
    @SuppressWarnings("java:S2095")
    HttpClient client = HttpClient.newHttpClient();
    Gson gson = new Gson();

    try {
      List<KsiazkaDto> ksiazkiDtos = fetchDtoList(client, gson, "ksiazki",
              new TypeToken<List<KsiazkaDto>>() {
              }.getType());
      List<AutorzyDto> autorzyDtos = fetchDtoList(client, gson, "autorzy",
              new TypeToken<List<AutorzyDto>>() {
              }.getType());
      List<WypozyczeniaDto> wypoDtos = fetchDtoList(client, gson, "wypozyczenia/"
                      + Integer.parseInt(LogUser.userIdStr),
              new TypeToken<List<WypozyczeniaDto>>() {
              }.getType());
      List<KaryDto> karyDtos = fetchDtoList(client, gson, "kary/"
              + Integer.parseInt(LogUser.userIdStr), new TypeToken<List<KaryDto>>() {
      }.getType());

      Map<Integer, KsiazkaDto> ksiazkaMap = ksiazkiDtos.stream()
              .collect(Collectors.toMap(k -> k.id, k -> k));
      Map<Integer, AutorzyDto> autorMap = autorzyDtos.stream()
              .collect(Collectors.toMap(a -> a.id, a -> a));

      List<Ksiazka> ksiazkaList = buildKsiazkaList(ksiazkiDtos, autorMap);
      List<Wypozyczenia> wypozyczeniaList = buildWypozyczeniaList(wypoDtos,
              ksiazkaMap, autorMap, u);
      List<Kary> karyList = buildKaryList(karyDtos, wypoDtos,
              ksiazkaMap, autorMap);

      Platform.runLater(() -> {
        penaltyTable.setItems(FXCollections.observableArrayList(karyList));
        serachTable.setItems(FXCollections.observableArrayList(ksiazkaList));
        borrowTable.setItems(FXCollections.observableArrayList(wypozyczeniaList));
      });

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      logger.log(Level.SEVERE, "Thread was interrupted", e);
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
    }
  }

  /**
   * Funkcja odpowiedzalna za wykonanie zapytania do serwera.
   *
   * @param client                Zainicjowany w fetchAllData clientHTTP
   * @param gson                  Zainicjowany w fetchAllData clientHTTP
   * @param endpoint              Fragment zapytania
   * @return                      Zwracana lista, body gdy zapytanie się uda
   * @throws IOException          w przypadku problemu z clientemHTTP
   * @throws InterruptedException w przypadku problemu z clientemHTTP
   */
  private <T> List<T> fetchDtoList(HttpClient client, Gson gson,
                                   String endpoint, Type type) throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://localhost:8443/library/" + endpoint))
            .header(AUTHORIZATION, BEARER + LogUser.getUserToken())
            .GET()
            .build();
    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
    return gson.fromJson(response.body(), type);
  }

  /**
   * Funkcja łącząca ze sobą mapę Autorów z klasą KsiazkaDto, aby utworzyć pełną listę książek.
   *
   * @param dtos      parametr związany z klasą KsiazkaDto.
   * @param autorMap  parametr związany z mapą autorów.
   * @return          zwracana jest pełna lista do klasy Ksiazka.
   */
  private List<Ksiazka> buildKsiazkaList(List<KsiazkaDto> dtos, Map<Integer, AutorzyDto> autorMap) {
    List<Ksiazka> list = new ArrayList<>();
    for (KsiazkaDto dto : dtos) {
      Ksiazka ksiazka = convertDtoToKsiazka(dto);
      AutorzyDto autor = autorMap.get(dto.id_autora);
      if (autor != null) {
        ksiazka.setAutorName(autor.Imie + " " + autor.Nazwisko);
      }
      list.add(ksiazka);
    }
    return list;
  }

  /**
   * Funkcja łącząca ze sobą mapy z klasą WypozyczeniaDto, aby utworzyć połączenia zgodne z wypożyczeniami tylko dla danego użytkownika.
   *
   * @param dtos            parametr związany z klasą WypozyczeniaDto.
   * @param ksiazkaMap      parametr związany z mapą ksiązek.
   * @param autorMap        parametr związany z mapą autorów.
   * @return                Zwracana jest uzupełniona lista do klasy Wypozyczenia.
   */
  private List<Wypozyczenia> buildWypozyczeniaList(List<WypozyczeniaDto> dtos,
                                                   Map<Integer, KsiazkaDto> ksiazkaMap, Map<Integer, AutorzyDto> autorMap, Uzytkownik u) {
    List<Wypozyczenia> list = new ArrayList<>();
    for (WypozyczeniaDto dto : dtos) {
      Wypozyczenia wyp = convertDtoToWypozyczenia(dto);
      wyp.setUserData(u.getImie() + " " + u.getNazwisko());

      KsiazkaDto ksiazka = ksiazkaMap.get(dto.id_ksiazki);
      if (ksiazka != null) {
        wyp.setBookTitle(ksiazka.Tytul);
        AutorzyDto autor = autorMap.get(ksiazka.id_autora);
        if (autor != null) {
          wyp.setAutorName(autor.Imie + " " + autor.Nazwisko);
        }
      }
      list.add(wyp);
    }
    return list;
  }

  /**
   * Funkcja łącząca ze sobą mapy z klasą KaryDto, aby utworzyć połączenia zgodne z karami dla danego użytkownika.
   *
   * @param dtos        parametr związany z klasą KaryDto.
   * @param wypoDtos    parametr związany z klasą WypozyczeniaDto.
   * @param ksiazkaMap  parametr związany z mapą ksiązek.
   * @param autorMap    parametr związany z mapą autorów.
   * @return            Zwracana jest uzupełniona lista do klasy Kary.
   */
  private List<Kary> buildKaryList(List<KaryDto> dtos, List<WypozyczeniaDto> wypoDtos,
                                   Map<Integer, KsiazkaDto> ksiazkaMap, Map<Integer, AutorzyDto> autorMap) {
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
                  AutorzyDto autor = autorMap.get(ksiazka.id_autora);
                  if (autor != null) {
                    kara.setAutorName(autor.Imie + " " + autor.Nazwisko);
                  }
                }
              });
      list.add(kara);
    }
    return list;
  }


  /**
   * Zamienia obiekt FiltrDto na obiekt Filtr.
   *
   * @param dto obiekt z danymi wejściowymi
   * @return nowy obiekt Filtr z danymi z dto
   */
  public Filtr convertDtoToFiltr(FiltrDto dto) {
    String autorName = (dto.Imie != null && dto.Nazwisko != null) ? dto.Imie + " " + dto.Nazwisko
            : "Nieznany autor";
    FiltrSup filtrSup = new FiltrSup(
            dto.Rezerwacja,
            dto.czy_wypozyczono,
            dto.Gatunek,
            dto.id_autora
    );
    return new Filtr(
            filtrSup,
            dto.Data_Wydania,
            dto.id,
            dto.id_placowki,
            dto.Dodano,
            dto.Tytul,
            autorName
    );
  }

  /**
   * Funkcja odpowiedzalna za konwertowanie DTO do modelu Ksiazka.
   *
   * @param dto Przekazywana klasa DTO.
   * @return    Zwraca nową element w modelu Ksiazka.
   */
  private Ksiazka convertDtoToKsiazka(KsiazkaDto dto) {
    KsiazkaSup ksup = new KsiazkaSup(
            dto.Tytul,
            dto.Gatunek,
            dto.Data_Wydania);
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

  /**
   * Funkcja odpowiedzalna za konwertowanie DTO do modelu Wypozyczenia.
   *
   * @param dto Przekazywana klasa DTO.
   * @return    Zwraca nową element w modelu Wypozyczenia.
   */
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

  /**
   * Funkcja odpowiedzalna za konwertowanie DTO do modelu Kary.
   *
   * @param dtoK Przekazywana klasa DTO.
   * @return    Zwraca nową element w modelu Kary .
   */
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

  /**
   * Funkcja odpowiedzialna za odświeżanie danych.
   */
  public void refresh() {
    fetchData();
  }

  /**
   * Wysyła żądanie filtrujące książki na podstawie wybranych kryteriów.
   * Pobiera dane z dwóch pól wyboru i dwóch pól tekstowych. Tworzy zapytanie HTTP GET
   * do serwera z odpowiednimi parametrami. Odbiera odpowiedź i przetwarza wynik.
   */
  public void filtrbutton() {
    try {
      String param1 = "";
      String param2 = "";

      if (fcombo1.getValue() != null && !fcombo1.getValue().isEmpty()
              && fsearch1.getText() != null && !fsearch1.getText().isEmpty()) {
        String klucz1 = URLEncoder.encode(fcombo1.getValue(), StandardCharsets.UTF_8);
        String wartosc1 = URLEncoder.encode(fsearch1.getText(), StandardCharsets.UTF_8);
        param1 = klucz1 + "=" + wartosc1;
      }

      if (fcombo2.getValue() != null && !fcombo2.getValue().isEmpty()
              && fsearch2.getText() != null && !fsearch2.getText().isEmpty()) {
        String klucz2 = URLEncoder.encode(fcombo2.getValue(), StandardCharsets.UTF_8);
        String wartosc2 = URLEncoder.encode(fsearch2.getText(), StandardCharsets.UTF_8);
        param2 = klucz2 + "=" + wartosc2;
      }

      String form = "";
      if (!param1.isEmpty() && !param2.isEmpty()) {
        form = param1 + "&" + param2;
      } else if (!param1.isEmpty()) {
        form = param1;
      } else if (!param2.isEmpty()) {
        form = param2;
      }

      @SuppressWarnings("java:S2095")
      HttpClient client = HttpClient.newHttpClient();

      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create("https://localhost:8443/library/ksiazki/filtr?" + form))
              .header(CONTENTTYPE, APPURL)
              .header(AUTHORIZATION, BEARER + LogUser.getUserToken())
              .GET()
              .build();

      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

      Gson gson = new Gson();

      if (response.statusCode() == 200) {
        handleJsonResponse(response.body(), gson);
      } else {
        handleServerError();
      }

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      logger.log(Level.SEVERE, "Thread was interrupted", e);
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage(), e);
    }
  }


  /**
   * Przetwarza odpowiedź JSON i wyświetla dane w tabeli.
   * Konwertuje JSON na listę obiektów FiltrDto, zamienia je na Filtr
   * i ustawia w tabeli na interfejsie użytkownika.
   *
   * @param responseBody treść odpowiedzi z serwera
   * @param gson obiekt Gson do konwersji JSON
   */
  private void handleJsonResponse(String responseBody, Gson gson) {
    try {
      Type listType = new TypeToken<List<FiltrDto>>() {
      }.getType();
      List<FiltrDto> dtoList = gson.fromJson(responseBody, listType);

      List<Filtr> filtrList = dtoList.stream()
              .map(this::convertDtoToFiltr)
              .toList();

      Platform.runLater(() -> fitrTable.setItems(FXCollections.observableArrayList(filtrList)));
    } catch (JsonSyntaxException ex) {
      logger.log(Level.SEVERE, () -> "Niepoprawny format odpowiedzi JSON: " + ex.getMessage());
    }
  }

  /**
   * Obsługuje błąd serwera.
   * Zapisuje ostrzeżenie w logach. Jeśli wystąpi błąd przy logowaniu, zapisuje go jako błąd.
   */
  private void handleServerError() {
    try {
      logger.log(Level.WARNING, "Błąd z serwera ");
    } catch (Exception ex) {
      logger.log(Level.SEVERE, () -> "Nie udało się sparsować błędu serwera: " + ex.getMessage());
    }
  }


  /**
   * Funkcja odpowiedzalna za logikę przycisku konfiguracji 2FA, przekierowywuje do okna setup_totp.fxml.
   */
  public void on2fa() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/setup_totp.fxml"));
      Parent logRoot = fxmlLoader.load();

      Stage logStage = new Stage();
      logStage.initModality(Modality.APPLICATION_MODAL);
      logStage.setTitle("Logowanie");
      logStage.setScene(new Scene(logRoot));
      logStage.show();
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
  }


}
