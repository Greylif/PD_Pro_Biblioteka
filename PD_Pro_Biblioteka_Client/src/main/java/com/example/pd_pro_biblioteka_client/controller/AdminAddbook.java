package com.example.pd_pro_biblioteka_client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class AdminAddbook {

    public TextField a_title;
    public TextField a_genre;
    public DatePicker a_relaseDate;
    public TextField au_name;
    public TextField au_surname;
    public Button add_button;

    @FXML
    public void add_act(ActionEvent actionEvent) {
    }
}
