package com.example.pd_pro_biblioteka_client.controller;


import com.example.pd_pro_biblioteka_client.model.Ksiazka_2;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.*;
import java.util.Optional;

@Component
public class Client {

    public TableView borrowTable;
    public TabPane tabPane;
    public TableColumn b_title;
    public TableColumn b_autor;
    public TableColumn borrow_date;
    public TableColumn return_date;
    public TableColumn place_name;
    public TableView penaltyTable;

    public TableColumn p_title;
    public TableColumn p_autor;
    public TableColumn p_value;
    public TableColumn p_return_date;
    public TableColumn p_place_name;
    public TableColumn p_status;
    public TableView settingsTable;
    public TableColumn p_id;

    @FXML
    private TableColumn<Ksiazka_2, String> s_title;
    @FXML
    private TableColumn<Ksiazka_2, String> s_autor;
    @FXML
    private TableColumn<Ksiazka_2, String> s_borrow;

    public TableColumn f_checkbox;
    public TableColumn f_name;

    public Button borrow_button;
    public TableView fitrTable;
    @FXML
    private TableView<Ksiazka_2> serachTable;

    @FXML
    public void initialize() {

        s_title.setCellValueFactory(cellData -> cellData.getValue().tytulProperty());
        s_title.setCellFactory(TextFieldTableCell.forTableColumn());
        //s_autor.setCellValueFactory(cellData -> cellData.getValue().gatunekProperty());

        s_title.setOnEditCommit(event -> {
            event.getRowValue().setTytul(event.getNewValue());
        });

        ObservableList<Ksiazka_2> ksiazki = FXCollections.observableArrayList(
                new Ksiazka_2(1, "Metro 2033", "Postapo", LocalDate.of(2010, 3, 15), LocalDateTime.now(), 2, 1),
                new Ksiazka_2(2, "Dune", "Sci-Fi", LocalDate.of(1965, 8, 1), LocalDateTime.now(), 4, 1)
        );

        serachTable.setItems(ksiazki);
        serachTable.setEditable(true);
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
