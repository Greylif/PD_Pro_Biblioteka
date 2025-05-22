package com.example.pd_pro_biblioteka_client.controller;


import com.example.pd_pro_biblioteka_client.model.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.stream.Collectors;

@Component
public class Client {

    @FXML
    private TableView<Wypozyczenia> borrowTable;
    @FXML
    private TabPane tabPane;
    @FXML
    private TableColumn<Ksiazka, String> b_title;
    @FXML
    private TableColumn<Ksiazka, String> b_autor;
    @FXML
    private TableColumn<Wypozyczenia, LocalDateTime> borrow_date;
    @FXML
    private TableColumn<Wypozyczenia, LocalDateTime> return_date;
    @FXML
    private TableColumn<Ksiazka, String> place_name;

    @FXML
    private TableView<Kary> penaltyTable;
    @FXML
    private TableColumn<Kary, Integer> p_id;
    @FXML
    private TableColumn<Ksiazka, String> p_title;
    @FXML
    private TableColumn<Ksiazka, String> p_autor;
    @FXML
    private TableColumn<Kary, Integer> p_value;
    @FXML
    private TableColumn<Kary, LocalDateTime> p_return_date;
    @FXML
    private TableColumn<Wypozyczenia, String> p_place_name;
    @FXML
    private TableColumn<Kary, Boolean> p_status;


    @FXML
    private TextField user_name;
    @FXML
    private TextField user_surname;
    @FXML
    private DatePicker user_date;
    @FXML
    private TextField user_email;
    @FXML
    private TextField user_login;
    @FXML
    private PasswordField user_password;

    @FXML
    private TableColumn<Ksiazka, String> s_title;
    @FXML
    private TableColumn<Ksiazka, String> s_autor;
    @FXML
    private TableColumn<Ksiazka, Boolean> s_borrow;
    @FXML
    private TableView<Ksiazka> serachTable;

    public TableColumn f_checkbox;
    public TableColumn f_name;

    public Button borrow_button;
    public TableView fitrTable;


    @FXML
    public void initialize() {




        s_title.setEditable(true);
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

    public void reserve(ActionEvent actionEvent) {
        System.out.println("Reserve called");
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

}
