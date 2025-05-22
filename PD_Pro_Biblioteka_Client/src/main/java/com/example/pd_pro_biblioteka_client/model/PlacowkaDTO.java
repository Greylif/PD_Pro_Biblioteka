package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PlacowkaDTO {
    public int id;
    public String Adres;
}
