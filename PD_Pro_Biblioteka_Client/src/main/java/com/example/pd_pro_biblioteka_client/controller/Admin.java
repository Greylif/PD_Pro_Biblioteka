package com.example.pd_pro_biblioteka_client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class Admin {
    
    public Button logg_button;
    public TextField admin_name;
    public TextField admin_surname;
    public DatePicker admin_date;
    public TextField admin_email;
    public TextField admin_login;
    public PasswordField admin_password;
    public TableView penaltyTable;
    public TableColumn p_id;
    public TableColumn p_title;
    public TableColumn p_autor;
    public TableColumn p_return_date;
    public TableColumn p_place_name;
    public TableColumn p_value;
    public TableColumn p_status;
    public Button save_button;
    public Button del_button;

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
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
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
}
