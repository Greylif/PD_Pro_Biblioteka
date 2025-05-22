package com.example.pd_pro_biblioteka_client.controller;

import com.example.pd_pro_biblioteka_client.model.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Admin {

    @FXML
    private TableColumn b_person;
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
    private TableColumn place_name;
    @FXML
    private TableView borrowTable;
    @FXML
    private TableColumn<Ksiazka,Boolean> s_select;
    @FXML
    private TableColumn<Ksiazka,String> s_genre;
    @FXML
    private TableColumn<Ksiazka,String> s_status;
    @FXML
    private TableColumn<Ksiazka,String> s_year;
    @FXML
    private TableColumn<Ksiazka,String> s_autor;
    @FXML
    private TableView<Ksiazka> serachTable;
    @FXML
    private TableColumn<Ksiazka,String> s_title;
    @FXML
    private Button logg_button;
    @FXML
    private TextField admin_name;
    @FXML
    private TextField admin_surname;
    @FXML
    private DatePicker admin_date;
    @FXML
    private TextField admin_email;
    @FXML
    private TextField admin_login;
    @FXML
    private PasswordField admin_password;
    @FXML
    private TableView penaltyTable;
    @FXML
    private TableColumn<Kary, Integer> p_id;
    @FXML
    private TableColumn<Kary, String> p_title;
    @FXML
    private TableColumn<Kary, String> p_autor;
    @FXML
    private TableColumn<Kary, String> p_return_date;
    @FXML
    private TableColumn<Placowka, String> p_place_name;
    @FXML
    private TableColumn<Kary, Double> p_value;
    @FXML
    private TableColumn<Kary, Boolean> p_status;
    @FXML
    private TableColumn<Kary, String> p_userid;
    @FXML
    private Button save_button;
    @FXML
    private Button del_button;



    @FXML
    public void initialize() {
        fetchAllData();
        //Tab 1 - książki
        s_title.setCellValueFactory(cellData -> cellData.getValue().tytulProperty());
//        s_autor.setCellValueFactory(cellData -> cellData.getValue().autorFullNameProperty());
//        s_genre.setCellValueFactory(cellData -> cellData.getValue().gatunekProperty());
//        s_year.setCellValueFactory(cellData -> cellData.getValue().dataWydaniaProperty().asString());
//        s_status.setCellValueFactory(cellData -> {
//            Boolean status = cellData.getValue().getStatus();
//            String readableStatus;
//
//            if (status == null) {
//                readableStatus = "Dostępne";
//            } else if (status) {
//                readableStatus = "Wypożyczone";
//            } else {
//                readableStatus = "Zarezerwowane";
//            }
//
//            return new SimpleStringProperty(readableStatus);
//        });
//
//        s_select.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
//        s_select.setCellFactory(CheckBoxTableCell.forTableColumn(s_select));
//
//        serachTable.setEditable(true);
//        s_select.setEditable(true);

        //tab 2 - wypożyczenia
        b_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asString());
        b_title.setCellValueFactory(cellData -> cellData.getValue().tytulProperty());
        b_autor.setCellValueFactory(cellData -> cellData.getValue().autorProperty());
        borrow_date.setCellValueFactory(cellData -> cellData.getValue().dataWypozyczeniaProperty());
        return_date.setCellValueFactory(cellData -> cellData.getValue().terminOddaniaProperty());

        //tab 3 - kary
        p_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        p_value.setCellValueFactory(cellData -> cellData.getValue().KwotaProperty().asObject());
        p_status.setCellValueFactory(cellData -> cellData.getValue().CzyZaplaconoProperty());
        p_return_date.setCellValueFactory(cellData -> cellData.getValue().Termin_Zaplaty_Property());
        p_title.setCellValueFactory(cellData -> cellData.getValue().bookTitleProperty());
        p_autor.setCellValueFactory(cellData -> cellData.getValue().autorNameProperty());
        p_userid.setCellValueFactory(cellData -> cellData.getValue().id_uzytkownikaProperty().asString());


    }

    private void fetchAllData() {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        try {
            // Fetch wszystkie dane
            HttpResponse<String> ksiazkiResponse = client.send(
                    HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/library/ksiazki")).GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            List<KsiazkaDTO> ksiazkiDTOs = gson.fromJson(ksiazkiResponse.body(), new TypeToken<List<KsiazkaDTO>>(){}.getType());

            System.out.println(ksiazkiDTOs);

            HttpResponse<String> autorzyResponse = client.send(
                    HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/library/autorzy")).GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            List<AutorzyDTO> autorzyDTOs = gson.fromJson(autorzyResponse.body(), new TypeToken<List<AutorzyDTO>>(){}.getType());

            HttpResponse<String> wypoResponse = client.send(
                    HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/library/wypozyczenia")).GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            List<WypozyczeniaDTO> wypoDTOs = gson.fromJson(wypoResponse.body(), new TypeToken<List<WypozyczeniaDTO>>(){}.getType());

            HttpResponse<String> karyResponse = client.send(
                    HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/library/kary")).GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            List<KaryDTO> karyDTOs = gson.fromJson(karyResponse.body(), new TypeToken<List<KaryDTO>>(){}.getType());

            // Mapy pomocnicze
            Map<Integer, KsiazkaDTO> ksiazkaMap = ksiazkiDTOs.stream()
                    .collect(Collectors.toMap(k -> k.id, k -> k));

            Map<Integer, AutorzyDTO> autorMap = autorzyDTOs.stream()
                    .collect(Collectors.toMap(a -> a.id, a -> a));

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

            // Wyświetlenie w TableView
            Platform.runLater(() -> penaltyTable.setItems(FXCollections.observableArrayList(karyList)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private Autorzy convertDtoToAutorzy(AutorzyDTO dto) {
        return new Autorzy(
                dto.id,
                dto.Imie,
                dto.Nazwisko,
                dto.Rok_Urodzenia
        );
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

    private AdminModel convertDtoToAdmin(AdminDTO dto) {
        return new AdminModel(
                dto.id,
                dto.Imie,
                dto.Nazwisko,
                dto.Nazwa_Uzytkownika,
                dto.Haslo,
                dto.id_placowki,
                dto.Mfa_Enabled,
                dto.Mfa_Secret
        );
    }

    private Kary convertDtoToKary(KaryDTO dto_k) {
        return new Kary(
                dto_k.id,
                dto_k.Kwota,
                dto_k.Data_Wydania_Kary,
                dto_k.Termin_Zaplaty,
                Boolean.valueOf(dto_k.Czy_Zaplacono),
                dto_k.id_uzytkownika
        );
    }

    private Placowka convertDtoToPlacowka(PlacowkaDTO dto) {
        return new Placowka(
                dto.id,
                dto.Adres
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

    @FXML
    public void borrow(ActionEvent actionEvent) {
    }

    public void logout(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent logRoot = fxmlLoader.load();

            Stage logStage = new Stage();
            logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
            logStage.setTitle("Logowanie");
            logStage.setScene(new Scene(logRoot));
            logStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add_book(ActionEvent actionEvent) {
        try {
//            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admin_addbook.fxml"));
            Parent logRoot = fxmlLoader.load();
            Stage logStage = new Stage();
            logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
            logStage.setTitle("Dodawanie książki");
            logStage.setScene(new Scene(logRoot));
            logStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(ActionEvent actionEvent) {
    }

    public void delete_acc(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdzenie");
        alert.setHeaderText("Czy jesteś pewny?");
        alert.setContentText("Tej operacji nie można cofnąć.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Tu wykonaj akcję po zatwierdzeniu
            System.out.println("Użytkownik zatwierdził.");

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent logRoot = fxmlLoader.load();

            Stage logStage = new Stage();
            logStage.initModality(Modality.APPLICATION_MODAL); // Blokuje interakcję z głównym oknem
            logStage.setTitle("Logowanie");
            logStage.setScene(new Scene(logRoot));
            logStage.show();
        } else {
            // Anulowano
            System.out.println("Użytkownik anulował.");
        }
    }

    public void delete_book(ActionEvent actionEvent) {
    }
}
