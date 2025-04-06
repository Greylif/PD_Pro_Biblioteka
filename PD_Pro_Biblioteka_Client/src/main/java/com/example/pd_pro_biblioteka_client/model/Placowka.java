package com.example.pd_pro_biblioteka_client.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Placowka {
    @NotNull(message = "Musi posiadac ID")
    private int id;
    @NotBlank(message = "Adres nie moze byc pusty")
    private String Adres;

    @Override
    public String toString() {
        return "Placowka{id=" + id + ", Adres='" + Adres + "'}";
    }
}
