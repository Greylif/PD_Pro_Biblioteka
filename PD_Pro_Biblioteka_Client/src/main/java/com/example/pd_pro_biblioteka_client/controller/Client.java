package com.example.pd_pro_biblioteka_client.controller;


import com.example.pd_pro_biblioteka_client.model.*;

import com.example.pd_pro_biblioteka_client.service.SessionMonitor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Client {

    @FXML
    private TableView<Filtr> fitrTable;
    @FXML
    private TableColumn<Filtr, String> f_id_book;
    @FXML
    private TableColumn<Filtr, String> f_title;
    @FXML
    private TableColumn<Filtr, String> f_id_autor;
    @FXML
    private TableColumn<Filtr, String> f_autor;
    @FXML
    private TableColumn<Filtr, String> f_genre;
    @FXML
    private TableColumn<Filtr, String> f_year;
    @FXML
    private TableColumn<Filtr, String> f_id_place;
    @FXML
    private ComboBox<String> f_combo1;
    @FXML
    private TextField f_search1;
    @FXML
    private ComboBox<String> f_combo2;
    @FXML
    private TextField f_search2;
    @FXML
    private ComboBox<String> f_combo3;
    @FXML
    private TextField f_search3;
    @FXML
    private ComboBox<String> f_combo4;
    @FXML
    private TextField f_search4;
    @FXML
    private CheckBox showPassword;
    @FXML
    private TextField textField;
    @FXML
    private TableView<Wypozyczenia> borrowTable;
    @FXML
    private TabPane tabPane;
    @FXML
    private TableColumn<Wypozyczenia, String> b_title;
    @FXML
    private TableColumn<Wypozyczenia, String> b_autor;
    @FXML
    private TableColumn<Wypozyczenia, String> borrow_date;
    @FXML
    private TableColumn<Wypozyczenia, String> return_date;
    @FXML
    private TableColumn<Wypozyczenia, String> b_person;
    @FXML
    private TableColumn<Wypozyczenia, String> b_id;
    @FXML
    private TableColumn<Ksiazka, String> place_name;

    @FXML
    private TableView<Kary> penaltyTable;
    @FXML
    private TableColumn<Kary, String> p_id;
    @FXML
    private TableColumn<Ksiazka, String> p_title;
    @FXML
    private TableColumn<Ksiazka, String> p_autor;
    @FXML
    private TableColumn<Kary, String> p_value;
    @FXML
    private TableColumn<Kary, String> p_return_date;
    @FXML
    private TableColumn<Wypozyczenia, String> p_place_name;
    @FXML
    private TableColumn<Kary, String> p_status;
    @FXML
    private TableColumn<Kary, String> p_userid;
    @FXML
    private TableColumn<Kary, String>  p_desc;
    @FXML
    private TableColumn<Kary, String>  p_date;
    @FXML
    private TableColumn<Kary, String>  p_payment_date;

    @FXML
    private TextField user_name;
    @FXML
    private TextField user_surname;
    @FXML
    private TextField user_date;
    @FXML
    private TextField user_email;
    @FXML
    private TextField user_login;
    @FXML
    private PasswordField user_password;
    @FXML
    private TextField user_ID;

    @FXML
    private TableColumn<Ksiazka, String> s_title;
    @FXML
    private TableColumn<Ksiazka, String> s_autor;
    @FXML
    private TableColumn<Ksiazka, Boolean> s_borrow;
    @FXML
    private TableColumn<Ksiazka, String> s_ID;
    @FXML
    private TableColumn<Ksiazka, String> s_ID_autor;
    @FXML
    private TableView<Ksiazka> serachTable;

    static public Button borrow_button;

    private static final Logger logger = Logger.getLogger(Client.class.getName());



    @FXML
    public void initialize() {
        fetchData();

        //tab 1 - ksiazki
        s_ID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
        s_ID_autor.setCellValueFactory(cellData -> cellData.getValue().idAutoraProperty().asString());
        s_title.setCellValueFactory(cellData -> cellData.getValue().tytulProperty());
        s_autor.setCellValueFactory(cellData -> cellData.getValue().autorNameProperty());
        s_borrow.setEditable(false);
        s_borrow.setCellValueFactory(cellData -> cellData.getValue().WypozyczenieProperty());
        s_borrow.setCellFactory(CheckBoxTableCell.forTableColumn(s_borrow));

        f_combo1.getItems().addAll("tytul", "autorImie", "idPlacowki");
        f_combo2.getItems().addAll("gatunek", "autorNazwisko", "dataWydania");


        //tab 2 - filtry
        f_id_book.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
        f_title.setCellValueFactory(cellData -> cellData.getValue().tytulProperty());
        f_id_autor.setCellValueFactory(cellData -> cellData.getValue().idAutoraProperty().asString());
        f_autor.setCellValueFactory(cellData -> cellData.getValue().autorDataProperty());
        f_genre.setCellValueFactory(cellData -> cellData.getValue().gatunekProperty());
        f_year.setCellValueFactory(cellData -> cellData.getValue().dataWydaniaProperty().asString());
        f_id_place.setCellValueFactory(cellData -> cellData.getValue().idPlacowkiProperty().asString());

        //tab 3 - wypozyczenia
        b_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
        b_title.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
        b_autor.setCellValueFactory(cellData -> cellData.getValue().autorNameProperty());
        borrow_date.setCellValueFactory(cellData -> cellData.getValue().data_WypozyczeniaProperty());
        return_date.setCellValueFactory(cellData -> cellData.getValue().termin_OddaniaProperty());

        //tab 4 - kary
        p_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
        p_userid.setCellValueFactory(cellData -> cellData.getValue().id_uzytkownikaProperty().asString());
        p_desc.setCellValueFactory(cellData -> cellData.getValue().OpisProperty());
        p_date.setCellValueFactory(cellData -> cellData.getValue().Data_Wydania_Kary_Property());
        p_payment_date.setCellValueFactory(cellData -> cellData.getValue().Termin_Zaplaty_Property());
        p_value.setCellValueFactory(cellData -> cellData.getValue().KwotaProperty().asString());
        p_status.setCellValueFactory(cellData -> cellData.getValue().getCzy_Zaplacono().asString());

        //tab 5 - dane użytkownika
        Uzytkownik u = logUser.get();
        user_ID.setText(String.valueOf(u.getId()));
        user_name.setText(u.getImie());
        user_surname.setText(u.getNazwisko());
        user_email.setText(u.getEmail());
        user_login.setText(String.valueOf(u.getNazwa_Uzytkownika().get()));
        user_password.setText(u.getHaslo());
        user_date.setText(String.valueOf(u.getData_urodzenia().get()));
    }


    public void logout(ActionEvent actionEvent) {
        try {
            logUser.clearUser();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent logRoot = fxmlLoader.load();

            Stage logStage = new Stage();
            logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
            logStage.setScene(new Scene(logRoot));
            logStage.show();

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }


    public void save() {
        String id = user_ID.getText();
        String imie = user_name.getText();
        String nazwisko = user_surname.getText();
        String login = user_login.getText();
        String haslo = user_password.getText();

        sendUserUpdate(id, imie, nazwisko, login, haslo);
    }

    private void sendUserUpdate(String ID, String imie, String nazwisko, String login, String haslo) {
        try {
            String url = "https://localhost:8443/library/uzytkownicy/" + ID + "?";

            String body = String.format(
                    "imie=%s&nazwisko=%s&nazwaUzytkownika=%s&haslo=%s",
                    URLEncoder.encode(imie, StandardCharsets.UTF_8),
                    URLEncoder.encode(nazwisko, StandardCharsets.UTF_8),
                    URLEncoder.encode(login, StandardCharsets.UTF_8),
                    URLEncoder.encode(haslo, StandardCharsets.UTF_8)
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Bearer " + logUser.getUserToken())
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            @SuppressWarnings("java:S2095")
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

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
            String url = "https://localhost:8443/library/uzytkownicy/" + logUser.getUserIdStr();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Bearer " + logUser.getUserToken())
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200){
                logUser.clearUser();
                logger.info("Użytkownik zatwierdził.");

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
                Parent logRoot = fxmlLoader.load();

                Stage logStage = new Stage();
                logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
                logStage.setScene(new Scene(logRoot));
                logStage.show();
            }


        } else {
            logger.info("nie usunelo");
        }
    }

    @FXML
    private void togglePasswordVisibility() {
        if (showPassword.isSelected()) {
            textField.setText(user_password.getText());
            textField.setVisible(true);
            textField.setManaged(true);
            user_password.setVisible(false);
            user_password.setManaged(false);
        } else {
            user_password.setText(textField.getText());
            user_password.setVisible(true);
            user_password.setManaged(true);
            textField.setVisible(false);
            textField.setManaged(false);
        }
    }

    private void fetchData() {
        Uzytkownik u = logUser.get();

        @SuppressWarnings("java:S2095")
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        try {
            HttpResponse<String> ksiazkiResponse = client.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create("https://localhost:8443/library/ksiazki")).header("Authorization", "Bearer " + logUser.getUserToken()).GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            List<KsiazkaDTO> ksiazkiDTOs = gson.fromJson(ksiazkiResponse.body(), new TypeToken<List<KsiazkaDTO>>(){}.getType());

            HttpResponse<String> autorzyResponse = client.send(
                    HttpRequest.newBuilder().uri(URI.create("https://localhost:8443/library/autorzy")).header("Authorization", "Bearer " + logUser.getUserToken()).GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            List<AutorzyDTO> autorzyDTOs = gson.fromJson(autorzyResponse.body(), new TypeToken<List<AutorzyDTO>>(){}.getType());

            HttpResponse<String> wypoResponse = client.send(
                    HttpRequest.newBuilder().uri(URI.create("https://localhost:8443/library/wypozyczenia/" + Integer.parseInt(logUser.userIdStr))).header("Authorization", "Bearer " + logUser.getUserToken()).GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            List<WypozyczeniaDTO> wypoDTOs = gson.fromJson(wypoResponse.body(), new TypeToken<List<WypozyczeniaDTO>>(){}.getType());

            HttpResponse<String> karyResponse = client.send(
                    HttpRequest.newBuilder().uri(URI.create("https://localhost:8443/library/kary/" + Integer.parseInt(logUser.userIdStr))).header("Authorization", "Bearer " + logUser.getUserToken()).GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            List<KaryDTO> karyDTOs = gson.fromJson(karyResponse.body(), new TypeToken<List<KaryDTO>>(){}.getType());


            // Mapy pomocnicze
            Map<Integer, KsiazkaDTO> ksiazkaMap = ksiazkiDTOs.stream()
                    .collect(Collectors.toMap(k -> k.id, k -> k));

            Map<Integer, AutorzyDTO> autorMap = autorzyDTOs.stream()
                    .collect(Collectors.toMap(a -> a.id, a -> a));


            //tab 1 - książki
            List<Ksiazka> ksiazkaList = new ArrayList<>();

            for (KsiazkaDTO dto : ksiazkiDTOs) {
                Ksiazka ksiazka = convertDtoToKsiazka(dto);

                AutorzyDTO autor = autorMap.get(dto.id_autora);
                if (autor != null) {
                    ksiazka.setAutorName(autor.Imie + " " + autor.Nazwisko);
                }

                ksiazkaList.add(ksiazka);
            }

            //tab 2 - wypożyczenia
            List<Wypozyczenia> wypozyczeniaList = new ArrayList<>();

            for (WypozyczeniaDTO dto : wypoDTOs) {
                Wypozyczenia wyp = convertDtoToWypozyczenia(dto);

                // Pobierz użytkownika
                wyp.setUserData(u.getImie() + " " + u.getNazwisko());

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

            //tab 3 - kary
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


            Platform.runLater(() -> penaltyTable.setItems(FXCollections.observableArrayList(karyList)));
            Platform.runLater(() -> serachTable.setItems(FXCollections.observableArrayList(ksiazkaList)));
            Platform.runLater(() -> borrowTable.setItems(FXCollections.observableArrayList(wypozyczeniaList)));

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

    }



    public Filtr convertDtoToFiltr(FiltrDTO dto) {
        String autorName = (dto.Imie != null && dto.Nazwisko != null) ? dto.Imie + " " + dto.Nazwisko : "Nieznany autor";
        return new Filtr(
                dto.Rezerwacja,
                dto.czy_wypozyczono,
                dto.Gatunek,
                dto.id_autora,
                dto.Data_Wydania,
                dto.id,
                dto.id_placowki,
                dto.Dodano,
                dto.Tytul,
                autorName
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

    public void refresh() {
        fetchData();
    }

    public void filtr_button() {
        try {
            String param1 = "";
            String param2 = "";

            if (f_combo1.getValue() != null && !f_combo1.getValue().isEmpty() &&
                    f_search1.getText() != null && !f_search1.getText().isEmpty()) {
                String klucz1 = URLEncoder.encode(f_combo1.getValue(), StandardCharsets.UTF_8);
                String wartosc1 = URLEncoder.encode(f_search1.getText(), StandardCharsets.UTF_8);
                param1 = klucz1 + "=" + wartosc1;
            }

            if (f_combo2.getValue() != null && !f_combo2.getValue().isEmpty() &&
                    f_search2.getText() != null && !f_search2.getText().isEmpty()) {
                String klucz2 = URLEncoder.encode(f_combo2.getValue(), StandardCharsets.UTF_8);
                String wartosc2 = URLEncoder.encode(f_search2.getText(), StandardCharsets.UTF_8);
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

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://localhost:8443/library/ksiazki/filtr?" + form))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Bearer " + logUser.getUserToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            if (response.statusCode() == 200) {
                try {
                    Type listType = new TypeToken<List<FiltrDTO>>(){}.getType();
                    List<FiltrDTO> dtoList = gson.fromJson(response.body(), listType);

                    List<Filtr> filtrList = dtoList.stream()
                            .map(this::convertDtoToFiltr)
                            .toList();

                    Platform.runLater(() -> fitrTable.setItems(FXCollections.observableArrayList(filtrList)));
                } catch (com.google.gson.JsonSyntaxException ex) {
                    logger.log(Level.SEVERE, "Niepoprawny format odpowiedzi JSON: " + ex.getMessage());
                }
            } else {
                try {
                    logger.log(Level.WARNING, "Błąd z serwera ");
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Nie udało się sparsować błędu serwera: " + ex.getMessage());
                }
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }


    public void on2FA() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/setup_totp.fxml"));
            Parent logRoot = fxmlLoader.load();

            Stage logStage = new Stage();
            logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
            logStage.setTitle("Logowanie");
            logStage.setScene(new Scene(logRoot));
            logStage.show();
        } catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private Stage getStage() {
        return (Stage) serachTable.getScene().getWindow();
    }


}
