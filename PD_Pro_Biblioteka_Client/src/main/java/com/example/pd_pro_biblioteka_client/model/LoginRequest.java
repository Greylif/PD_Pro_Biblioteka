package com.example.pd_pro_biblioteka_client.model;

public class LoginRequest {
    private String username;
    private String password;
    private Integer totp; // może być null

    // Konstruktor
    public LoginRequest(String username, String password, Integer twoFA) {
        this.username = username;
        this.password = password;
        this.totp = twoFA;
    }
}
